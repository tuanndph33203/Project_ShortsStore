package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.CongNghe;
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
public interface CongNgheRepository extends JpaRepository<CongNghe, Integer> {
    @Query(value = "SELECT cn FROM CongNghe cn WHERE cn.ten LIKE %:search% or cn.ma LIKE %:search% or cn.moTa LIKE %:search%")
    Page<CongNghe> pageSearch(Pageable pageable, @Param("search") String search);

    List<CongNghe> getCongNgheByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cong_nghe cn SET cn.trang_thai = :trangThai WHERE cn.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
