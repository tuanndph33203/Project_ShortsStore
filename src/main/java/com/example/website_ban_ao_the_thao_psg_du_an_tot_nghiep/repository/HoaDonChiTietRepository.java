package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDonChiTiet;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet,Integer> {

    List<HoaDonChiTiet> getHoaDonChiTietByHoaDon(HoaDon hoaDon);

    @Query("SELECT hdct.chiTietSanPham, SUM(hdct.soLuong) AS totalSold " +
            "FROM HoaDonChiTiet hdct " +
            "WHERE hdct.hoaDon.trangThai = 'APPROVED' " +
            "GROUP BY hdct.chiTietSanPham " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProduct(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE hoa_don_chi_tiet hdct SET hdct.trang_thai = :trangThai WHERE hdct.id = :id", nativeQuery = true)
    void updateTrangThaiHoaDonChiTiet(@Param("trangThai") ApplicationConstant.TrangThaiHoaDonChiTiet trangThai, @Param("id") Integer id);
}
