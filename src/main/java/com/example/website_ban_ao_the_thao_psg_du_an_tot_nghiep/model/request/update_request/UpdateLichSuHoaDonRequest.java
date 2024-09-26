package com.example.website_ban_ao_the_thao_ps.model.request.update_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.LoaiLichSuHoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateLichSuHoaDonRequest {
    private Integer Id;
    private NhanVien nhanVien;
    private HoaDon hoaDon;
    private LocalDateTime ngayTao;
    private String moTa;
    @Enumerated(EnumType.STRING)
    private LoaiLichSuHoaDon loaiLichSuHoaDon;
}
