package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.KichThuoc;
import com.example.website_ban_ao_the_thao_ps.entity.LoaiSanPham;
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
public interface KichThuocRepository extends JpaRepository<KichThuoc, Integer> {
    @Query(value = "SELECT kt FROM KichThuoc kt WHERE kt.ten LIKE %:search% or kt.ma LIKE %:search%")
    Page<KichThuoc> pageSearch(Pageable pageable, @Param("search") String search);

    List<KichThuoc> getKichThuocByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "UPDATE kich_thuoc kt SET kt.trang_thai = :trangThai WHERE kt.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);

    KichThuoc findByTen(String ten);
    KichThuoc findByTenAndId(String ten, Integer id);

}
