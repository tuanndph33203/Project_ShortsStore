package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_ps.service.BanHangOnlineService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api/ban-hang-online/")
@CrossOrigin(origins = "*",maxAge = -1)
public class BanHangOnlineController {

    @Autowired
    private BanHangOnlineService banHangOnlineService;
    @PostMapping("check-out")
    public ResponseEntity<Map<String, String>> checkOut(@RequestBody JsonNode data, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String paymentUrl = banHangOnlineService.createOrder("Don hang", baseUrl, String.valueOf(data.get("giaTien").asInt()), String.valueOf(data.get("maHd").asInt())
        );
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("paymentUrl", paymentUrl);
        return ResponseEntity.ok(responseMap);
    }
    @PutMapping("cap-nhap-so-luong-gio-hang-chi-tiet/{id}")
    public ResponseEntity<?> truSoLuongGioHangChiTiet(@PathVariable(name = "id") Integer id, @RequestParam(name = "soLuong") int soLuong) {
        return ResponseEntity.ok(banHangOnlineService.capNhapSoLuongGioHangChiTiet(id, soLuong));
    }
    @PostMapping("them-thong-tin-khach-hang-khongTk")
    public ResponseEntity<?> themThongTinDiaChi(@RequestBody JsonNode data) {
        try {
            KhachHangResponse khachHang = banHangOnlineService.thongTinDiaChiKhongTk(
                    data.get("tenPhuongXa").asText(),
                    data.get("hoTen").asText(),
                    data.get("tenQuanHuyen").asText(),
                    data.get("tenTinh").asText(),
                    data.get("diaChiChiTiet").asText(),
                    data.get("email").asText(),
                    data.get("sdt").asText(),
                    data.get("idGh").asInt()
            );
            return ResponseEntity.ok(khachHang);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi thêm thông tin địa chỉ cho khách hàng.");
        }
    }

