package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.KichThuoc;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.service.SanPhamService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/san-pham")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class SanPhamController {

    @Autowired
    SanPhamService sanPhamService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "12", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(sanPhamService.pageSanPham(pageNo, pageSize));
    }

    @GetMapping("/keywords")
    public ResponseEntity<?> searchAdmin(@RequestParam(name = "search", required = false) String search,
                                         @RequestParam(name = "gioiTinh", required = false) Boolean gioiTinh,
                                         @RequestParam(name = "cauThuId", required = false) Integer cauThuId,
                                         @RequestParam(name = "coAoId", required = false) Integer coAoId,
                                         @RequestParam(name = "mauSacId", required = false) Integer mauSacId,
                                         @RequestParam(name = "loaiSanPhamId", required = false) Integer loaiSanPhamId,
                                         @RequestParam(name = "chatLieuId", required = false) Integer chatLieuId,
                                         @RequestParam(name = "dongSanPhamId", required = false) Integer dongSanPhamId,
                                         @RequestParam(name = "nhaSanXuatId", required = false) Integer nhaSanXuatId,
                                         @RequestParam(name = "thuongHieuId", required = false) Integer thuongHieuId,
                                         @RequestParam(name = "nuocSanXuatId", required = false) Integer nuocSanXuatId,
                                         @RequestParam(name = "congNgheId", required = false) Integer congNgheId,
                                         @RequestParam(name = "giaMin", required = false) BigDecimal giaMin, @RequestParam(name = "giaMax", required = false)BigDecimal giaMax,
                                         @RequestParam(name = "trangThai", required = false) ApplicationConstant.TrangThaiSanPham trangThai,
                                         @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                         @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(sanPhamService.pageSearchSanPhamAdmin(pageNo, pageSize, search,gioiTinh, cauThuId,
                coAoId, mauSacId, loaiSanPhamId, chatLieuId, dongSanPhamId, nhaSanXuatId, thuongHieuId, nuocSanXuatId,
                congNgheId, trangThai, giaMin, giaMax));
    }
    @GetMapping("/list")
    public ResponseEntity<?> listSanPhamAcitve() {
        return ResponseEntity.ok(sanPhamService.listSanPhamResponse());
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = sanPhamService.exportExcelSanPham();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSanPhamId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(sanPhamService.getOneSanPham(id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertSanPham(@PathVariable("id") Integer id) {
        sanPhamService.removeOrRevertSanPham(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeSanPham(@PathVariable("id") Integer id) {
        sanPhamService.removeOrRevertSanPham(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSanPham(@RequestBody CreateSanPhamRequest createSanPhamRequest){
        return ResponseEntity.ok(this.sanPhamService.createSanPham(createSanPhamRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSanPham(@Valid @RequestBody UpdateSanPhamRequest updateSanPhamRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(sanPhamService.updateSanPham(updateSanPhamRequest, id));
    }
}
