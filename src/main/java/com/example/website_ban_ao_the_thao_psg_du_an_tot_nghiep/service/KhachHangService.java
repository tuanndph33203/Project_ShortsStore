package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangInfo;
import com.example.website_ban_ao_the_thao_ps.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface KhachHangService {
    Page<KhachHangResponse> pageKhachHang(Integer pageNo, Integer size);

    Page<KhachHangResponse> pageSearchKhachHang(Integer pageNo, Integer size, String search, ApplicationConstant.TrangThaiTaiKhoan trangThai, Integer thuHangId);

    List<KhachHangResponse> listKhachHangResponse();
    List<KhachHangInfo> listKhachHangInfo();

    byte[] exportExcelKhachHang() throws IOException;

//    void importExcel(MultipartFile file) throws IOException, InvalidFormatException;

    KhachHangResponse create(CreateKhachHangRequest createKhachHangRequest);
    KhachHangResponse findKhachHangByEmail(String email);

    boolean checkMail(String email);

    KhachHangResponse update(UpdateKhachHangRequest updateKhachHangRequest, Integer id);

    KhachHangResponse getOne(Integer id);

    void removeOrRevert(String trangThaiKhachHang,Integer id);

//    KhachHangResponse updateSoLuongDonHangVaSoTien(Integer id, Integer soLuongDonHangThanhCong, BigDecimal soTienDaChiTieu);

//    Integer addKhachHang(KhachHangDto khachHangDto);

//    LoginMesage loginKhachHang(KhachHangDto khachHangDto);

    boolean changePassword(KhachHangDto khachHangDto, String newPassword);

    boolean suaThongTin(KhachHangDto khachHangDto, Integer id);

    void sendForgotPasswordEmail(String email);


    boolean changeEmail(KhachHangDto khachHangDto, String newEmail);

    void updateImage(String image, String email);
}