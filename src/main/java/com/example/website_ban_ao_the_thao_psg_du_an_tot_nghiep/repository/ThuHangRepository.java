package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ThuHangRepository extends JpaRepository<ThuHang, Integer> {

    @Query(value = "SELECT th FROM ThuHang th " +
            "WHERE (:search is null or th.ma LIKE %:search% or " +
            "th.ten LIKE %:search% or " +
            "CAST(th.soTienKhachChiToiThieu AS string ) LIKE %:search% or " +
            "CAST(th.soLuongDonHangToiThieu AS string ) LIKE %:search% or " +
            "th.moTa LIKE %:search%)" +
            "AND (:trangThai is null or th.trangThai = :trangThai)")
    Page<ThuHang> pageSearch(Pageable pageable, @Param("search") String search, @Param("trangThai") ApplicationConstant.TrangThaiThuHang trangThai);

    List<ThuHang> getThuHangByTrangThai(ApplicationConstant.TrangThaiThuHang trangThai);

    @Query(value = "select c from ThuHang c where c.trangThai= 'ACTIVE'")
    List<ThuHang> getThuHangsByTrangThaiActive();

    @Transactional
    @Modifying
    @Query(value = "UPDATE thu_hang th SET th.trang_thai = :trangThai WHERE th.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);

    ThuHang findBySoLuongDonHangToiThieuAndSoTienKhachChiToiThieu(Integer soLuongDonHangToiThieu, BigDecimal soTienKhachChiToiThieu);

    @Query("SELECT thuHang, COUNT(khachHang) as soLuongKhachHang FROM ThuHang thuHang LEFT JOIN thuHang.khachHangList khachHang GROUP BY thuHang ORDER BY thuHang.soTienKhachChiToiThieu ASC, thuHang.soLuongDonHangToiThieu ASC")
    List<Object[]> selectCountThuHang();
}
