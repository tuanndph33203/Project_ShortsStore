package com.example.website_ban_ao_the_thao_ps.entity;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "ti_le_quy_doi_thu_hang")
public class TiLeQuyDoiThuHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chuong_trinh_tich_diem_id")
    private ChuongTrinhTichDiem chuongTrinhTichDiem;

    @ManyToOne
    @JoinColumn(name = "thu_hang_id")
    private ThuHang thuHang;

    @Column(name = "he_so_nhan")
    private Integer heSoNhan;

    @Column(name = "nguong_so_tien")
    private BigDecimal nguongSoTien;

    @Column(name = "ti_le_chuyen_doi")
    private Integer tiLeChuyendoi;

    @Column(name = "ngay_cap_nhat")
    private LocalDate ngayCapNhat;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiTiLeQuyDoiThuHang trangThai;
}
