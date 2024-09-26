package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.service.CongNgheService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/cong-nghe")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class CongNgheController {
    @Autowired
    CongNgheService congNgheService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(congNgheService.pageCongNghe(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(congNgheService.pageSearchCongNghe(pageNo, pageSize, search));
    }



    @GetMapping("/list")
    public ResponseEntity<?> listCongNgheAcitve() {
        return ResponseEntity.ok(congNgheService.listCongNgheResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCongNgheId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(congNgheService.getOneCongNghe(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCongNghe(@Valid @RequestBody CreateCongNgheRequest createCongNgheRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(congNgheService.createCongNghe(createCongNgheRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCongNghe(@Valid @RequestBody UpdateCongNgheRequest updateCongNgheRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(congNgheService.updateCongNghe(updateCongNgheRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertCongNghe(@PathVariable("id") Integer id) {
        congNgheService.removeOrRevertCongNghe(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeCongNghe(@PathVariable("id") Integer id) {
        congNgheService.removeOrRevertCongNghe(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
