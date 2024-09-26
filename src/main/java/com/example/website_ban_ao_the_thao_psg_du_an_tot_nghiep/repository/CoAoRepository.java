package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.CoAo;
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
public interface CoAoRepository extends JpaRepository<CoAo, Integer> {
    @Query(value = "SELECT ca FROM CoAo ca WHERE ca.ten LIKE %:search% or ca.ma LIKE %:search%")
    Page<CoAo> pageSearch(Pageable pageable, @Param("search") String search);

    List<CoAo> getCoAoByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE co_ao ca SET ca.trang_thai = :trangThai WHERE ca.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
