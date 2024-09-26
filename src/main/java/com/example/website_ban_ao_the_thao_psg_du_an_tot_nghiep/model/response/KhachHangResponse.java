package com.example.website_ban_ao_the_thao_ps.model.response;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiTaiKhoan;
import com.example.website_ban_ao_the_thao_ps.entity.DiaChi;
import com.example.website_ban_ao_the_thao_ps.entity.GioHang;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class KhachHangResponse {
    private Integer id;
    private ThuHang thuHang;
    private String ma;
    private String ten;
    private String anh;
    private Boolean gioiTinh;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate ngaySinh;
    private String diaChi;
    private String sdt;
    private String email;
    private String matKhau;
    private Integer soLuongDonHangThanhCong;
    private BigDecimal soTienDaChiTieu;
    private Double soDiem;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private TrangThaiTaiKhoan trangThai;
    private List<GioHang> gioHangList;
    private List<HoaDon> hoaDonList;
    private List<DiaChi> diaChiList;
}
