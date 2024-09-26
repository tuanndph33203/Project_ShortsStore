package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TiLeQuyDoiThuHangRepository extends JpaRepository<TiLeQuyDoiThuHang, Integer> {

    @Query(value = "select c.thuHang from TiLeQuyDoiThuHang c")
    List<TiLeQuyDoiThuHang> findThuHangInTiLeQuyDoiThuHang();

    @Query(value = "select c.thuHang from TiLeQuyDoiThuHang c")
    TiLeQuyDoiThuHang findThuHangInTiLeQuyDoiThuHang2();

    @Query(value = "select c from TiLeQuyDoiThuHang c where c.chuongTrinhTichDiem.id = :id")
    List<TiLeQuyDoiThuHang> findTiLeQuyDoiThuHangByChuongTrinhTichDiemId(Integer id);

    TiLeQuyDoiThuHang findByChuongTrinhTichDiemAndThuHang(ChuongTrinhTichDiem chuongTrinhTichDiem, ThuHang thuHang);

    List<TiLeQuyDoiThuHang> getTiLeQuyDoiThuHangByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    List<TiLeQuyDoiThuHang> getTiLeQuyDoiThuHangByChuongTrinhTichDiem(ChuongTrinhTichDiem chuongTrinhTichDiem);

    @Transactional
    @Modifying
    @Query(value = "UPDATE thuong_hieu th SET th.trang_thai = :trangThai WHERE th.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
