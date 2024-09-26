package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.VaiTro;
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
public interface VaiTroRepository extends JpaRepository<VaiTro, Integer> {
    @Query(value = "SELECT vt FROM VaiTro vt WHERE vt.ten LIKE %:search% or vt.ma LIKE %:search%")
    Page<VaiTro> pageSearch(Pageable pageable, @Param("search") String search);

    List<VaiTro> getVaiTroByTrangThai(ApplicationConstant.TrangThaiVaiTro trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE vai_tro vt SET vt.trang_thai = :trangThai WHERE vt.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);
}
