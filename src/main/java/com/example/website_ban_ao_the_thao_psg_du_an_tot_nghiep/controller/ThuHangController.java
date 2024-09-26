package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.service.ThuHangService;
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

@RequestMapping("/api/thu-hang")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class ThuHangController {
    @Autowired
    ThuHangService thuHangService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(thuHangService.pageThuHang(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize,
                                    @RequestParam(name = "trangThai",required = false) String trangThai) {
        return ResponseEntity.ok(thuHangService.pageSearchThuHang(pageNo, pageSize, search, trangThai));
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = thuHangService.exportExcelThuHang();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listThuHangAcitve() {
        return ResponseEntity.ok(thuHangService.listThuHangResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getThuHangId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(thuHangService.getOneThuHang(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createThuHang(@Valid @RequestBody CreateThuHangRequest createThuHangRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(thuHangService.createThuHang(createThuHangRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateThuHang(@Valid @RequestBody UpdateThuHangRequest updateThuHangRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(thuHangService.updateThuHang(updateThuHangRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertThuHang(@PathVariable("id") Integer id) {
        thuHangService.removeOrRevertThuHang(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeThuHang(@PathVariable("id") Integer id) {
        thuHangService.removeOrRevertThuHang(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @GetMapping("get-list")
    public ResponseEntity<?> getAllList(){
        return ResponseEntity.ok(this.thuHangService.getAllList());
    }
}
