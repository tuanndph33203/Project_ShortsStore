package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_ps.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_ps.dto.NhanVienDto;
import com.example.website_ban_ao_the_thao_ps.dto.NhanVienInfo;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface NhanVienService {
    Page<NhanVienResponse>  pageNhanVien(Integer pageNo, Integer size);

    Page<NhanVienResponse> pageSearchNhanVien(Integer pageNo, Integer size, String search, ApplicationConstant.TrangThaiTaiKhoan trangThai, Integer vaiTroId);

    List<NhanVienResponse> listNhanVienResponse();
//    List<NhanVienInfo> listEmailAndSdtAndCccdNhanVien();
    NhanVienResponse findByEmail(String email);

    byte[] exportExcelNhanVien() throws IOException;

    NhanVienResponse create(CreateNhanVienRequest createNhanVienRequest);

    NhanVienResponse update(UpdateNhanVienRequest updateNhanVienRequest, Integer id);

    NhanVienResponse getOne(Integer id);
    NhanVienResponse getOneNhanVienByEmail(String email);
    void removeOrRevert(String trangThaiNhanVien, Integer id);

    LoginMesage loginNhanVien(NhanVienDto nhanVienDto);

    boolean changeEmail(NhanVienDto nhanVienDto, String newEmailNv);

    void sendForgotPasswordEmailForNhanVien(String email);

    void updatePassword(String newPassword, String email);

    void updateImageNV(String image, String email);
}