package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.QuyDoiDiem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

    public interface QuyDoiDiemRepository extends JpaRepository<QuyDoiDiem, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE quy_doi_diem qdd SET qdd.trang_thai = :trangThai WHERE qdd.id = :id", nativeQuery = true)
    void removeOrRevert(@Param("trangThai") String trangThai, @Param("id") Integer id);

}
