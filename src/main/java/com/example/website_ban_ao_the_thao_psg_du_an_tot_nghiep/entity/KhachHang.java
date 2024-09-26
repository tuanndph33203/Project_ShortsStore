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
import jakarta.persistence.Lob;
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
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "thu_hang_id")
    private ThuHang thuHang;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "ten")
    private String ten;

    @Column(name = "anh")
    private String anh;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "email")
    private String email;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "ma")
    private String ma;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "so_luong_don_hang_thanh_cong")
    private Integer soLuongDonHangThanhCong;

    @Column(name = "so_tien_da_chi_tieu")
    private BigDecimal soTienDaChiTieu;

    @Column(name = "so_diem")
    private Double soDiem;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    public KhachHang() {

    }

    public KhachHang(Boolean gioiTinh, String ten, String email, String sdt,String ma, String matKhau) {
        this.gioiTinh = gioiTinh;
        this.ten = ten;
        this.email = email;
        this.sdt = sdt;
        this.ma = ma;
        this.matKhau = matKhau;
    }

    @Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiTaiKhoan trangThai;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "khachHang",fetch = FetchType.LAZY)
    private List<GioHang> gioHangList;


    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "khachHang",fetch = FetchType.LAZY)
    private List<HoaDon> hoaDonList;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "khachHang",fetch = FetchType.LAZY)
    private List<DiaChi> diaChiList;
}
