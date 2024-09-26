package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.CauThu;
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
public interface CauThuRepository extends JpaRepository<CauThu, Integer> {
    @Query(value = "SELECT ct FROM CauThu ct WHERE ct.ten LIKE %:search% or ct.ma LIKE %:search% or ct.soAo LIKE %:search%")
    Page<CauThu> pageSearch(Pageable pageable, @Param("search") String search);

    List<CauThu> getCauThuByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cau_thu ct SET ct.trang_thai = :trangThai WHERE ct.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
