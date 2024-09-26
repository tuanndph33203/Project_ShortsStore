package com.example.website_ban_ao_the_thao_ps.model.request.update_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiVaiTro;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UpdateVaiTroRequest {
    private Integer id;
    private String ma;
    @NotBlank(message = "Tên không để trống")
    @Size(min = 1, max = 255, message = "Tên không vượt quá 255 ký tự")
    private String ten;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private TrangThaiVaiTro trangThai;
    private List<NhanVien> nhanVienList;
}
