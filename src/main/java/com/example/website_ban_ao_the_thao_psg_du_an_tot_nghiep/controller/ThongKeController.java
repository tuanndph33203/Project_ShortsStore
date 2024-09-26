package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.dto.HoaDonDTO;
import com.example.website_ban_ao_the_thao_ps.dto.HoaDonDTO2;
import com.example.website_ban_ao_the_thao_ps.dto.HoaDonDTO3;
import com.example.website_ban_ao_the_thao_ps.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/thong-ke")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class ThongKeController {

    @Autowired
    ThongKeService thongKeService;

    // done
    @GetMapping("/theo-ngay-truoc")
    public ResponseEntity<?> thongKeDonHangTheoNgay(@RequestParam(name = "ngayTruoc", required = false) Integer ngayTruoc) {
        return ResponseEntity.ok(thongKeService.findByNgayTaoTruoc(ngayTruoc));
    }

    // done
    @GetMapping("/theo-thang-truoc")
    public ResponseEntity<?> thongKeDonHangTheoThang(@RequestParam(name = "thangTruoc", required = false) Integer thangTruoc) {
        return ResponseEntity.ok(thongKeService.findByThangTaoTruoc(thangTruoc));
    }

    // done
    @GetMapping("/top-khach-hang")
    public ResponseEntity<?> topKhachHangCoSoDiemCaoNhat(@RequestParam(name = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(thongKeService.findTopByOrderBySoDiemDesc(limit));
    }

    // done
    @GetMapping("/top-selling-product")
    public ResponseEntity<?> getTopSellingProduct(@RequestParam(name = "limit", required = false) Integer limit) {
        return ResponseEntity.ok(thongKeService.getTopSellingProducts(limit));
    }

    // done
    @GetMapping("/trang-thai")
    public ResponseEntity<List<HoaDonDTO3>> countAndSumByTrangThai() {
        List<HoaDonDTO3> result = thongKeService.countAndSumByTrangThai();
        return ResponseEntity.ok(result);
    }

    // done
    @GetMapping("/ngay-tao")
    public ResponseEntity<List<HoaDonDTO>> countAndSumByNgayTao(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        List<HoaDonDTO> result = thongKeService.countAndSumByNgayTao(startDate, endDate);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/ngay-tao-money")
    public ResponseEntity<List<HoaDonDTO2>> countAndSumByNgayTaoMoney(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        // Kiểm tra nếu startDate là null, thì gán giá trị là 7 ngày trước
        if (startDate == null) {
            startDate = LocalDate.now().minusDays(6);
        }

        // Kiểm tra nếu endDate là null, thì gán giá trị là ngày hiện tại
        if (endDate == null) {
            endDate = LocalDate.now();
        }
        List<HoaDonDTO2> result = thongKeService.countAndSumByNgayTaoMoney(startDate, endDate);
        return ResponseEntity.ok(result);
    }

    // done
    @GetMapping("/tong-hoa-don")
    public ResponseEntity<?> sumHoaDon() {
       HoaDonDTO result = thongKeService.sumHoaDon();
        return ResponseEntity.ok(result);
    }

}
