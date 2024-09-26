package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_ps.service.ChuongTrinhTichDiemService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/chuong-trinh-tich-diem")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class ChuongTrinhTichDiemController {
    @Autowired
   private ChuongTrinhTichDiemService chuongTrinhTichDiemService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(chuongTrinhTichDiemService.pageChuongTrinhTichDiem(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(name = "trangThai",required = false) String trangThai) {
        return ResponseEntity.ok(chuongTrinhTichDiemService.pageSearchChuongTrinhTichDiem(pageNo, pageSize, search, trangThai));
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = chuongTrinhTichDiemService.exportExcelTichDiem();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listChuongTrinhTichDiemAcitve() {
        return ResponseEntity.ok(chuongTrinhTichDiemService.listChuongTrinhTichDiemResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChuongTrinhTichDiemId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chuongTrinhTichDiemService.getOneChuongTrinhTichDiem(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChuongTrinhTichDiem(@Valid @RequestBody CreateChuongTrinhTichDiemRequest createChuongTrinhTichDiemRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(chuongTrinhTichDiemService.createChuongTrinhTichDiem(createChuongTrinhTichDiemRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChuongTrinhTichDiem(@Valid @RequestBody UpdateChuongTrinhTichDiemRequest updateChuongTrinhTichDiemRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(chuongTrinhTichDiemService.updateChuongTrinhTichDiem(updateChuongTrinhTichDiemRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertChuongTrinhTichDiem(@PathVariable("id") Integer id) {
        chuongTrinhTichDiemService.removeOrRevertChuongTrinhTichDiem(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeChuongTrinhTichDiem(@PathVariable("id") Integer id) {
        chuongTrinhTichDiemService.removeOrRevertChuongTrinhTichDiem(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("RemoveSuccess ");
    }
}
