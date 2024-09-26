package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.dto.RequestPayment;
import com.example.website_ban_ao_the_thao_ps.dto.UpdateThanhToanHoaDon;
import com.example.website_ban_ao_the_thao_ps.service.BanHangTaiQuayService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/api/ban-hang-tai-quay")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class BanHangAdminController {


    @Autowired
    BanHangTaiQuayService banHangTaiQuayService;

    @GetMapping("/hoa-don-cho")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(banHangTaiQuayService.getAllHoaDonCho());
    }

    @GetMapping("/hoa-don-cho/{id}")
    public ResponseEntity<?> getOneHoaDon(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(banHangTaiQuayService.getOneHoaDon(id));
    }

    @GetMapping("/hoa-don-pending")
    public ResponseEntity<?> getAllHoaDonPending() {
        return ResponseEntity.ok(banHangTaiQuayService.getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon.PENDING));
    }

    @GetMapping("/hoa-don-pending-cancelled")
    public ResponseEntity<?> getAllHoaDonPendingCancelled() {
        return ResponseEntity.ok(banHangTaiQuayService.getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon.PENDING_CANCELLED));
    }

    @GetMapping("/delete-hoa-don-chi-tiet/{id}")
    public ResponseEntity<?> deleteHoaDonChiTiet(@PathVariable("id") Integer id) {
        banHangTaiQuayService.deleteHoaDonChiTiet(id);
        return ResponseEntity.ok("Delete hdct success");
    }

    @PostMapping("/create-hoa-don-cho")
    public ResponseEntity<?> createHoaDonCho(@RequestParam(defaultValue = "") String email) {
        return ResponseEntity.ok(banHangTaiQuayService.createHoaDonCho(email));
    }

    @PostMapping("/create-hoa-don-chi-tiet/{idHoaDon}")
    public ResponseEntity<?> createHoaDonChiTiet(@PathVariable("idHoaDon") Integer idHoaDon, @RequestBody JsonNode data) {
        banHangTaiQuayService.createHoaDonChiTiet(idHoaDon, data.get("idCtsp").asInt());
        return ResponseEntity.ok("Thêm sản phẩm thành công");
    }

    @PutMapping("/tru-so-luong-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> truSoLuongHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct) {
        return ResponseEntity.ok(banHangTaiQuayService.truSoLuongHoaDonChiTiet(idHdct));
    }

    @PutMapping("/cong-so-luong-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> congSoLuongHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct) {
        return ResponseEntity.ok(banHangTaiQuayService.congSoLuongHoaDonChiTiet(idHdct));
    }

    @PutMapping("/update-so-luong-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> updateSoLuongHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct, @RequestBody JsonNode data) {
        return ResponseEntity.ok(banHangTaiQuayService.updateSoLuongAndDonGiaHoaDonChiTiet(idHdct, data.get("soLuong").asInt()));
    }

    @PutMapping("/update-khach-hang-hoa-don/{idHoaDon}")
    public ResponseEntity<?> updateKhachHangHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        return ResponseEntity.ok(banHangTaiQuayService.updateKhachHangHoaDon(idHd, data.get("idKhachHang").asInt()));
    }

    @PutMapping("/update-trang-thai-hoa-don/{idHoaDon}")
    public ResponseEntity<?> updateTrangThaiHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        banHangTaiQuayService.updateTrangThaiHoaDon(idHd, ApplicationConstant.TrangThaiHoaDon.valueOf(data.get("trangThaiHoaDon").asText()), data.get("email").asText());
        return ResponseEntity.ok("Cập nhập trạng thái hoá đơn thành công");
    }

    @PutMapping("/tra-hang-hoa-don-chi-tiet/{idHdct}")
    public ResponseEntity<?> traHangHoaDonChiTiet(@PathVariable("idHdct") Integer idHdct, @RequestBody JsonNode data) {
        banHangTaiQuayService.updateTrangThaiHoaDonChiTiet(idHdct, data.get("soLuong").asInt());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/thanh-toan-hoa-don/{idHoaDon}")
    public ResponseEntity<?> thanhToanHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody UpdateThanhToanHoaDon updateThanhToanHoaDon) {
        try {
            banHangTaiQuayService.thanhToanHoaDon(idHd, updateThanhToanHoaDon);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PutMapping("/cap-nhat-dia-chi-hoa-don/{idHoaDon}")
    public ResponseEntity<?> capNhatDiaChiHoaDon(@PathVariable("idHoaDon") Integer idHd, @RequestBody JsonNode data) {
        try {
            LocalDate ngayMuonNhan = LocalDate.parse(data.get("ngayMuonNhan").asText());
            String diaChi = data.get("diaChi").asText();
            String tenNguoiNhan = data.get("tenNguoiNhan").asText();
            String sdtNguoiNhan = data.get("sdtNguoiNhan").asText();
            String sdtNguoiShip = data.get("sdtNguoiShip").asText();
            banHangTaiQuayService.updateDiaChiHoaDon(idHd, ngayMuonNhan, diaChi, tenNguoiNhan, sdtNguoiNhan, sdtNguoiShip);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @PostMapping("/payment")
//    public ResponseEntity<?> createPayment(@RequestBody RequestPayment requestPayment) throws UnsupportedEncodingException {
//        return new ResponseEntity<>(banHangTaiQuayService.payment(requestPayment), HttpStatus.OK);
//    }

    @PostMapping("/check-out")
    public ResponseEntity<Map<String, String>> checkOut(@RequestBody JsonNode data, HttpServletRequest request) {

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String paymentUrl = banHangTaiQuayService.createOrder("Don hang", baseUrl, String.valueOf(data.get("giaTien").asText()), String.valueOf(data.get("maHd").asText()));


        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("paymentUrl", paymentUrl);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("vnpay-payment")
    public ResponseEntity<?> addHoaDon(HttpServletRequest request) {
        int paymentStatus = banHangTaiQuayService.luuHoaDon(request);

        // Chuyển hướng đến URL cụ thể tùy thuộc vào kết quả
        String redirectUrl = "http://localhost:3000/free/ban-hang-tai-quay";
        RedirectView redirectView = new RedirectView(redirectUrl, true);

        return ResponseEntity.status(302).location(URI.create(Objects.requireNonNull(redirectView.getUrl()))).build();

    }
}
