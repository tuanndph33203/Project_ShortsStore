package com.example.website_ban_ao_the_thao_ps.repository;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.SanPham;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query("SELECT sp FROM SanPham sp " +
            "LEFT JOIN sp.coAo ca " +
            "LEFT JOIN sp.cauThu ct " +
            "LEFT JOIN sp.mauSac ms " +
            "LEFT JOIN sp.loaiSanPham lsp " +
            "LEFT JOIN sp.chatLieu cl " +
            "LEFT JOIN sp.dongSanPham dsp " +
            "LEFT JOIN sp.nhaSanXuat nsx " +
            "LEFT JOIN sp.thuongHieu th " +
            "LEFT JOIN sp.nuocSanXuat ns " +
            "LEFT JOIN sp.congNghe cn " +
            "WHERE (:search is null or sp.ten like %:search% " +
            "or sp.ma like %:search% " +
            "or sp.cauThu.ten like %:search% " +
            "or sp.cauThu.soAo like %:search%) " +
            "and (:gioiTinh is null or sp.gioiTinh = :gioiTinh) " +
            "and (:coAoId is null or ca.id = :coAoId) " +
            "and (:cauThuId is null or ct.id = :cauThuId) " +
            "and (:mauSacId is null or ms.id = :mauSacId) " +
            "and (:loaiSanPhamId is null or lsp.id = :loaiSanPhamId) " +
            "and (:chatLieuId is null or cl.id = :chatLieuId) " +
            "and (:dongSanPhamId is null or dsp.id = :dongSanPhamId) " +
            "and (:nhaSanXuatId is null or nsx.id = :nhaSanXuatId) " +
            "and (:thuongHieuId is null or th.id = :thuongHieuId) " +
            "and (:nuocSanXuatId is null or ns.id = :nuocSanXuatId) " +
            "and (:congNgheId is null or cn.id = :congNgheId) " +
            "and (:trangThai is null or sp.trangThai = :trangThai) "+
            "and ((:giaMin is null and :giaMax is null) or (sp.gia >= :giaMin and sp.gia <= :giaMax))")

    Page<SanPham> pageSearchAdmin(Pageable pageable, @Param("search") String search,@Param("gioiTinh") Boolean gioiTinh, @Param("cauThuId") Integer cauThuId, @Param("coAoId") Integer coAoId,
                                  @Param("mauSacId") Integer mauSacId, @Param("loaiSanPhamId") Integer loaiSanPhamId,
                                  @Param("chatLieuId") Integer chatLieuId, @Param("dongSanPhamId") Integer dongSanPhamId,
                                  @Param("nhaSanXuatId") Integer nhaSanXuatId, @Param("thuongHieuId") Integer thuongHieuId,
                                  @Param("nuocSanXuatId") Integer nuocSanXuatId, @Param("congNgheId") Integer congNgheId,
                                  @Param("trangThai") ApplicationConstant.TrangThaiSanPham trangThai,
                                  @Param("giaMin") BigDecimal giaMin, @Param("giaMax") BigDecimal giaMax);

    List<SanPham> getSanPhamByTrangThai(ApplicationConstant.TrangThaiSanPham trangThai);

    @Transactional
    @Modifying
    @Query(value = "Update san_pham sp set sp.trang_thai =:trangThai where sp.id = :id",nativeQuery = true)
    void removeOrRevertSanPham(@Param("trangThai") String trangThai, @Param("id") Integer id);

}
