package com.example.website_ban_ao_the_thao_ps.model.request.update_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDonChiTiet;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.LichSuHoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateHoaDonRequest {

    private Integer id;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private String ma;
    private LocalDate ngayTao;
    private LocalDate ngayMuonNhan;
    private Integer phanTramGiamGia;
    private Double soDiemCong;
    private Double soDiemTru;
    private BigDecimal soTienChuyenKhoan;
    private BigDecimal soTienMat;
    private BigDecimal soTienVanChuyen;
    private String maGiaoDichTienMat;
    private String maGiaoDichChuyenKhoan;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.HinhThucThanhToan phuongThucThanhToan;
    private BigDecimal thanhTien;
    private String diaChi;
    private String tenNguoiNhan;
    private String sdtNguoiNhan;
    private String sdtNguoiShip;
    private String moTa;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.HinhThucBanHang hinhThucBanHang;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiHoaDon trangThai;
    private List<LichSuHoaDon> lichSuHoaDonList;
    private List<HoaDonChiTiet> hoaDonChiTietList;
}
