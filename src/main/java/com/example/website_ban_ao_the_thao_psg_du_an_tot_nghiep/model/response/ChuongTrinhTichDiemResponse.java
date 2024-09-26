package com.example.website_ban_ao_the_thao_ps.model.response;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ChuongTrinhTichDiemResponse {
    private Integer id;
    private String ma;
    private String ten;
    private LocalDateTime thoiGianBatDau;
    private LocalDateTime thoiGianKetThuc;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiChuongTrinhTichDiem trangThai;
    private List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList;
}
