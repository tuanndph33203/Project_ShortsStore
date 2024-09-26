package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChuongTrinhTichDiemRepository extends JpaRepository<ChuongTrinhTichDiem , Integer> {

//    @Query(value = "SELECT th FROM ThuHang th " +
//            "WHERE (:search is null or th.ma LIKE %:search% or " +
//            "th.ten LIKE %:search% or " +
//            "CAST(th.soTienKhachChiToiThieu AS string ) LIKE %:search% or " +
//            "CAST(th.soLuongDonHangToiThieu AS string ) LIKE %:search% or " +
//            "th.moTa LIKE %:search%)" +
//            "AND (:trangThai is null or th.trangThai = :trangThai)")
//    Page<ThuHang> pageSearch(Pageable pageable, @Param("search") String search, @Param("trangThai") ApplicationConstant.TrangThaiThuHang trangThai);

    @Query(value = "SELECT cttd FROM ChuongTrinhTichDiem cttd " +
            "WHERE (:search IS null OR cttd.ma LIKE %:search% OR " +
            "cttd.ten LIKE %:search%)" +
            "AND (:trangThai IS NULL OR cttd.trangThai = :trangThai)")
    Page<ChuongTrinhTichDiem> pageSearch(Pageable pageable, @Param("search") String search, @Param("trangThai") ApplicationConstant.TrangThaiChuongTrinhTichDiem trangThai);

    List<ChuongTrinhTichDiem> getChuongTrinhTichDiemByTrangThai(ApplicationConstant.TrangThaiChuongTrinhTichDiem trangThai);

    List<ChuongTrinhTichDiem> findByThoiGianKetThucBeforeAndTrangThaiNot(LocalDateTime localDateTime, ApplicationConstant.TrangThaiChuongTrinhTichDiem trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE chuong_trinh_tich_diem cttd SET cttd.trang_thai = :trangThai WHERE cttd.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
