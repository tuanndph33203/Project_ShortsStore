package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.service.CauThuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/cau-thu")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class CauThuController {
    @Autowired
    CauThuService cauThuService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(cauThuService.pageCauThu(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(cauThuService.pageSearchCauThu(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listCauThuAcitve() {
        return ResponseEntity.ok(cauThuService.listCauThuResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCauThuId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(cauThuService.getOneCauThu(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCauThu(@Valid @RequestBody CreateCauThuRequest createCauThuRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(cauThuService.createCauThu(createCauThuRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCauThu(@Valid @RequestBody UpdateCauThuRequest updateCauThuRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(cauThuService.updateCauThu(updateCauThuRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertCauThu(@PathVariable("id") Integer id) {
        cauThuService.removeOrRevertCauThu(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeCauThu(@PathVariable("id") Integer id) {
        cauThuService.removeOrRevertCauThu(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
