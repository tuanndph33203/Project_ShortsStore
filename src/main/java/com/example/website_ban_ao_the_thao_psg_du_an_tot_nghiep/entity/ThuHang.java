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
import jakarta.persistence.Lob;
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
@Table(name = "thu_hang")
public class ThuHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "anh")
    private String anh;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "so_tien_khach_chi_toi_thieu")
    private BigDecimal soTienKhachChiToiThieu;

    @Column(name = "so_luong_don_hang_toi_thieu")
    private Integer soLuongDonHangToiThieu;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiThuHang trangThai;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "thuHang",fetch = FetchType.LAZY)
    private List<KhachHang> khachHangList;

    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "thuHang",fetch = FetchType.LAZY)
    private List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList;
}