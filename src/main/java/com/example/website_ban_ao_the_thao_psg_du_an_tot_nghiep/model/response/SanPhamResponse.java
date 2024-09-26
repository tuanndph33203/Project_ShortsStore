package com.example.website_ban_ao_the_thao_ps.model.response;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.*;
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
public class SanPhamResponse {
    private Integer id;
    private MauSac mauSac;
    private LoaiSanPham loaiSanPham;
    private ChatLieu chatLieu;
    private DongSanPham dongSanPham;
    private NhaSanXuat nhaSanXuat;
    private ThuongHieu thuongHieu;
    private NuocSanXuat nuocSanXuat;
    private CongNghe congNghe;
    private CoAo coAo;
    private CauThu cauThu;
    private LocalDate namSanXuat;
    private String ma;
    private String ten;
    private BigDecimal gia;
    private String moTa;
    private Boolean gioiTinh;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiSanPham trangThai;
    private List<AnhSanPham> anhSanPhamList;
    private List<ChiTietSanPham> chiTietSanPhamList;
}
