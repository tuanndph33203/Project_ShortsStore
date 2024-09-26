package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.service.LoaiSanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;
@RequestMapping("/api/loai-san-pham")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class LoaiSanPhamController {
    @Autowired
    LoaiSanPhamService loaiSanPhamService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(loaiSanPhamService.pageLoaiSanPham(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(loaiSanPhamService.pageSearchLoaiSanPham(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listLoaiSanPhamAcitve() {
        return ResponseEntity.ok(loaiSanPhamService.listLoaiSanPhamResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoaiSanPhamId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(loaiSanPhamService.getOneLoaiSanPham(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLoaiSanPham(@Valid @RequestBody CreateLoaiSanPhamRequest createLoaiSanPhamRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(loaiSanPhamService.createLoaiSanPham(createLoaiSanPhamRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoaiSanPham(@Valid @RequestBody UpdateLoaiSanPhamRequest updateLoaiSanPhamRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(loaiSanPhamService.updateLoaiSanPham(updateLoaiSanPhamRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertLoaiSanPham(@PathVariable("id") Integer id) {
        loaiSanPhamService.removeOrRevertLoaiSanPham(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeLoaiSanPham(@PathVariable("id") Integer id) {
        loaiSanPhamService.removeOrRevertLoaiSanPham(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
