package com.example.website_ban_ao_the_thao_ps.dto;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateThanhToanHoaDon {
//    private Integer idHoaDon;
    private String moTa;
    private LocalDate ngayMuonNhan;
    private Integer phanTramGiamGia;
    private BigDecimal soTienVanChuyen;
    private Double soDiemTru;
    private ApplicationConstant.HinhThucThanhToan phuongThucThanhToan;
    private BigDecimal thanhTien;
    private String diaChi;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String sdtNguoiShip;
    private String email;
    private ApplicationConstant.HinhThucBanHang hinhThucBanHang;
}
