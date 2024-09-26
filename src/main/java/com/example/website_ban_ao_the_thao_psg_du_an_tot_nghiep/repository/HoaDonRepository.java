package com.example.website_ban_ao_the_thao_ps.repository;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query("SELECT hd FROM HoaDon hd " +
            "LEFT JOIN hd.khachHang kh " +
            "LEFT JOIN hd.nhanVien nv " +
            "WHERE (:search is null or hd.ma like CONCAT('%', :search, '%')  " +
            " or hd.sdtNguoiNhan like CONCAT('%', :search, '%') " +
            "or hd.tenNguoiNhan like CONCAT('%', :search, '%') " +
            "or nv.ten like CONCAT('%', :search, '%') " +
            "or kh.ten like CONCAT('%', :search, '%')" +
            "or kh.sdt like CONCAT('%', :search, '%')) " +
            "and (:ngayTaoMin is null or hd.ngayTao >= :ngayTaoMin) " +
            "and (:ngayTaoMax is null or hd.ngayTao <= :ngayTaoMax) " +
            "and (:hinhThucBanHang is null or hd.hinhThucBanHang = :hinhThucBanHang) " +
            "and (:trangThai is null or hd.trangThai = :trangThai)")
    Page<HoaDon> searchHoaDon(Pageable pageable, @Param("search") String search,
                              @Param("hinhThucBanHang") ApplicationConstant.HinhThucBanHang hinhThucBanHang,
                              @Param("ngayTaoMin") LocalDate ngayTaoMin,
                              @Param("ngayTaoMax") LocalDate ngayTaoMax,
                              @Param("trangThai") ApplicationConstant.TrangThaiHoaDon trangThai);

    @Query("SELECT hd FROM HoaDon hd " +
            "LEFT JOIN hd.khachHang kh " +
            "LEFT JOIN hd.nhanVien nv " +
            "WHERE (:searchTenOrMa is null or hd.ma like CONCAT('%', :searchTenOrMa, '%')  " +
            " or kh.ten like CONCAT('%', :searchTenOrMa, '%') " +
            "or nv.ten like CONCAT('%', :searchTenOrMa, '%')) " +
            "and (:maGiaoDich is null or hd.maGiaoDichChuyenKhoan like CONCAT('%', :maGiaoDich, '%')" +
            "or hd.maGiaoDichTienMat like CONCAT('%', :maGiaoDich, '%')) " +
            "and (:phuongThucThanhToan is null or hd.phuongThucThanhToan = :phuongThucThanhToan) " +
            "and (:ngayTaoMin is null or hd.ngayTao >= :ngayTaoMin) " +
            "and (:ngayTaoMax is null or hd.ngayTao <= :ngayTaoMax) ")
    Page<HoaDon> searchGiaoDich(Pageable pageable, @Param("searchTenOrMa") String searchTenOrMa,
                                @Param("maGiaoDich") String maGiaoDich,
                                @Param("ngayTaoMin") LocalDate ngayTaoMin,
                                @Param("ngayTaoMax") LocalDate ngayTaoMax,
                                @Param("phuongThucThanhToan") ApplicationConstant.HinhThucThanhToan hinhThucThanhToan
    );

    List<HoaDon> getHoaDonsByTrangThai(ApplicationConstant.TrangThaiHoaDon trangThaiHoaDon);

    @Query(value = "select hd from HoaDon hd where hd.trangThai = :trangThai")
    List<HoaDon> getHoaDonByTrangThai(@Param("trangThai") ApplicationConstant.TrangThaiHoaDon trangThaiHoaDon);

    @Transactional
    @Modifying
    @Query(value = "UPDATE hoa_don hd SET hd.trang_thai = :trangThai WHERE hd.id = :id", nativeQuery = true)
    void updateTrangThaiHoaDon(@Param("trangThai") String trangThai, @Param("id") Integer id);


    @Query("SELECT hd.trangThai AS trangThai, COUNT(hd) AS soLuong, SUM(hd.thanhTien) AS tongThanhTien " +
            "FROM HoaDon hd " +
            "GROUP BY hd.trangThai")
    List<Object[]> countAndSumByTrangThai();
    @Query("SELECT COUNT(hd) AS soLuong, SUM(hd.thanhTien) AS tongThanhTien " +
            "FROM HoaDon hd " +
            "WHERE (:startDate IS NULL OR hd.ngayTao >= :startDate) " +
            "AND (:endDate IS NULL OR hd.ngayTao <= :endDate) " +
            "GROUP BY hd.ngayTao")
    List<Object[]> countAndSumByNgayTao(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT hd.ngayTao AS ngayTao, COUNT(hd) AS soLuong, SUM(hd.thanhTien) AS tongThanhTien " +
            "FROM HoaDon hd " +
            "WHERE (:startDate IS NULL OR hd.ngayTao >= :startDate) " +
            "AND (:endDate IS NULL OR hd.ngayTao <= :endDate) " +
            "GROUP BY hd.ngayTao")
    List<Object[]> countAndSumByNgayTaoMoney(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT COUNT(hd) AS soLuong, SUM(hd.thanhTien) AS tongThanhTien " +
            "FROM HoaDon hd ")
    List<Object[]> sumHoaDon();

    HoaDon findHoaDonByMaGiaoDichChuyenKhoan(String maGD);

    HoaDon findHoaDonByMa(String maHd);
}
