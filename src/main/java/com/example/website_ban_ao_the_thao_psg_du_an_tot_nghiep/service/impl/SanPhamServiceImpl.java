package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.AnhSanPham;
import com.example.website_ban_ao_the_thao_ps.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_ps.entity.SanPham;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChiTietSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.SanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.SanPhamResponse;
import com.example.website_ban_ao_the_thao_ps.repository.AnhSanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.repository.ChiTietSanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.repository.KichThuocRepository;
import com.example.website_ban_ao_the_thao_ps.repository.SanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.service.SanPhamService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepository;

    @Autowired
    private SanPhamMapper sanPhamMapper;

    @Autowired
    private ChiTietSanPhamMapper chiTietSanPhamMapper;
    @Autowired
    private KichThuocRepository kichThuocRepository;

    @Override
    public Page<SanPhamResponse> pageSanPham(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<SanPham> sanPhamPage = sanPhamRepository.findAll(pageable);
        return sanPhamPage.map(sanPhamMapper::sanPhamEntityToSanPhamResponse);
    }

    @Override
    public Page<SanPhamResponse> pageSearchSanPhamAdmin(Integer pageNo, Integer size, String search, Boolean gioiTinh,
                                                        Integer cauThuId, Integer coAoId, Integer mauSacId, Integer loaiSanPhamId,
                                                        Integer chatLieuId, Integer dongSanPhamId, Integer nhaSanXuatId, Integer thuongHieuId,
                                                        Integer nuocSanXuatId, Integer congNgheId, ApplicationConstant.TrangThaiSanPham trangThai,
                                                        BigDecimal giaMin, BigDecimal giaMax) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<SanPham> sanPhamPage = sanPhamRepository.pageSearchAdmin(pageable, search, gioiTinh, cauThuId,
                coAoId, mauSacId, loaiSanPhamId, chatLieuId, dongSanPhamId, nhaSanXuatId, thuongHieuId, nuocSanXuatId,
                congNgheId, trangThai, giaMin, giaMax);
        return sanPhamPage.map(sanPhamMapper::sanPhamEntityToSanPhamResponse);
    }

    @Override
    public SanPhamResponse createSanPham(CreateSanPhamRequest createSanPhamRequest) {
        Boolean passValidation = true;
        for (ChiTietSanPham chiTietSanPhamOriginValidation : this.chiTietSanPhamRepository.findAll()) {
            for (ChiTietSanPham chiTietSanPhamCreate : createSanPhamRequest.getChiTietSanPhamList()) {
                if (chiTietSanPhamOriginValidation.getSku().equals(chiTietSanPhamCreate.getSku()) && chiTietSanPhamOriginValidation.getId() != chiTietSanPhamCreate.getId()) {
                    passValidation = false;
                    throw new InternalServerException("Mã " + chiTietSanPhamCreate.getSku() + " đã tồn tại");
                }
            }
        }
        for (AnhSanPham anhSanPham : createSanPhamRequest.getAnhSanPhamList()) {
            if (anhSanPham.getAnh().length() > 250) {
                passValidation = false;
                throw new DataIntegrityViolationException("Tên ảnh quá dài. Hãy sửa lại tên ảnh!");
            }
        }
        if (createSanPhamRequest.getAnhSanPhamList().size() > 3) {
            passValidation = false;
            throw new IllegalArgumentException("Không thể tạo sản phẩm. Số lượng ảnh vượt quá giới hạn.");
        } else if (passValidation == true) {
            try {
                SanPham sanPham = this.sanPhamMapper.createSanPhamRequestToSanPhamEntity(createSanPhamRequest);
                sanPham.setMa(GenCode.generateSanPhamCode());
                sanPham.setNgayTao(LocalDate.now());
                sanPham.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
                this.sanPhamRepository.save(sanPham);

                List<AnhSanPham> anhSanPhamList = new ArrayList<>();
                for (AnhSanPham anh : createSanPhamRequest.getAnhSanPhamList()) {
                    AnhSanPham anhSanPham = new AnhSanPham();
                    anhSanPham.setSanPham(sanPham);
                    anhSanPham.setAnh(anh.getAnh());
                    anhSanPham.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
                    anhSanPham.setNgayTao(LocalDate.now());
                    anhSanPhamList.add(anhSanPham);
                }

                this.anhSanPhamRepository.saveAll(anhSanPhamList);

                List<ChiTietSanPham> newChiTietSanPhamList = new ArrayList<>();
                for (ChiTietSanPham ct : createSanPhamRequest.getChiTietSanPhamList()) {
                    ChiTietSanPham ctsp = new ChiTietSanPham();
                    ctsp.setSanPham(sanPham);
                    ctsp.setKichThuoc(ct.getKichThuoc());
                    ctsp.setSoLuong(ct.getSoLuong());
                    ctsp.setSku(ct.getSku());
                    ctsp.setNgayTao(LocalDate.now());
                    ctsp.setTrangThai(ApplicationConstant.TrangThaiChiTietSanPham.ACTIVE);
                    newChiTietSanPhamList.add(ctsp);
                }
                chiTietSanPhamRepository.saveAll(newChiTietSanPhamList);
                return this.sanPhamMapper.sanPhamEntityToSanPhamResponse(sanPham);
            } catch (DataIntegrityViolationException ex) {
                throw new DuplicateRecordException("Không thể tạo sản phẩm. Có thể bản ghi bị trùng lặp." + ex);
            }
        } else {
            return null;
        }
    }

    @Override
    public SanPhamResponse updateSanPham(UpdateSanPhamRequest updateSanPhamRequest, Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        Boolean passValidation = true;
        for (ChiTietSanPham chiTietSanPhamFromWeb : updateSanPhamRequest.getChiTietSanPhamList()) {
            List<ChiTietSanPham> getChiTietSanPhamExceptId = this.chiTietSanPhamRepository.findAllChiTietSanPhamExceptId(chiTietSanPhamFromWeb.getId());
            for (ChiTietSanPham chiTietSanPhamOther : getChiTietSanPhamExceptId) {
                if (chiTietSanPhamFromWeb.getSku().equals(chiTietSanPhamOther.getSku())) {
                    passValidation = false;
                    throw new IllegalArgumentException("Mã SKU " + chiTietSanPhamFromWeb.getSku() + " đã tồn tại");
                }
            }
        }
        for (AnhSanPham anhSanPham : updateSanPhamRequest.getAnhSanPhamList()) {
            if (anhSanPham.getAnh().length() > 250) {
                passValidation = false;
                throw new DataIntegrityViolationException("Tên ảnh quá dài. Hãy sửa lại tên ảnh!");
            }
        }
        if (updateSanPhamRequest.getAnhSanPhamList().size() > 3) {
            passValidation = false;
            throw new IllegalArgumentException("Không thể tạo sản phẩm. Số lượng ảnh vượt quá giới hạn.");
        } else if (passValidation == true) {
            try {
                SanPham sanPham = this.sanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với ID: " + id));
                List<AnhSanPham> anhSanPhamList = sanPham.getAnhSanPhamList();
                Map<Integer, AnhSanPham> existingAnhSanPhamMap = anhSanPhamList.stream().collect(Collectors.toMap(AnhSanPham::getId, Function.identity()));
                List<AnhSanPham> updateAnhSanPhamList = new ArrayList<>();
                for (AnhSanPham anhSanPham : updateSanPhamRequest.getAnhSanPhamList()) {
                    AnhSanPham existingAnhSanPham = existingAnhSanPhamMap.get(anhSanPham.getId());

                    if (existingAnhSanPham != null) {
                        existingAnhSanPham.setAnh(anhSanPham.getAnh());
                        existingAnhSanPham.setNgayCapNhat(LocalDate.now());
                        updateAnhSanPhamList.add(existingAnhSanPham);
                    } else {
                        AnhSanPham newAnhSanPham = new AnhSanPham();
                        newAnhSanPham.setAnh(anhSanPham.getAnh());
                        newAnhSanPham.setSanPham(sanPham);
                        newAnhSanPham.setNgayTao(LocalDate.now());
                        updateAnhSanPhamList.add(newAnhSanPham);
                    }
                }
                this.anhSanPhamRepository.saveAll(updateAnhSanPhamList);

                List<ChiTietSanPham> chiTietSanPhamList = sanPham.getChiTietSanPhamList();
                Map<Integer, ChiTietSanPham> existingChiTietMap = chiTietSanPhamList.stream()
                        .collect(Collectors.toMap(ChiTietSanPham::getId, Function.identity()));

                List<ChiTietSanPham> updatedChiTietSanPham = new ArrayList<>();
                for (ChiTietSanPham updatedChiTiet : updateSanPhamRequest.getChiTietSanPhamList()) {
                    ChiTietSanPham existingChiTiet = existingChiTietMap.get(updatedChiTiet.getId());

                    if (existingChiTiet != null) {
                        existingChiTiet.setKichThuoc(updatedChiTiet.getKichThuoc());
                        existingChiTiet.setSku(updatedChiTiet.getSku());
                        existingChiTiet.setSoLuong(updatedChiTiet.getSoLuong());
                        updatedChiTietSanPham.add(existingChiTiet);
                    } else {
                        ChiTietSanPham newChiTiet = new ChiTietSanPham();
                        newChiTiet.setSanPham(sanPham);
                        newChiTiet.setKichThuoc(updatedChiTiet.getKichThuoc());
                        newChiTiet.setSku(updatedChiTiet.getSku());
                        newChiTiet.setSoLuong(updatedChiTiet.getSoLuong());
                        newChiTiet.setNgayTao(LocalDate.now());
                        newChiTiet.setTrangThai(ApplicationConstant.TrangThaiChiTietSanPham.ACTIVE);
                        chiTietSanPhamRepository.save(newChiTiet);
                        updatedChiTietSanPham.add(newChiTiet);
                    }
                }

                sanPham.setDongSanPham(updateSanPhamRequest.getDongSanPham());
                sanPham.setLoaiSanPham(updateSanPhamRequest.getLoaiSanPham());
                if (updateSanPhamRequest.getCauThu() != null) {
                    sanPham.setCauThu(updateSanPhamRequest.getCauThu());
                } else {
                    sanPham.setCauThu(null);
                }
                sanPham.setChatLieu(updateSanPhamRequest.getChatLieu());
                sanPham.setCoAo(updateSanPhamRequest.getCoAo());
                sanPham.setMauSac(updateSanPhamRequest.getMauSac());
                sanPham.setCongNghe(updateSanPhamRequest.getCongNghe());
                sanPham.setThuongHieu(updateSanPhamRequest.getThuongHieu());
                sanPham.setNuocSanXuat(updateSanPhamRequest.getNuocSanXuat());
                sanPham.setNhaSanXuat(updateSanPhamRequest.getNhaSanXuat());
                sanPham.setNamSanXuat(updateSanPhamRequest.getNamSanXuat());
                sanPham.setTen(updateSanPhamRequest.getTen());
                sanPham.setGia(updateSanPhamRequest.getGia());
                sanPham.setMoTa(updateSanPhamRequest.getMoTa());
                sanPham.setGioiTinh(updateSanPhamRequest.getGioiTinh());
                sanPham.setNgayCapNhat(LocalDate.now());

                this.sanPhamRepository.save(sanPham);

                return this.sanPhamMapper.sanPhamEntityToSanPhamResponse(sanPham);
            } catch (DataIntegrityViolationException ex) {
                throw new DuplicateRecordException("Không thể sửa sản phẩm. Có thể bản ghi bị trùng lặp." + ex);
            }
        } else {
            return null;
        }
    }

    @Override
    public List<SanPhamResponse> listSanPhamResponse() {
        return sanPhamMapper.listSanPhamEntityToSanPhamResponse(sanPhamRepository.getSanPhamByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public byte[] exportExcelSanPham() throws IOException {
        List<SanPham> data = sanPhamRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("SanPham");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("mauSac");
        headerRow.createCell(2).setCellValue("loaiSanPham");
        headerRow.createCell(3).setCellValue("chatLieu");
        headerRow.createCell(4).setCellValue("dongSanPham");
        headerRow.createCell(5).setCellValue("nhaSanXuat");
        headerRow.createCell(6).setCellValue("thuongHieu");
        headerRow.createCell(7).setCellValue("nuocSanXuat");
        headerRow.createCell(8).setCellValue("congNghe");
        headerRow.createCell(9).setCellValue("coAo");
        headerRow.createCell(10).setCellValue("cauThu");
        headerRow.createCell(11).setCellValue("namSanXuat");
        headerRow.createCell(12).setCellValue("ma");
        headerRow.createCell(13).setCellValue("ten");
        headerRow.createCell(14).setCellValue("gia");
        headerRow.createCell(15).setCellValue("moTa");
        headerRow.createCell(16).setCellValue("gioiTinh");
        headerRow.createCell(17).setCellValue("ngayTao");
        headerRow.createCell(18).setCellValue("ngayCapNhat");
        headerRow.createCell(19).setCellValue("trangThai");

        int rowNum = 1;
        for (SanPham sanPham : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Objects.toString(sanPham.getId(), ""));
            if (sanPham.getMauSac() != null) {
                row.createCell(1).setCellValue(sanPham.getMauSac().getId());
            } else {
                row.createCell(1).setCellValue((String) null);
            }
            if (sanPham.getLoaiSanPham() != null) {
                row.createCell(2).setCellValue(sanPham.getLoaiSanPham().getId());
            } else {
                row.createCell(3).setCellValue((String) null);
            }
            if (sanPham.getChatLieu() != null) {
                row.createCell(3).setCellValue(sanPham.getChatLieu().getId());
            } else {
                row.createCell(3).setCellValue((String) null);
            }
            if (sanPham.getDongSanPham() != null) {
                row.createCell(4).setCellValue(sanPham.getDongSanPham().getId());
            } else {
                row.createCell(4).setCellValue((String) null);
            }
            if (sanPham.getNhaSanXuat() != null) {
                row.createCell(5).setCellValue(sanPham.getNhaSanXuat().getId());
            } else {
                row.createCell(5).setCellValue((String) null);
            }
            if (sanPham.getThuongHieu() != null) {
                row.createCell(6).setCellValue(sanPham.getThuongHieu().getId());
            } else {
                row.createCell(6).setCellValue((String) null);
            }
            if (sanPham.getNuocSanXuat() != null) {
                row.createCell(7).setCellValue(sanPham.getNuocSanXuat().getId());
            } else {
                row.createCell(7).setCellValue((String) null);
            }
            if (sanPham.getCongNghe() != null) {
                row.createCell(8).setCellValue(sanPham.getCongNghe().getId());
            } else {
                row.createCell(8).setCellValue((String) null);
            }
            if (sanPham.getCoAo() != null) {
                row.createCell(9).setCellValue(sanPham.getCoAo().getId());
            } else {
                row.createCell(9).setCellValue((String) null);
            }
            if (sanPham.getCauThu() != null) {
                row.createCell(10).setCellValue(sanPham.getCauThu().getId());
            } else {
                row.createCell(10).setCellValue((String) null);
            }
            row.createCell(11).setCellValue(Objects.toString(sanPham.getNamSanXuat(), ""));
            row.createCell(12).setCellValue(Objects.toString(sanPham.getMa(), ""));
            row.createCell(13).setCellValue(Objects.toString(sanPham.getTen(), ""));
            row.createCell(14).setCellValue(Objects.toString(sanPham.getGia(), ""));
            row.createCell(15).setCellValue(Objects.toString(sanPham.getMoTa(), ""));
            row.createCell(16).setCellValue(Objects.toString(sanPham.getGioiTinh(), ""));
            row.createCell(17).setCellValue(Objects.toString(sanPham.getNgayTao(), ""));
            row.createCell(18).setCellValue(Objects.toString(sanPham.getNgayCapNhat(), ""));
            row.createCell(19).setCellValue(Objects.toString(sanPham.getTrangThai(), ""));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }


    @Override
    public SanPhamResponse getOneSanPham(Integer id) {
        SanPham sanPham = sanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("SanPham not found with id" + id));
        return sanPhamMapper.sanPhamEntityToSanPhamResponse(sanPham);
    }

    @Override
    public void removeOrRevertSanPham(String trangThaiSanPham, Integer id) {
        sanPhamRepository.removeOrRevertSanPham(trangThaiSanPham, id);
    }
}
