package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ThuHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuHangResponse;
import com.example.website_ban_ao_the_thao_ps.repository.ThuHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.ThuHangService;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class ThuHangServiceImpl implements ThuHangService {
    @Autowired
    ThuHangRepository thuHangRepository;

    @Autowired
    ThuHangMapper thuHangMapper;

    @Override
    public Page<ThuHangResponse> pageThuHang(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<ThuHang> thuHangPage = thuHangRepository.findAll(pageable);
        return thuHangPage.map(thuHangMapper::thuHangEntiyToThuHangResponse);
    }

    @Override
    public Page<ThuHangResponse> pageSearchThuHang(Integer pageNo, Integer size, String search, String trangThaiThuHang) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.ASC, "id"));

        ApplicationConstant.TrangThaiThuHang trangThaiThuHang1 = null;
        if (trangThaiThuHang != null) {
            trangThaiThuHang1 = ApplicationConstant.TrangThaiThuHang.valueOf(trangThaiThuHang);
        }

        Page<ThuHang> thuHangPage = thuHangRepository.pageSearch(pageable, search, trangThaiThuHang1);
        return thuHangPage.map(thuHangMapper::thuHangEntiyToThuHangResponse);
    }

    @Override
    public List<ThuHangResponse> listThuHangResponse() {
        return thuHangMapper.listThuHangEntityToThuHangResponse(thuHangRepository.getThuHangByTrangThai(ApplicationConstant.TrangThaiThuHang.ACTIVE));
    }

    @Override
//    public byte[] exportExcelThuHang() throws IOException {
//        List<ThuHang> data = thuHangRepository.findAll();
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("ThuHang");
//
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("id");
//        headerRow.createCell(1).setCellValue("ma");
//        headerRow.createCell(2).setCellValue("ten");
//        headerRow.createCell(3).setCellValue("anh");
//        headerRow.createCell(4).setCellValue("moTa");
//        headerRow.createCell(5).setCellValue("soTienKhachChiToiThieu");
//        headerRow.createCell(6).setCellValue("soLuongDonHangToiThieu");
//        headerRow.createCell(7).setCellValue("ngayTao");
//        headerRow.createCell(8).setCellValue("ngayCapNhat");
//        headerRow.createCell(9).setCellValue("trangThai");
//
//        int rowNum = 1;
//        for (ThuHang thuHang : data) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(thuHang.getId());
//            row.createCell(1).setCellValue(thuHang.getMa());
//            row.createCell(2).setCellValue(thuHang.getTen());
//            row.createCell(3).setCellValue(thuHang.getAnh());
//            row.createCell(4).setCellValue(thuHang.getMoTa());
//            row.createCell(5).setCellValue(String.valueOf(thuHang.getSoTienKhachChiToiThieu()));
//            row.createCell(6).setCellValue(thuHang.getSoLuongDonHangToiThieu());
//            row.createCell(7).setCellValue(String.valueOf(thuHang.getNgayTao()));
//            row.createCell(8).setCellValue(String.valueOf(thuHang.getNgayCapNhat()));
//            row.createCell(9).setCellValue(String.valueOf(thuHang.getTrangThai()));
//        }
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        return outputStream.toByteArray();
//    }
    public byte[] exportExcelThuHang() throws IOException {
        List<ThuHang> data = thuHangRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ThuHang");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("ma");
        headerRow.createCell(2).setCellValue("ten");
        headerRow.createCell(3).setCellValue("anh");
        headerRow.createCell(4).setCellValue("moTa");
        headerRow.createCell(5).setCellValue("soTienKhachChiToiThieu");
        headerRow.createCell(6).setCellValue("soLuongDonHangToiThieu");
        headerRow.createCell(7).setCellValue("ngayTao");
        headerRow.createCell(8).setCellValue("ngayCapNhat");
        headerRow.createCell(9).setCellValue("trangThai");

        int rowNum = 1;
        for (ThuHang thuHang : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Objects.toString(thuHang.getId(), ""));
            row.createCell(1).setCellValue(Objects.toString(thuHang.getMa(), ""));
            row.createCell(2).setCellValue(Objects.toString(thuHang.getTen(), ""));
            row.createCell(3).setCellValue(Objects.toString(thuHang.getAnh(), ""));
            row.createCell(4).setCellValue(Objects.toString(thuHang.getMoTa(), ""));
            row.createCell(5).setCellValue(Objects.toString(thuHang.getSoTienKhachChiToiThieu(), ""));
            row.createCell(6).setCellValue(Objects.toString(thuHang.getSoLuongDonHangToiThieu(), ""));
            row.createCell(7).setCellValue(Objects.toString(thuHang.getNgayTao(), ""));
            row.createCell(8).setCellValue(Objects.toString(thuHang.getNgayCapNhat(), ""));
            row.createCell(9).setCellValue(Objects.toString(thuHang.getTrangThai(), ""));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }



    @Override
    public ThuHangResponse createThuHang(CreateThuHangRequest createThuHangRequest) {
        try {
            ThuHang thuHang = thuHangMapper.createThuHangRequestToThuHangEntity(createThuHangRequest);
            thuHang.setMa(GenCode.generateThuHangCode());
            thuHang.setNgayTao(LocalDate.now());
            thuHang.setTrangThai(ApplicationConstant.TrangThaiThuHang.ACTIVE);
            return thuHangMapper.thuHangEntiyToThuHangResponse(thuHangRepository.save(thuHang));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create thu hang. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create thu hang due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public ThuHangResponse updateThuHang(UpdateThuHangRequest updateThuHangRequest, Integer id) {
        try {
            ThuHang thuHang = thuHangRepository.findById(id).orElseThrow(() -> new NotFoundException("ThuHang not found with id " + id));
            ThuHang updateThuHang = thuHangMapper.updateThuHangRequestToThuHangEntity(updateThuHangRequest);
            thuHang.setTen(updateThuHang.getTen());
            thuHang.setMoTa(updateThuHang.getMoTa());
            thuHang.setAnh(updateThuHang.getAnh());
            thuHang.setNgayCapNhat(LocalDate.now());
            thuHang.setSoLuongDonHangToiThieu(updateThuHang.getSoLuongDonHangToiThieu());
            thuHang.setSoTienKhachChiToiThieu(updateThuHang.getSoTienKhachChiToiThieu());
            return thuHangMapper.thuHangEntiyToThuHangResponse(thuHangRepository.save(thuHang));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update thu hang. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update thu hang due to an unexpected error." + ex);
        }
    }

    @Override
    public ThuHangResponse getOneThuHang(Integer id) {
        ThuHang thuHang = thuHangRepository.findById(id).orElseThrow(() -> new NotFoundException("ThuHang not found with id " + id));
        return thuHangMapper.thuHangEntiyToThuHangResponse(thuHang);
    }

    @Override
    public void removeOrRevertThuHang(String trangThaiThuHang, Integer id) {
        thuHangRepository.removeOrRevert(trangThaiThuHang, id);
    }

    @Override
    public List<Object[]> getAllList() {
        return this.thuHangRepository.selectCountThuHang();
    }
}
