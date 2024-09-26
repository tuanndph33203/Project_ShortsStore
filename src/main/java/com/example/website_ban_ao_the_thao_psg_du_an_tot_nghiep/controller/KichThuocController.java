package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKichThuocRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKichThuocRequest;
import com.example.website_ban_ao_the_thao_ps.service.KichThuocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/kich-thuoc")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class KichThuocController {
    @Autowired
    KichThuocService kichThuocService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(kichThuocService.pageKichThuoc(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(kichThuocService.pageSearchKichThuoc(pageNo, pageSize, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKichThuocId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(kichThuocService.getOneKichThuoc(id));
    }
    @GetMapping("/list")
    public ResponseEntity<?> listKichThuocAcitve() {
        return ResponseEntity.ok(kichThuocService.listKichThuocResponse());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createKichThuoc(@Valid @RequestBody CreateKichThuocRequest createKichThuocRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(kichThuocService.createKichThuoc(createKichThuocRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKichThuoc(@Valid @RequestBody UpdateKichThuocRequest updateKichThuocRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(kichThuocService.updateKichThuoc(updateKichThuocRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertKichThuoc(@PathVariable("id") Integer id) {
        kichThuocService.removeOrRevertKichThuoc(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeKichThuoc(@PathVariable("id") Integer id) {
        kichThuocService.removeOrRevertKichThuoc(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
