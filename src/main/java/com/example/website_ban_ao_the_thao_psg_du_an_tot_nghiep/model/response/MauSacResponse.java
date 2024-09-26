package com.example.website_ban_ao_the_thao_ps.model.response;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham;
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
public class MauSacResponse {
    private Integer id;
    private String ma;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private TrangThaiSanPham trangThai;
}
