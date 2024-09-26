package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.service.QuyDinhService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/quy-dinh")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class QuyDinhController {
    @Autowired
    QuyDinhService quyDinhService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "10", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(quyDinhService.pageQuyDinh(pageNo, pageSize));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listMauSacAcitve() {
        return ResponseEntity.ok(quyDinhService.listQuyDinhResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuyDinhId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(quyDinhService.getOneQuyDinh(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuyDinh(@Valid @RequestBody CreateQuyDinhRequest createQuyDinhRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(quyDinhService.createQuyDinh(createQuyDinhRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuyDinh(@Valid @RequestBody UpdateQuyDinhRequest updateQuyDinhRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(quyDinhService.updateQuyDinh(updateQuyDinhRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertQuyDinh(@PathVariable("id") Integer id) {
        quyDinhService.removeOrRevertQuyDinh(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeQuyDinh(@PathVariable("id") Integer id) {
        quyDinhService.removeOrRevertQuyDinh(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        this.quyDinhService.delete(id);
        return ResponseEntity.ok("Remove Success");
    }
}
