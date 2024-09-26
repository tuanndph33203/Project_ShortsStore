package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.HoaDonMapper;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import com.example.website_ban_ao_the_thao_ps.repository.HoaDonRepository;
import com.example.website_ban_ao_the_thao_ps.service.HoaDonService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    HoaDonMapper hoaDonMapper;
    @Override
    public Page<HoaDonResponse> pageHoaDon(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<HoaDon> hoaDonPage = hoaDonRepository.findAll(pageable);
        return hoaDonPage.map(hoaDonMapper::hoaDonEntityToHoaDonResponse);
    }

    @Override
    public Page<HoaDonResponse> searchHoaDon(Integer pageNo, Integer pageSize, String search, ApplicationConstant.HinhThucBanHang hinhThucBanHang, LocalDate ngayTaoMin, LocalDate ngayTaoMax, ApplicationConstant.TrangThaiHoaDon trangThai) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<HoaDon> hoaDonPage = hoaDonRepository.searchHoaDon(pageable, search,hinhThucBanHang,ngayTaoMin,ngayTaoMax,trangThai);
        return hoaDonPage.map(hoaDonMapper::hoaDonEntityToHoaDonResponse);
    }

    @Override
    public Page<HoaDonResponse> searchGiaoDich(Integer pageNo, Integer pageSize, String searchTenOrMa, String maGiaoDich, LocalDate ngayTaoMin, LocalDate ngayTaoMax, ApplicationConstant.HinhThucThanhToan hinhThucThanhToan) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<HoaDon> hoaDonPage = hoaDonRepository.searchGiaoDich(pageable,searchTenOrMa, maGiaoDich,ngayTaoMin,ngayTaoMax,hinhThucThanhToan);
        return hoaDonPage.map(hoaDonMapper::hoaDonEntityToHoaDonResponse);
    }

    @Override
    public HoaDonResponse getOneHoaDon(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElseThrow(() -> new NotFoundException("HoaDon not found with id" + id));
        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDon);
    }

    @Override
    public byte[] exportExcelHoaDon() throws IOException {
        List<HoaDon> data = hoaDonRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("HoaDon");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("khachHang");
        headerRow.createCell(2).setCellValue("nhanVien");
        headerRow.createCell(3).setCellValue("ma");
        headerRow.createCell(4).setCellValue("ngayTao");
        headerRow.createCell(5).setCellValue("ngayMuonNhan");
        headerRow.createCell(6).setCellValue("phanTramGiamGia");
        headerRow.createCell(7).setCellValue("soDiemCong");
        headerRow.createCell(8).setCellValue("soDiemTru");
        headerRow.createCell(9).setCellValue("soTienChuyenKhoan");
        headerRow.createCell(10).setCellValue("soTienMat");
        headerRow.createCell(11).setCellValue("soTienVanChuyen");
        headerRow.createCell(12).setCellValue("maGiaoDichTienMat");
        headerRow.createCell(13).setCellValue("maGiaoDichChuyenKhoan");
        headerRow.createCell(14).setCellValue("phuongThucThanhToan");
        headerRow.createCell(15).setCellValue("thanhTien");
        headerRow.createCell(16).setCellValue("diaChi");
        headerRow.createCell(17).setCellValue("tenNguoiNhan");
        headerRow.createCell(18).setCellValue("sdtNguoiNhan");
        headerRow.createCell(19).setCellValue("sdtNguoiShip");
        headerRow.createCell(20).setCellValue("hinhThucBanHang");
        headerRow.createCell(21).setCellValue("trangThai");

        int rowNum = 1;
        for (HoaDon hoaDon : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(hoaDon.getId());

            // Xử lý giá trị null cho KhachHang
            if (hoaDon.getKhachHang() != null) {
                row.createCell(1).setCellValue(hoaDon.getKhachHang().getId());
            } else {
                row.createCell(1).setCellValue((String) null);
            }

            // Xử lý giá trị null cho NhanVien
            if (hoaDon.getNhanVien() != null) {
                row.createCell(2).setCellValue(hoaDon.getNhanVien().getId());
            } else {
                row.createCell(2).setCellValue((String) null);
            }

            // Xử lý các trường khác với kiểm tra giá trị null và sử dụng Objects.toString() cho chuyển đổi
            row.createCell(3).setCellValue(Objects.toString(hoaDon.getMa(), ""));
            row.createCell(4).setCellValue(Objects.toString(hoaDon.getNgayTao(), ""));
            row.createCell(5).setCellValue(Objects.toString(hoaDon.getNgayMuonNhan(), ""));
            row.createCell(6).setCellValue(Objects.toString(hoaDon.getPhanTramGiamGia(), ""));
            row.createCell(7).setCellValue(Objects.toString(hoaDon.getSoDiemCong(), ""));
            row.createCell(8).setCellValue(Objects.toString(hoaDon.getSoDiemTru(), ""));
            row.createCell(9).setCellValue(Objects.toString(hoaDon.getSoTienChuyenKhoan(), ""));
            row.createCell(10).setCellValue(Objects.toString(hoaDon.getSoTienMat(), ""));
            row.createCell(11).setCellValue(Objects.toString(hoaDon.getSoTienVanChuyen(), ""));
            row.createCell(12).setCellValue(Objects.toString(hoaDon.getMaGiaoDichTienMat(), ""));
            row.createCell(13).setCellValue(Objects.toString(hoaDon.getMaGiaoDichChuyenKhoan(), ""));
            row.createCell(14).setCellValue(Objects.toString(hoaDon.getPhuongThucThanhToan(), ""));
            row.createCell(15).setCellValue(Objects.toString(hoaDon.getThanhTien(), ""));
            row.createCell(16).setCellValue(Objects.toString(hoaDon.getDiaChi(), ""));
            row.createCell(17).setCellValue(Objects.toString(hoaDon.getTenNguoiNhan(), ""));
            row.createCell(18).setCellValue(Objects.toString(hoaDon.getSdtNguoiNhan(), ""));
            row.createCell(19).setCellValue(Objects.toString(hoaDon.getSdtNguoiShip(), ""));
            row.createCell(20).setCellValue(Objects.toString(hoaDon.getHinhThucBanHang(), ""));
            row.createCell(21).setCellValue(Objects.toString(hoaDon.getTrangThai(), ""));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

}
