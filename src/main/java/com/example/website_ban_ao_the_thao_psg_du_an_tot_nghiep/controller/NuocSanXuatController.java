package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.service.NuocSanXuatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/nuoc-san-xuat")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class NuocSanXuatController {
    @Autowired
    NuocSanXuatService nuocSanXuatService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nuocSanXuatService.pageNuocSanXuat(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nuocSanXuatService.pageSearchNuocSanXuat(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listMauSacAcitve() {
        return ResponseEntity.ok(nuocSanXuatService.listNuocSanXuatResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNuocSanXuatId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(nuocSanXuatService.getOneNuocSanXuat(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNuocSanXuat(@Valid @RequestBody CreateNuocSanXuatRequest createNuocSanXuatRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nuocSanXuatService.createNuocSanXuat(createNuocSanXuatRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNuocSanXuat(@Valid @RequestBody UpdateNuocSanXuatRequest updateNuocSanXuatRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nuocSanXuatService.updateNuocSanXuat(updateNuocSanXuatRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertNuocSanXuat(@PathVariable("id") Integer id) {
        nuocSanXuatService.removeOrRevertNuocSanXuat(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeNuocSanXuat(@PathVariable("id") Integer id) {
        nuocSanXuatService.removeOrRevertNuocSanXuat(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
