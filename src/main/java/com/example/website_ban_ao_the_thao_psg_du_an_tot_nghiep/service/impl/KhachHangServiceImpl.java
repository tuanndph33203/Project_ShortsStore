package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.CustomEmailSender;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangInfo;
import com.example.website_ban_ao_the_thao_ps.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_ps.entity.DiaChi;
import com.example.website_ban_ao_the_thao_ps.entity.GioHang;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.exception.ResourceNotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.GioHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.KhachHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_ps.repository.DiaChiRepository;
import com.example.website_ban_ao_the_thao_ps.repository.GioHangRepository;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.repository.ThuHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.KhachHangService;
import jakarta.transaction.Transactional;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    ThuHangRepository thuHangRepository;

    @Autowired
    KhachHangMapper khachHangMapper;

    @Autowired
    GioHangMapper gioHangMapper;

    @Autowired
    private CustomEmailSender customEmailSender;

    @Override
    public Page<KhachHangResponse> pageKhachHang(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<KhachHang> khachHangPage = khachHangRepository.findAll(pageable);
        return khachHangPage.map(khachHangMapper::khachHangEntityToKhachHangResponse);
    }

    @Override
    public Page<KhachHangResponse> pageSearchKhachHang(Integer pageNo, Integer size, String search, ApplicationConstant.TrangThaiTaiKhoan trangThai, Integer thuHangId) {
        Pageable pageable = PageRequest.of(pageNo, size,Sort.by(Sort.Direction.DESC, "id"));
        Page<KhachHang> khachHangPage = khachHangRepository.pageSearch(pageable, search, trangThai, thuHangId);
        return khachHangPage.map(khachHangMapper::khachHangEntityToKhachHangResponse);
    }

    @Override
    public List<KhachHangResponse> listKhachHangResponse() {
        return khachHangMapper.listKhachHangEntityToKhachHangResponse(khachHangRepository.findAll());
    }

    @Override
    public List<KhachHangInfo> listKhachHangInfo() {
        List<KhachHang> khachHangEntities = khachHangRepository.findAll();
        List<KhachHangInfo> khachHangInfos = new ArrayList<>();
        for (KhachHang khachHangEntity : khachHangEntities) {
            KhachHangInfo khachHangInfo = new KhachHangInfo();
            khachHangInfo.setEmail(khachHangEntity.getEmail());
            khachHangInfo.setSdt(khachHangEntity.getSdt());
            khachHangInfos.add(khachHangInfo);
        }
        return khachHangInfos;
    }
    @Override
    public byte[] exportExcelKhachHang() throws IOException {
        List<KhachHang> data = khachHangRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("KhachHang");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("thuHang");
        headerRow.createCell(2).setCellValue("ma");
        headerRow.createCell(3).setCellValue("ten");
        headerRow.createCell(4).setCellValue("anh");
        headerRow.createCell(5).setCellValue("gioiTinh");
        headerRow.createCell(6).setCellValue("ngaySinh");
        headerRow.createCell(7).setCellValue("diaChi");
        headerRow.createCell(8).setCellValue("sdt");
        headerRow.createCell(9).setCellValue("email");
        headerRow.createCell(10).setCellValue("matKhau");
        headerRow.createCell(11).setCellValue("soLuongDonHangThanhCong");
        headerRow.createCell(12).setCellValue("soTienDaChiTieu");
        headerRow.createCell(13).setCellValue("soDiem");
        headerRow.createCell(14).setCellValue("ngayTao");
        headerRow.createCell(15).setCellValue("ngayCapNhat");
        headerRow.createCell(16).setCellValue("trangThai");

        int rowNum = 1;
        for (KhachHang khachHang : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Objects.toString(khachHang.getId(), ""));
            if (khachHang.getThuHang() != null) {
                row.createCell(1).setCellValue(khachHang.getThuHang().getId());
            } else {
                row.createCell(1).setCellValue((String) null);
            }
            row.createCell(2).setCellValue(Objects.toString(khachHang.getMa(), ""));
            row.createCell(3).setCellValue(Objects.toString(khachHang.getTen(), ""));
            row.createCell(4).setCellValue(Objects.toString(khachHang.getAnh(), ""));
            row.createCell(5).setCellValue(khachHang.getGioiTinh() != null ? khachHang.getGioiTinh().booleanValue() : false);
            row.createCell(6).setCellValue(Objects.toString(khachHang.getNgaySinh(), ""));
            row.createCell(7).setCellValue(Objects.toString(khachHang.getDiaChi(), ""));
            row.createCell(8).setCellValue(Objects.toString(khachHang.getSdt(), ""));
            row.createCell(9).setCellValue(Objects.toString(khachHang.getEmail(), ""));
            row.createCell(10).setCellValue(Objects.toString(khachHang.getMatKhau(), ""));
            row.createCell(11).setCellValue(Objects.toString(khachHang.getSoLuongDonHangThanhCong(), ""));
            row.createCell(12).setCellValue(Objects.toString(khachHang.getSoTienDaChiTieu(), ""));
            row.createCell(13).setCellValue(Objects.toString(khachHang.getSoDiem(), ""));
            row.createCell(14).setCellValue(Objects.toString(khachHang.getNgayTao(), ""));
            row.createCell(15).setCellValue(Objects.toString(khachHang.getNgayCapNhat(), ""));
            row.createCell(16).setCellValue(Objects.toString(khachHang.getTrangThai(), ""));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
//
//    @Override
//    public void importExcel(MultipartFile file) throws IOException, InvalidFormatException {
//        // Kiểm tra định dạng file excel
//        if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//            throw new InternalServerException("Không đúng định dạng Excel");
//        }
//
//        Workbook workbook = WorkbookFactory.create(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//
//        List<KhachHang> khachHangList = new ArrayList<>();
//
//        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//
//            KhachHang khachHang = new KhachHang();
//            khachHang.setId((int) row.getCell(0).getNumericCellValue());
//            khachHang.setThuHang((int) row.getCell(1).getNumericCellValue());
//            khachHang.setMa(row.getCell(2).getStringCellValue());
//            khachHang.setTen(row.getCell(3).getStringCellValue());
//            khachHang.setAnh(row.getCell(4).getStringCellValue());
//            khachHang.setGioiTinh(row.getCell(5) != null ? Boolean.valueOf(row.getCell(5).getStringCellValue()) : null);
//
//            try {
//                khachHang.setNgaySinh(LocalDate.parse(row.getCell(6).getStringCellValue()));
//            } catch (DateTimeParseException e) {
//                // Xử lý lỗi ngày sinh không hợp lệ (có thể log hoặc bỏ qua tùy theo yêu cầu)
//                continue;
//            }
//
//            khachHang.setDiaChi(row.getCell(7) != null ? row.getCell(7).getStringCellValue() : null);
//            khachHang.setSdt(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null);
//            khachHang.setEmail(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null);
//            khachHang.setMatKhau(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null);
//            khachHang.setSoLuongDonHangThanhCong((int) row.getCell(11).getNumericCellValue());
//            khachHang.setSoTienDaChiTieu(row.getCell(12) != null ? BigDecimal.valueOf(row.getCell(12).getNumericCellValue()) : null);
//            khachHang.setSoDiem(row.getCell(13) != null ? row.getCell(13).getNumericCellValue() : null);
//
//            try {
//                khachHang.setNgayTao(LocalDate.parse(row.getCell(14).getStringCellValue()));
//                khachHang.setNgayCapNhat(LocalDate.parse(row.getCell(15).getStringCellValue()));
//            } catch (DateTimeParseException e) {
//                // Xử lý lỗi ngày tạo hoặc ngày cập nhật không hợp lệ (có thể log hoặc bỏ qua tùy theo yêu cầu)
//                continue;
//            }
//
//            if (row.getCell(16) != null) {
//                try {
//                    khachHang.setTrangThai(ApplicationConstant.TrangThaiTaiKhoan.valueOf(row.getCell(16).getStringCellValue()));
//                } catch (IllegalArgumentException e) {
//                    // Xử lý lỗi trạng thái không hợp lệ (có thể log hoặc bỏ qua tùy theo yêu cầu)
//                    continue;
//                }
//            }
//
//            khachHangList.add(khachHang);
//        }
//
//        for (KhachHang kh : khachHangList) {
//            // Kiểm tra xem ID khách hàng đã tồn tại chưa
//            Optional<KhachHang> existingKhachHang = khachHangRepository.findById(kh.getId());
//            if (existingKhachHang.isPresent()) {
//                // Cập nhật bản ghi khách hàng
//                kh.setNgayCapNhat(LocalDate.now());
//            } else {
//                // Tạo bản ghi khách hàng mới
//                kh.setNgayTao(LocalDate.now());
//            }
//
//            khachHangRepository.save(kh);
//        }
//    }

//
//    @Override
//    public void importExcel(MultipartFile file) throws IOException, InvalidFormatException {
//        // Kiểm tra định dạng file excel
//        if (!file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//            throw new InternalServerException("Không đúng định dạng Excel");
//        }
//
//        Workbook workbook = WorkbookFactory.create(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//
//        List<KhachHang> khachHangList = new ArrayList<>();
//
//        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//            Row row = sheet.getRow(i);
//
//            KhachHang khachHang = new KhachHang();
//            khachHang.setId((int) row.getCell(0).getNumericCellValue());
//            khachHang.setThuHang((int) row.getCell(1).getNumericCellValue());
//            khachHang.setMa(row.getCell(2).getStringCellValue());
//            khachHang.setTen(row.getCell(3).getStringCellValue());
//            khachHang.setAnh(row.getCell(4).getStringCellValue());
//            khachHang.setGioiTinh(row.getCell(5) != null ? Boolean.valueOf(row.getCell(5).getStringCellValue()) : null);
//
//            try {
//                khachHang.setNgaySinh(LocalDate.parse(row.getCell(6).getStringCellValue()));
//            } catch (DateTimeParseException e) {
//                // Xử lý lỗi ngày sinh không hợp lệ (có thể log hoặc bỏ qua tùy theo yêu cầu)
//                continue;
//            }
//
//            khachHang.setDiaChi(row.getCell(7) != null ? row.getCell(7).getStringCellValue() : null);
//            khachHang.setSdt(row.getCell(8) != null ? row.getCell(8).getStringCellValue() : null);
//            khachHang.setEmail(row.getCell(9) != null ? row.getCell(9).getStringCellValue() : null);
//            khachHang.setMatKhau(row.getCell(10) != null ? row.getCell(10).getStringCellValue() : null);
//            khachHang.setSoLuongDonHangThanhCong((int) row.getCell(11).getNumericCellValue());
//            khachHang.setSoTienDaChiTieu(row.getCell(12) != null ? BigDecimal.valueOf(row.getCell(12).getNumericCellValue()) : null);
//            khachHang.setSoDiem(row.getCell(13) != null ? row.getCell(13).getNumericCellValue() : null);
//
//            try {
//                khachHang.setNgayTao(LocalDate.parse(row.getCell(14).getStringCellValue()));
//                khachHang.setNgayCapNhat(LocalDate.parse(row.getCell(15).getStringCellValue()));
//            } catch (DateTimeParseException e) {
//                // Xử lý lỗi ngày tạo hoặc ngày cập nhật không hợp lệ (có thể log hoặc bỏ qua tùy theo yêu cầu)
//                continue;
//            }
//
//            if (row.getCell(16) != null) {
//                try {
//                    khachHang.setTrangThai(ApplicationConstant.TrangThaiTaiKhoan.valueOf(row.getCell(16).getStringCellValue()));
//                } catch (IllegalArgumentException e) {
//                    // Xử lý lỗi trạng thái không hợp lệ (có thể log hoặc bỏ qua tùy theo yêu cầu)
//                    continue;
//                }
//            }
//
//            khachHangList.add(khachHang);
//        }
//
//        for (KhachHang kh : khachHangList) {
//            // Kiểm tra xem ID khách hàng đã tồn tại chưa
//            Optional<KhachHang> existingKhachHang = khachHangRepository.findById(kh.getId());
//            if (existingKhachHang.isPresent()) {
//                // Cập nhật bản ghi khách hàng
//                kh.setNgayCapNhat(LocalDate.now());
//            } else {
//                // Tạo bản ghi khách hàng mới
//                kh.setNgayTao(LocalDate.now());
//            }
//
//            khachHangRepository.save(kh);
//        }
//    }

    @Override
    public KhachHangResponse create(CreateKhachHangRequest createKhachHangRequest) {
        try {
            KhachHang khachHang = khachHangMapper.createKhachHangRequestToKhachHangEntity(createKhachHangRequest);
            khachHang.setMa(GenCode.generateKhachHangCode());
            khachHang.setNgayTao(LocalDate.now());
            khachHang.setSoLuongDonHangThanhCong(0);
            khachHang.setSoDiem((double) 0);
            khachHang.setSoTienDaChiTieu(BigDecimal.valueOf(0));
            khachHang.setTrangThai(ApplicationConstant.TrangThaiTaiKhoan.ACTIVE);

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            String mkKhachHang = GenCode.generatePassWordKhachHang();
            String encodedPassword = passwordEncoder.encode(mkKhachHang);
            khachHang.setMatKhau(encodedPassword);
            if(khachHangRepository.findBySdt(khachHang.getSdt()) != null){
                throw new InternalServerException("Số điện thoại này đã tồn tại " + khachHang.getSdt());
            }

            if(khachHangRepository.findKhachHangByEmail(khachHang.getEmail()) != null){
                throw new InternalServerException("Email này đã tồn tại " + khachHang.getEmail());
            }

            KhachHang khSave = khachHangRepository.save(khachHang);
            GioHang gioHang = new GioHang();
            gioHang.setKhachHang(khSave);
            gioHang.setNgayTao(LocalDate.now());
            gioHang.setTrangThai(ApplicationConstant.TrangThaiGioHang.PENDING);
            gioHangRepository.save(gioHang);

            customEmailSender.signupKhachHangSendEmail(khachHang, mkKhachHang);

            System.out.println(mkKhachHang);

            return khachHangMapper.khachHangEntityToKhachHangResponse(khSave);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create khach hang. Possibly duplicate record." + ex);
        }
    }


    @Override
    public KhachHangResponse findKhachHangByEmail(String email) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);
        return khachHangMapper.khachHangEntityToKhachHangResponse(khachHang);
    }

    @Override
    public boolean checkMail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return khachHangRepository.existsByEmail(email);
    }



    @Override
    @Transactional
    public KhachHangResponse update(UpdateKhachHangRequest updateKhachHangRequest, Integer id) {
        try {
            KhachHang khachHang = khachHangRepository.findById(id).orElseThrow(() -> new NotFoundException("KhachHang not found with id " + id));

            KhachHang updateKhachHang = khachHangMapper.updateKhachHangRequestToKhachHangEntity(updateKhachHangRequest);
//
//            for (KhachHang kh : khachHangRepository.findAll()){
//                if(kh.getEmail().equals(updateKhachHang.getEmail()) && kh.getId() != khachHang.getId()){
//                    throw new InternalServerException("Email này đã tồn tại " + kh.getEmail());
//                }
//            }
//
//            for (KhachHang kh : khachHangRepository.findAll()){
//                if(kh.getSdt().equals(updateKhachHang.getSdt()) && kh.getId() != khachHang.getId()){
//                    throw new InternalServerException("Số điện thoại này đã tồn tại " + kh.getSdt());
//                }
//            }

            for (KhachHang kh : khachHangRepository.findAll()){
                if(kh.getEmail().equals(updateKhachHang.getEmail()) && kh.getId() != khachHang.getId()){
                    throw new InternalServerException("Email này đã tồn tại " + kh.getEmail());
                }
            }

            for (KhachHang kh : khachHangRepository.findAll()){
                if(kh.getSdt().equals(updateKhachHang.getSdt()) && kh.getId() != khachHang.getId()){
                    throw new InternalServerException("Số điện thoại này đã tồn tại " + kh.getSdt());
                }
            }

            khachHang.setTen(updateKhachHang.getTen());
            khachHang.setAnh(updateKhachHang.getAnh());
            khachHang.setGioiTinh(updateKhachHang.getGioiTinh());
            khachHang.setNgaySinh(updateKhachHang.getNgaySinh());
            khachHang.setDiaChi(updateKhachHang.getDiaChi());
            khachHang.setEmail(updateKhachHang.getEmail());
            khachHang.setSdt(updateKhachHang.getSdt());
            khachHang.setNgayCapNhat(LocalDate.now());

            return khachHangMapper.khachHangEntityToKhachHangResponse(khachHangRepository.save(khachHang));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update khach hang. Possibly duplicate record." + ex);
        }
    }

    @Override
    public KhachHangResponse getOne(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElseThrow(() -> new NotFoundException("KhachHang not found with id " + id));
        return khachHangMapper.khachHangEntityToKhachHangResponse(khachHang);
    }

    @Override
    public void removeOrRevert(String trangThaiKhachHang, Integer id) {
        khachHangRepository.removeOrRevert(trangThaiKhachHang, id);
    }

