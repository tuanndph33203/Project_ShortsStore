package com.example.website_ban_ao_the_thao_ps.service;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.dto.RequestPayment;
import com.example.website_ban_ao_the_thao_ps.dto.ResponsePayment;
import com.example.website_ban_ao_the_thao_ps.dto.UpdateThanhToanHoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonChiTietResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public interface BanHangTaiQuayService {

    HoaDonResponse createHoaDonCho(String email);

    List<HoaDonResponse> getAllHoaDonCho();

    void deleteHoaDonChiTiet(Integer id);

    HoaDonResponse updateKhachHangHoaDon(Integer idHd,Integer idKhachHang);

    HoaDonResponse updateDiaChiHoaDon(Integer idHd,LocalDate ngayMuonNhan,String diaChi,
                                      String tenNguoiNhan,
                                      String sdtNguoiNhan,
                                      String sdtNguoiShip);

    HoaDonResponse getOneHoaDon(Integer id);

    void createHoaDonChiTiet(Integer idHoaDon,Integer idCtsp);

    HoaDonChiTietResponse truSoLuongHoaDonChiTiet(Integer idHdct);

    HoaDonChiTietResponse congSoLuongHoaDonChiTiet(Integer idHdct);

    HoaDonChiTietResponse updateSoLuongAndDonGiaHoaDonChiTiet(Integer idHdct, Integer soLuong);

    void updateTrangThaiHoaDon(Integer idHoaDon, ApplicationConstant.TrangThaiHoaDon trangThaiHoaDon,String email);

    void updateTrangThaiHoaDonChiTiet(Integer id,Integer soLuong);

    void thanhToanHoaDon(Integer idHd,UpdateThanhToanHoaDon updateThanhToanHoaDon);

    List<HoaDonResponse> getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon trangThai);

//    ResponsePayment payment(RequestPayment requestPayment) throws UnsupportedEncodingException;
    int luuHoaDon(HttpServletRequest request);
    String createOrder( String orderInfor, String urlReturn,String gia,String maHd);

}
