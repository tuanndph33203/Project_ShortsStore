package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.service.TiLeQuyDoiThuHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/ti-le-quy-doi-thu-hang")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class TiLeQuyDoiThuHangController {
    @Autowired
    TiLeQuyDoiThuHangService tiLeQuyDoiThuHangService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(tiLeQuyDoiThuHangService.pageTiLeQuyDoiThuHang(pageNo, pageSize));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listTiLeQuyDoiThuHangAcitve() {
        return ResponseEntity.ok(tiLeQuyDoiThuHangService.listTiLeQuyDoiThuHangResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTiLeQuyDoiThuHangId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(tiLeQuyDoiThuHangService.getOneTiLeQuyDoiThuHang(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTiLeQuyDoiThuHang(@Valid @RequestBody CreateTiLeQuyDoiThuHangRequest createTiLeQuyDoiThuHangRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(tiLeQuyDoiThuHangService.createTiLeQuyDoiThuHang(createTiLeQuyDoiThuHangRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTiLeQuyDoiThuHang(@Valid @RequestBody UpdateTiLeQuyDoiThuHangRequest updateTiLeQuyDoiThuHangRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(tiLeQuyDoiThuHangService.updateTiLeQuyDoiThuHang(updateTiLeQuyDoiThuHangRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertTiLeQuyDoiThuHang(@PathVariable("id") Integer id) {
        tiLeQuyDoiThuHangService.removeOrRevertTiLeQuyDoiThuHang(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeTiLeQuyDoiThuHang(@PathVariable("id") Integer id) {
        tiLeQuyDoiThuHangService.removeOrRevertTiLeQuyDoiThuHang(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