//    @Override
//    public Integer addKhachHang(KhachHangDto khachHangDto) {
//        String plainTextPassword = GenCode.generatePassWordKhachHang();
//        String encodedPassword = passwordEncoder.encode(plainTextPassword);
//        String generatedCode = GenCode.generateKhachHangCode();
//        KhachHang khachHang = new KhachHang(
//                khachHangDto.getGioiTinh(),
//                khachHangDto.getTen(),
//                khachHangDto.getEmail(),
//                khachHangDto.getSdt(),
//                generatedCode,
//                encodedPassword
//
//        );
//        khachHang.setSoLuongDonHangThanhCong(0);
//        khachHang.setSoTienDaChiTieu(BigDecimal.ZERO);
//        khachHang.setSoDiem(0.0);
//        customEmailSender.signupKhachHangSendEmail(khachHang, plainTextPassword);
//        khachHangRepository.saveAndFlush(khachHang);
//        return khachHang.getId().intValue();
//    }

//    @Override
//    public LoginMesage loginKhachHang(KhachHangDto khachHangDto) {
//        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(khachHangDto.getEmail());
//        if (khachHang == null) {
//            return new LoginMesage("Thông tin đăng nhập không chính xác!", false);
//        }
//        String password = khachHangDto.getMatKhau();
//        String encodedPassword = khachHang.getMatKhau();
//        boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
//        if (isPwdRight) {
//            return new LoginMesage("Đăng nhập thành công", true);
//        } else {
//            return new LoginMesage("Thông tin đăng nhập không chính xác!", false);
//        }
//    }

    @Override
    public boolean changePassword(KhachHangDto khachHangDto, String newPassword) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(khachHangDto.getEmail());
        if (khachHang == null) {
            return false;
        }
        String currentPassword = khachHangDto.getMatKhau();
        String encodedCurrentPassword = khachHang.getMatKhau();
        boolean isCurrentPasswordCorrect = passwordEncoder.matches(currentPassword, encodedCurrentPassword);
        if (isCurrentPasswordCorrect) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            khachHang.setMatKhau(encodedNewPassword);
            khachHangRepository.save(khachHang);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean suaThongTin(KhachHangDto khachHangDto, Integer id) {
        Optional<KhachHang> optionalKhachHang = khachHangRepository.findById(id);
        if (optionalKhachHang.isPresent()) {
            KhachHang khachHang = optionalKhachHang.get();


            khachHang.setTen(khachHangDto.getTen());
            khachHang.setSdt(khachHangDto.getSdt());
            khachHang.setDiaChi(khachHangDto.getDiaChi());
            khachHang.setNgaySinh(khachHangDto.getNgaySinh());
            khachHang.setGioiTinh(khachHangDto.getGioiTinh());


            khachHangRepository.save(khachHang);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void sendForgotPasswordEmail(String email) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);
        if (khachHang != null) {
            String newPlainTextPassword = GenCode.generatePassWordKhachHang();
            String encodedNewPassword = passwordEncoder.encode(newPlainTextPassword);
            khachHang.setMatKhau(encodedNewPassword);
            khachHangRepository.save(khachHang);
            customEmailSender.sendForgotPasswordEmail(khachHang, newPlainTextPassword);
        } else {
            throw new ResourceNotFoundException("Không tìm thấy email trong hệ thống.");
        }
    }



    @Override
    public boolean changeEmail(KhachHangDto khachHangDto, String newEmail) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(khachHangDto.getEmail());
        if (khachHang == null) {
            return false;
        }
        String currentPassword = khachHangDto.getMatKhau();
        String encodedCurrentPassword = khachHang.getMatKhau();
        boolean isCurrentPasswordCorrect = passwordEncoder.matches(currentPassword, encodedCurrentPassword);
        if (isCurrentPasswordCorrect) {
            khachHang.setEmail(newEmail);
            khachHangRepository.save(khachHang);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateImage(String image, String email) {
        this.khachHangRepository.updateImage(image, email);
    }
}