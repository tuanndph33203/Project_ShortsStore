package com.example.website_ban_ao_the_thao_ps.model.request.update_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiDiaChi;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateDiaChiRequest {
    private Integer id;
    private KhachHang khachHang;
    private String hoTen;
    private String sdt;
    private String diaChiChiTiet;
    private String maTinh;
    private String tenTinh;
    private String maQuanHuyen;
    private String tenQuanHuyen;
    private String maPhuongXa;
    private String tenPhuongXa;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhap;
    @Enumerated(EnumType.STRING)
    private TrangThaiDiaChi trangThai;
}
