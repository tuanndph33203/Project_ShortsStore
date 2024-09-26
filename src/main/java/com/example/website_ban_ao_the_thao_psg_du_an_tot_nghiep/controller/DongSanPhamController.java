package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.service.DongSanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/dong-san-pham")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class DongSanPhamController {
    @Autowired
    DongSanPhamService dongSanPhamService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(dongSanPhamService.pageDongSanPham(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(dongSanPhamService.pageSearchDongSanPham(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listDongSanPhamAcitve() {
        return ResponseEntity.ok(dongSanPhamService.listDongSanPhamResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDongSanPhamId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(dongSanPhamService.getOneDongSanPham(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDongSanPham(@Valid @RequestBody CreateDongSanPhamRequest createDongSanPhamRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(dongSanPhamService.createDongSanPham(createDongSanPhamRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDongSanPham(@Valid @RequestBody UpdateDongSanPhamRequest updateDongSanPhamRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(dongSanPhamService.updateDongSanPham(updateDongSanPhamRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertDongSanPham(@PathVariable("id") Integer id) {
        dongSanPhamService.removeOrRevertDongSanPham(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeDongSanPham(@PathVariable("id") Integer id) {
        dongSanPhamService.removeOrRevertDongSanPham(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