    @PutMapping(value = "sua-thong-tin-khach-hang-khongTk")
    public ResponseEntity<?> suaThongTinDiaChi(@RequestBody JsonNode data, @RequestParam(name = "idKh") Integer idKh) {
        try {
            System.out.println("Tên Xã"+ data.get("tenPhuongXa").asText());
            KhachHangResponse khachHang = banHangOnlineService.SuathongTinDiaChiKhongTk(data.get("tenPhuongXa").asText(),data.get("hoTen").asText(),data.get("tenQuanHuyen").asText(), data.get("tenTinh").asText(),
                    data.get("diaChiChiTiet").asText() ,data.get("email").asText(), data.get("sdt").asText(),idKh);
            return ResponseEntity.ok(khachHang);
        }  catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng với ID: " + e);
        }
    }

    @PostMapping("gio-hang-khong-tai-khoan")
    public ResponseEntity<?> addGioHang() {
        try {
            GioHangResponse gioHangResponse = banHangOnlineService.addGioHang();
            return  ResponseEntity.ok(gioHangResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add gio hang", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("vnpay-payment")
    public ResponseEntity<?> addHoaDon(HttpServletRequest request) {
        int paymentStatus = banHangOnlineService.luuHoaDon(request);
        String resultMessage = (paymentStatus == 1) ? "ordersuccess" : "orderfail";

        // Chuyển hướng đến URL cụ thể tùy thuộc vào kết quả
        String redirectUrl = (paymentStatus == 1) ? "http://localhost:3001/thong-bao-thanh-cong" : "http://localhost:3001/thong-bao-that-bai";
        RedirectView redirectView = new RedirectView(redirectUrl, true);

        return ResponseEntity.status(302).location(URI.create(Objects.requireNonNull(redirectView.getUrl()))).build();

    }
    @RequestMapping(value = "add-gio-hang", method = RequestMethod.POST)
    public ResponseEntity<?> addGioHang(@RequestParam(defaultValue = "") String email,
                                        @RequestParam("ref") Integer idCtsp,
                                        @RequestBody JsonNode data) {
        try {
            if (!email.isEmpty()) {
                System.out.println(email);

                return ResponseEntity.ok(banHangOnlineService.addGioHangChiTiet(idCtsp, Integer.valueOf(data.get("soLuong").asInt()), email));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email must not be empty");
            }
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @RequestMapping(value = "add-gio-hang-chi-tiet", method = RequestMethod.POST)
    public ResponseEntity<?> addGioHangChiTietKhongTk(@RequestParam("idGh") String idGh, @RequestParam("ref") Integer idCtsp, @RequestBody JsonNode data) {
        System.out.println(idGh + "hihhi");
        int idGioHang;
        try {
            idGioHang = Integer.parseInt(idGh);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid id format");
        }
        System.out.println("idGioHang" + idGioHang);
        return ResponseEntity.ok(banHangOnlineService.addGioHangChiTietKhongTk(idCtsp, data.get("soLuong").asInt(), idGh));
    }

    @PostMapping("add-hoa-don")
    public ResponseEntity<?> addHoaDon(@RequestBody JsonNode data, @RequestParam(defaultValue = "") String email) {
        try {
            int idDiaChi = data.get("idDiaChi").asInt();
            int maHd = data.has("ma") ? data.get("ma").asInt() : 0;

            System.out.println("iud" + idDiaChi);
            System.out.println("hd" + maHd);

            return ResponseEntity.ok(banHangOnlineService.addHoaDon(idDiaChi, maHd, email));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
    @PostMapping("add-hoa-don-KhongTk")
    public ResponseEntity<?> addHoaDonKhongTk(@RequestBody JsonNode data, @RequestParam(name = "idKh") Integer idKh) {
        try {
            int idDiaChi = data.get("idDiaChi").asInt();
            int maHd = data.has("ma") ? data.get("ma").asInt() : 0; // Kiểm tra trường có tồn tại không

            System.out.println("iud" + idDiaChi);
            System.out.println("hd" + maHd);

            return ResponseEntity.ok(banHangOnlineService.addHoaDonKhongTk(idDiaChi, maHd, idKh));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }



    @GetMapping("deTail-gioHang")
    public ResponseEntity<?> deTailGioHang(@RequestParam(defaultValue = "")String email){
        return ResponseEntity.ok(banHangOnlineService.getOneGioHang(email));
    }
    @GetMapping("deTail-diaChiKhachHangTk")
    public ResponseEntity<?> getOneKhachHangKhongTk(@RequestParam(name = "idKh")Integer idKh){
        System.out.println("idKh"+idKh);
        return ResponseEntity.ok(banHangOnlineService.getOneKhachHangKhongTk(idKh));
    }

    @GetMapping("gioHang-khongTk/{id}")
    public ResponseEntity<?> deTailGioHangKhongTk(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(banHangOnlineService.getOneGioHangKhongTaiKhoan(id));
    }
    @PutMapping("huy-don/{id}")
    public ResponseEntity<?>huyDonHang(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(banHangOnlineService.huyDonHangOnline(id));
    }
    @GetMapping("khach-hang")
    public ResponseEntity<KhachHangResponse> getOneKhachHang(@RequestParam(defaultValue = "") String email) {
        System.out.println("aaaa"+email);
        KhachHangResponse khachHangResponse = banHangOnlineService.getOneKhachHang(email);

        return ResponseEntity.ok(khachHangResponse);
    }

    @DeleteMapping("delete-gioHangChiTiet/{id}")
    public ResponseEntity<?> deleteGioHangChiTiet(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(banHangOnlineService.delete(id));
    }

    @PutMapping("cong-soLuong-GioHangChiTiet/{id}")
    public ResponseEntity<?> congSoLuongGioHangChiTiet(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(banHangOnlineService.congSoLuongGioHangChiTiet(id));
    }
    @PutMapping("tru-soLuong-GioHangChiTiet/{id}")
    public ResponseEntity<?> truSoLuongGioHangChiTiet(@PathVariable(name = "id") Integer id){
        return ResponseEntity.ok(banHangOnlineService.truSoLuongGioHangChiTiet(id));
    }
    @PutMapping("update-soLuong-GioHangChiTiet/{id}")
    public ResponseEntity<?> updateSoLuongGioHangChiTiet(@PathVariable(name = "id") Integer id,@RequestBody
    JsonNode data){
        return ResponseEntity.ok(banHangOnlineService.updateSoLuongTrongGioHang(id,data.get("soLuong").asInt()));
    }
    @PutMapping("update-size-GioHangChiTiet")
    public ResponseEntity<?> updateSizeGioHangChiTiet(@RequestBody
                                                      JsonNode data){
        return ResponseEntity.ok(banHangOnlineService.updateSizeGioHangChiTiet(data.get("idGhct").asInt(),data.get("idCtsp").asInt(), data.get("soLuong").asInt()));
    }
    @GetMapping("load-Size-SanPham")
    public ResponseEntity<?> loadSizeSanPham(Integer id){
        return ResponseEntity.ok(banHangOnlineService.loadSizeSanPham(id));
    }
    @GetMapping("/find-khach-hang")
    public ResponseEntity<?> findKhachHang(
            @RequestParam(name = "trangThaiTaiKhoan") ApplicationConstant.TrangThaiTaiKhoan trangThaiTaiKhoan,
            @RequestParam(name = "email") String email) {
        try {
            KhachHang khachHang = banHangOnlineService.findKhachHangtest(trangThaiTaiKhoan, email);
            if (khachHang != null) {
                return ResponseEntity.ok(khachHang);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy khách hàng với email: " + email);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tìm kiếm khách hàng.");
        }
    }
}