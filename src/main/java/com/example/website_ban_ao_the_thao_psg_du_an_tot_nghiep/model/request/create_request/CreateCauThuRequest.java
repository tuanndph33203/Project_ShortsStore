package com.example.website_ban_ao_the_thao_ps.model.request.create_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class CreateCauThuRequest {

    private Integer id;
    private String ma;
    @NotBlank(message = "Tên không để trống")
    @Size(min = 1, max = 255, message = "Tên không vượt quá 255 ký tự")
    private String ten;
    @NotBlank(message = "Số áo không để trống")
    @Size(min = 1, max = 255, message = "Số áo không vượt quá 255 ký tự")
    private String soAo;
    private LocalDate ngayTao;
    private LocalDate ngayCapNhat;
    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiSanPham trangThai;
}
