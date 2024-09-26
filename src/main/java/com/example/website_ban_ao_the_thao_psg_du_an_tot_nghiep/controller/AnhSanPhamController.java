package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.service.AnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/anh-san-pham")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class AnhSanPhamController {

    @Autowired
    private AnhSanPhamService anhSanPhamService;

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAnhSanPham(@PathVariable("id") Integer id) {
        this.anhSanPhamService.delete(id);
        return ResponseEntity.ok("Đã xóa!");
    }
}
