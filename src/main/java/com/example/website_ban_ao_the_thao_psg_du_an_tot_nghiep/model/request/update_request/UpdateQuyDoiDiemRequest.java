package com.example.website_ban_ao_the_thao_ps.model.request.update_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateQuyDoiDiemRequest {
    private Integer id;
    private Integer diem;
    private BigDecimal soTienQuyDoi;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiQuyDoiDiem trangThai;
}
