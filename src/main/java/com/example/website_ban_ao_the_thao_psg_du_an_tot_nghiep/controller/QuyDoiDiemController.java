package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.service.QuyDoiDiemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/quy-doi-diem")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class QuyDoiDiemController {
    @Autowired
    QuyDoiDiemService quyDoiDiemService;

    @GetMapping("/list")
    public ResponseEntity<?> viewQuyDoiDiem() {
        return ResponseEntity.ok(quyDoiDiemService.listQuyDoiDiemResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuyDoiDiemId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(quyDoiDiemService.getOneQuyDoiDiem(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuyDoiDiem(@Valid @RequestBody UpdateQuyDoiDiemRequest updateQuyDoiDiemRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(quyDoiDiemService.updateQuyDoiDiem(updateQuyDoiDiemRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertQuyDoiDiem(@PathVariable("id") Integer id) {
        quyDoiDiemService.removeOrRevertQuyDoiDiem(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeQuyDoiDiem(@PathVariable("id") Integer id) {
        quyDoiDiemService.removeOrRevertQuyDoiDiem(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
