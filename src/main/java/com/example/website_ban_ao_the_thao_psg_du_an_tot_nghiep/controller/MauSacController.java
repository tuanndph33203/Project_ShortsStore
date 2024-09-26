package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.service.MauSacService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/mau-sac")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class MauSacController {
    @Autowired
    MauSacService mauSacService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(mauSacService.pageMauSac(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(mauSacService.pageSearchMauSac(pageNo, pageSize, search));
    }


    @GetMapping("/list")
    public ResponseEntity<?> listMauSacAcitve() {
        return ResponseEntity.ok(mauSacService.listMauSacResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMauSacId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(mauSacService.getOneMauSac(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMauSac(@Valid @RequestBody CreateMauSacRequest createMauSacRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(mauSacService.createMauSac(createMauSacRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMauSac(@Valid @RequestBody UpdateMauSacRequest updateMauSacRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(mauSacService.updateMauSac(updateMauSacRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertMauSac(@PathVariable("id") Integer id) {
        mauSacService.removeOrRevertMauSac(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeMauSac(@PathVariable("id") Integer id) {
        mauSacService.removeOrRevertMauSac(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
