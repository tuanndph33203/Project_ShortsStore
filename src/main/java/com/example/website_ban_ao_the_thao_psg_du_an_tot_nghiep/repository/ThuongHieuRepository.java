package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ThuongHieu;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {
    @Query(value = "SELECT th FROM ThuongHieu th WHERE th.ten LIKE %:search% or th.ma LIKE %:search%")
    Page<ThuongHieu> pageSearch(Pageable pageable, @Param("search") String search);

    List<ThuongHieu> getThuongHieuByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE thuong_hieu th SET th.trang_thai = :trangThai WHERE th.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
