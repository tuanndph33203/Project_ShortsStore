package com.example.website_ban_ao_the_thao_ps.model.request.create_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateThuHangRequest {

    private Integer id;

    private String ma;

    @NotBlank(message = "Tên không để trống")
    @Size(min = 1, max = 255, message = "Tên không vượt quá 255 ký tự")
    private String ten;

    private String anh;

    private String moTa;

    @NotNull(message = "Số tiền khách chi tối thiểu không để trống")
    @Min(value = 0, message = "Số tiền khách chi tối thiểu phải là số nguyên và lớn hơn 0")
    private BigDecimal soTienKhachChiToiThieu;

    @NotNull(message = "Số lượng đơn hàng tối thiểu không để trống")
    @Min(value = 0, message = "Số lượng đơn hàng tối thiểu phải là số nguyên và lớn hơn 0")
    private Integer soLuongDonHangToiThieu;

    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiThuHang trangThai;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;
    private List<KhachHang> khachHangList;
    private List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList;
}
