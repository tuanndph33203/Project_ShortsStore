package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.service.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;
@RequestMapping("/api/thuong-hieu")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class ThuongHieuController {
    @Autowired
    ThuongHieuService thuongHieuService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(thuongHieuService.pageThuongHieu(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(thuongHieuService.pageSearchThuongHieu(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listMauSacAcitve() {
        return ResponseEntity.ok(thuongHieuService.listThuongHieuResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getThuongHieuId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(thuongHieuService.getOneThuongHieu(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createThuongHieu(@Valid @RequestBody CreateThuongHieuRequest createThuongHieuRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(thuongHieuService.createThuongHieu(createThuongHieuRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateThuongHieu(@Valid @RequestBody UpdateThuongHieuRequest updateThuongHieuRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(thuongHieuService.updateThuongHieu(updateThuongHieuRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertThuongHieu(@PathVariable("id") Integer id) {
        thuongHieuService.removeOrRevertThuongHieu(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeThuongHieu(@PathVariable("id") Integer id) {
        thuongHieuService.removeOrRevertThuongHieu(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
