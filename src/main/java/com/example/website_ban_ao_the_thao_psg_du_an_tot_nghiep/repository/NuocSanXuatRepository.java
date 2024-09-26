package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.NuocSanXuat;
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
public interface NuocSanXuatRepository extends JpaRepository<NuocSanXuat, Integer> {
    @Query(value = "SELECT nsx FROM NuocSanXuat nsx WHERE nsx.ten LIKE %:search% or nsx.ma LIKE %:search%")
    Page<NuocSanXuat> pageSearch(Pageable pageable, @Param("search") String search);

    List<NuocSanXuat> getNuocSanXuatByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE nuoc_san_xuat nsx SET nsx.trang_thai = :trangThai WHERE nsx.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
