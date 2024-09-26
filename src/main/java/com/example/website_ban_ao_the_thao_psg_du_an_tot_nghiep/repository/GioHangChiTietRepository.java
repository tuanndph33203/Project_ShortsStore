package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.GioHang;
import com.example.website_ban_ao_the_thao_ps.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet,Integer> {
    List<GioHangChiTiet>findGioHangChiTietByTrangThaiAndGioHang(ApplicationConstant.TrangThaiGioHang trangThai, GioHang gioHang);

}
