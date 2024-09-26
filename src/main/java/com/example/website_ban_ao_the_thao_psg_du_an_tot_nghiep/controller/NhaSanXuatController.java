package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.service.NhaSanXuatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/nha-san-xuat")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class NhaSanXuatController {
    @Autowired
    NhaSanXuatService nhaSanXuatService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhaSanXuatService.pageNhaSanXuat(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhaSanXuatService.pageSearchNhaSanXuat(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listMauSacAcitve() {
        return ResponseEntity.ok(nhaSanXuatService.listNhaSanXuat());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNhaSanXuatId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(nhaSanXuatService.getOneNhaSanXuat(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNhaSanXuat(@Valid @RequestBody CreateNhaSanXuatRequest createNhaSanXuatRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhaSanXuatService.createNhaSanXuat(createNhaSanXuatRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNhaSanXuat(@Valid @RequestBody UpdateNhaSanXuatRequest updateNhaSanXuatRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhaSanXuatService.updateNhaSanXuat(updateNhaSanXuatRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertNhaSanXuat(@PathVariable("id") Integer id) {
        nhaSanXuatService.removeOrRevertNhaSanXuat(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeNhaSanXuat(@PathVariable("id") Integer id) {
        nhaSanXuatService.removeOrRevertNhaSanXuat(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
