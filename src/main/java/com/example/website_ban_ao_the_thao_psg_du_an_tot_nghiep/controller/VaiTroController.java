package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateVaiTroRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateVaiTroRequest;
import com.example.website_ban_ao_the_thao_ps.service.VaiTroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/vai-tro")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class VaiTroController {
    @Autowired
    VaiTroService vaiTroService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(vaiTroService.pageVaiTro(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(vaiTroService.pageSearchVaiTro(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listVaiTroAcitve() {
        return ResponseEntity.ok(vaiTroService.listVaiTroResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVaiTroId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(vaiTroService.getOneVaiTro(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createVaiTro(@Valid @RequestBody CreateVaiTroRequest createVaiTroRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(vaiTroService.createVaiTro(createVaiTroRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVaiTro(@Valid @RequestBody UpdateVaiTroRequest updateVaiTroRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(vaiTroService.updateVaiTro(updateVaiTroRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertVaiTro(@PathVariable("id") Integer id) {
        vaiTroService.removeOrRevertVaiTro(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeVaiTro(@PathVariable("id") Integer id) {
        vaiTroService.removeOrRevertVaiTro(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
