package com.example.website_ban_ao_the_thao_ps.model.response;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
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
public class ThuHangResponse {

    private Integer id;
    private String ma;
    private String ten;
    private String anh;
    private String moTa;
    private BigDecimal soTienKhachChiToiThieu;
    private Integer soLuongDonHangToiThieu;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiThuHang trangThai;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    private List<KhachHang> khachHangList;
    private List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList;
}
