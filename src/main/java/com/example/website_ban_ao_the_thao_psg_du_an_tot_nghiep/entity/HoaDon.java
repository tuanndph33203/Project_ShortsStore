package com.example.website_ban_ao_the_thao_ps.entity;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "nhan_vien_id")
    private NhanVien nhanVien;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_muon_nhan")
    private LocalDate ngayMuonNhan;

    @Column(name = "phan_tram_giam_gia")
    private Integer phanTramGiamGia;

    @Column(name = "so_diem_cong")
    private Double soDiemCong;

    @Column(name = "so_diem_tru")
    private Double soDiemTru;

    @Column(name = "so_tien_chuyen_khoan")
    private BigDecimal soTienChuyenKhoan;

    @Column(name = "so_tien_mat")
    private BigDecimal soTienMat;

    @Column(name = "so_tien_van_chuyen")
    private BigDecimal soTienVanChuyen;

    @Column(name = "ma_giao_dich_tien_mat")
    private String maGiaoDichTienMat;

    @Column(name = "ma_giao_dich_chuyen_khoan")
    private String maGiaoDichChuyenKhoan;

    @Column(name = "phuong_thuc_thanh_toan")
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.HinhThucThanhToan phuongThucThanhToan;

    @Column(name = "thanh_tien")
    private BigDecimal thanhTien;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "ten_nguoi_nhan")
    private String tenNguoiNhan;

    @Column(name = "sdt_nguoi_nhan")
    private String sdtNguoiNhan;

    @Column(name = "sdt_nguoi_ship")
    private String sdtNguoiShip;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "hinh_thuc_ban_hang")
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.HinhThucBanHang hinhThucBanHang;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiHoaDon trangThai;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    private List<LichSuHoaDon> lichSuHoaDonList;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    private List<HoaDonChiTiet> hoaDonChiTietList;
}
