package com.example.website_ban_ao_the_thao_ps.model.request.create_request;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.LichSuHoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.VaiTro;
import com.example.website_ban_ao_the_thao_ps.service.AgeConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
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
public class CreateNhanVienRequest {

    private Integer id;

    private String ma;

//    @NotBlank(message = "Tên không để trống")
//    @Size(min = 5, max = 255, message = "Tên không vượt quá 255 ký tự")
    private String ten;

    private VaiTro vaiTro;

    private String anh;

//    @NotNull(message = "Giới tính không được để trống")
    private Boolean gioiTinh;

//    @NotNull(message = "Ngày sinh không được để trống")
//    @Past(message = "Ngày sinh không được lớn hơn ngày hiện tại")
//    @AgeConstraint(message = "Phải trên 18 tuổi")
    private LocalDate ngaySinh;

//    @NotBlank(message = "Địa chỉ không để trống")
//    @Size(min = 5, max = 255, message = "Địa chỉ không vượt quá 255 ký tự")
    private String diaChi;

//    @NotBlank(message = "SĐT không được để trống")
//    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ. Phải bắt đầu bằng số 0 và có 10 chữ số.")
    private String sdt;

//    @NotBlank(message = "Số căn cước công dân không để trống")
//    @Pattern(regexp = "^\\d{12}$", message = "Số căn cước công dân không hợp lệ. Phải có 12 chữ số.")
    private String soCanCuocCongDan;

//    @NotBlank(message = "Email không được để trống")
//    @Size(min = 8, max = 255, message = "Email không vượt quá 255 ký tự")
//    @Email(message = "Email sai định dạng")
    private String email;

//    @NotBlank(message = "Mật khẩu không để trống")
//    @Size(min = 8, max = 255, message = "Mật khẩu không vượt quá 255 ký tự")
    private String matKhau;

    private LocalDate ngayTao;

    private LocalDate ngayCapNhat;

    @Enumerated(EnumType.STRING)
    private ApplicationConstant.TrangThaiTaiKhoan trangThai;
    private List<LichSuHoaDon> lichSuHoaDonList;
    private List<HoaDon> hoaDonList;
}
