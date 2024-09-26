package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCoAoRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCoAoRequest;
import com.example.website_ban_ao_the_thao_ps.service.CoAoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;
@RequestMapping("/api/co-ao")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class CoAoController {
    @Autowired
    CoAoService coAoService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(coAoService.pageCoAo(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(coAoService.pageSearchCoAo(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listCoAoAcitve() {
        return ResponseEntity.ok(coAoService.listCoAoResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoAoId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(coAoService.getOneCoAo(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCoAo(@Valid @RequestBody CreateCoAoRequest createCoAoRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(coAoService.createCoAo(createCoAoRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCoAo(@Valid @RequestBody UpdateCoAoRequest updateCoAoRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(coAoService.updateCoAo(updateCoAoRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertCoAo(@PathVariable("id") Integer id) {
        coAoService.removeOrRevertCoAo(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeCoAo(@PathVariable("id") Integer id) {
        coAoService.removeOrRevertCoAo(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
