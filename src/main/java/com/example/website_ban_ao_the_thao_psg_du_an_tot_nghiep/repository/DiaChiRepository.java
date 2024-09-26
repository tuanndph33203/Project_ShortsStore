package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.entity.DiaChi;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
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
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {

    @Query(value = "SELECT * FROM dia_chi WHERE ho_ten LIKE %?1% OR sdt LIKE %?1% ", nativeQuery = true)
    Page<DiaChi> pageSearchActive(String searchString, Pageable pageable);

    List<DiaChi> findAllByKhachHang(KhachHang khachHang);




    @Query(value = "SELECT * FROM dia_chi ", nativeQuery = true)
    Page<DiaChi> pageACTIVE(Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE dia_chi dc SET dc.trang_thai = :trangThai WHERE dc.id = :id", nativeQuery = true)
    void update(@Param("trangThai") String trangThai, @Param("id") Integer id);
}




