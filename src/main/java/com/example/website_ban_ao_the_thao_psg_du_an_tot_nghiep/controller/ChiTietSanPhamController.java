package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiChiTietSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;


@RequestMapping("/api/chi-tiet-san-pham")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class ChiTietSanPhamController {

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping("")
    public ResponseEntity<?> getAllChiTietSanPham(@RequestParam(defaultValue = "0", name = "pageNoCTSP", required = false) Integer pageNoCTSP,
                                    @RequestParam(defaultValue = "5", name = "pageSizeCTSP", required = false) Integer pageSizeCTSP) {
        return ResponseEntity.ok(chiTietSanPhamService.pageChiTietSanPham(pageNoCTSP, pageSizeCTSP));
    }

    @GetMapping("/keywords")
    public ResponseEntity<?> searchChiTietSanPhamAdmin(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(name = "gioiTinh", required = false) Boolean gioiTinh,
                                    @RequestParam(name = "kichThuocId", required = false) Integer kichThuocId,
                                    @RequestParam(name = "cauThuId", required = false) Integer cauThuId,
                                    @RequestParam(name = "mauSacId", required = false) Integer mauSacId,
                                    @RequestParam(name = "thuongHieuId", required = false) Integer thuongHieuId,
                                    @RequestParam(name = "congNgheId", required = false) Integer congNgheId,
                                    @RequestParam(defaultValue = "0", name = "pageNoCTSP", required = false) Integer pageNoCTSP,
                                    @RequestParam(defaultValue = "5", name = "pageSizeCTSP", required = false) Integer pageSizeCTSP) {
        return ResponseEntity.ok(chiTietSanPhamService.pageSearchChiTietSanPhamAdmin( pageNoCTSP,
                pageSizeCTSP, search, gioiTinh, kichThuocId, cauThuId, mauSacId, thuongHieuId, congNgheId, ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listChiTietSanPhamAcitve() {
        return ResponseEntity.ok(chiTietSanPhamService.findByTrangThai());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChiTietSanPhamId(@PathVariable(value = "id", required = false) Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.getOneChiTietSanPham(id));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<?> getOneChiTietSanPhamBySku(@PathVariable(value = "sku", required = false) String sku) {
        return ResponseEntity.ok(chiTietSanPhamService.getOneChiTietSanPhamBySku(sku));
    }

    @GetMapping("/them-san-pham")
    public ResponseEntity<?> getByTrangThai() {
        return ResponseEntity.ok(chiTietSanPhamService.findByTrangThai());
    }

    @PutMapping("/update-so-luong")
    public ResponseEntity<?> updateSoLuong(@RequestBody UpdateChiTietSanPhamRequest updateChiTietSanPhamRequest) {
        return ResponseEntity.ok(this.chiTietSanPhamService.updateSoLuongSanPhamPending(updateChiTietSanPhamRequest));
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeChiTietSanPham(@PathVariable("id") Integer id) {
        this.chiTietSanPhamService.removeOrRevertChiTietSanPham(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertChiTietSanPham(@PathVariable("id") Integer id) {
        this.chiTietSanPhamService.removeOrRevertChiTietSanPham(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }

    @GetMapping("/get-all-sku")
    public ResponseEntity<?> getAllSkuExcept(@RequestParam("id") List<Integer> id){
        return ResponseEntity.ok(this.chiTietSanPhamService.findAllSku(id));
    }
}
