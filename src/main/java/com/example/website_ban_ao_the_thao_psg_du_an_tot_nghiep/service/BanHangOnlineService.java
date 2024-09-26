package com.example.website_ban_ao_the_thao_ps.service;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangChiTietResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface BanHangOnlineService {


    GioHangChiTietResponse congSoLuongGioHangChiTiet(Integer id);
    GioHangChiTietResponse delete(Integer id);
    GioHangChiTietResponse updateSoLuongTrongGioHang(Integer id,Integer soLuong);
    GioHangChiTietResponse truSoLuongGioHangChiTiet(Integer id);
    GioHangResponse getOneGioHang(String email);
    GioHangResponse getOneGioHangKhongTaiKhoan(Integer id);
    KhachHangResponse getOneKhachHang(String email);
    GioHangChiTietResponse addGioHangChiTiet(Integer idCtsp,Integer soLuong, String email);
    GioHangChiTietResponse addGioHangChiTietKhongTk(Integer idCtsp,Integer soLuong, String id);
    HoaDonResponse addHoaDon(Integer idDiaChi,Integer ma,String email);
    HoaDonResponse addHoaDonKhongTk(Integer idDiaChi,Integer ma,Integer id);
     String createOrder( String orderInfor, String urlReturn,String gia,String maHd);
     GioHangResponse addGioHang();
     KhachHangResponse getOneKhachHangKhongTk(Integer id);
    GioHangChiTietResponse capNhapSoLuongGioHangChiTiet(Integer id, Integer soLuong);
     int luuHoaDon(HttpServletRequest request);
     GioHangChiTietResponse updateSizeGioHangChiTiet(Integer idGhct ,Integer idCtsp,Integer soLuong);
     List<ChiTietSanPham> loadSizeSanPham(Integer idCtsp);
    KhachHangResponse thongTinDiaChiKhongTk(String tenXa,String hoTen , String tenQuan,String tenTinh,String diaChiChiTiet, String email, String sdt,Integer idGh);
    KhachHangResponse SuathongTinDiaChiKhongTk(String tenXa,String hoTen , String tenQuan,String tenTinh,String diaChiChiTiet, String email, String sdt,Integer idKh);
    HoaDonResponse huyDonHangOnline(Integer id);
    KhachHang findKhachHangtest( ApplicationConstant.TrangThaiTaiKhoan trangThaiTaiKhoan,String email);
}
