package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.CustomEmailSender;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_ps.dto.NhanVienDto;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.entity.VaiTro;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.exception.ResourceNotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.NhanVienMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NhanVienResponse;
import com.example.website_ban_ao_the_thao_ps.repository.NhanVienRepository;
import com.example.website_ban_ao_the_thao_ps.repository.VaiTroRepository;
import com.example.website_ban_ao_the_thao_ps.service.NhanVienService;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    VaiTroRepository vaiTroRepository;

    @Autowired
    NhanVienMapper nhanVienMapper;

    @Autowired
    private CustomEmailSender customEmailSender;

    @Override
    public Page<NhanVienResponse> pageNhanVien(Integer pageNo, Integer size){
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepository.findAll(pageable);
        return nhanVienPage.map(nhanVienMapper::nhanVienEntityToNhanVienResponse);
    }

    @Override
    public Page<NhanVienResponse> pageSearchNhanVien(Integer pageNo,Integer size,String search, ApplicationConstant.TrangThaiTaiKhoan trangThai, Integer vaiTroId) {
        Pageable pageable = PageRequest.of(pageNo, size,Sort.by(Sort.Direction.DESC, "id"));
        Page<NhanVien> nhanVienPage = nhanVienRepository.pageSearch(pageable, search, trangThai, vaiTroId);
        return nhanVienPage.map(nhanVienMapper::nhanVienEntityToNhanVienResponse);
    }

    @Override
    public List<NhanVienResponse> listNhanVienResponse() {
        return nhanVienMapper.listNhanVienEntityToNhanVienResponse(nhanVienRepository.getNhanVienByTrangThai(ApplicationConstant.TrangThaiTaiKhoan.ACTIVE));
    }

    @Override
    public NhanVienResponse findByEmail(String email) {
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.findByEmail(email));
    }

    @Override
    public byte[] exportExcelNhanVien() throws IOException {
        List<NhanVien> data = nhanVienRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("NhanVien");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("id");
        headerRow.createCell(1).setCellValue("vaiTro");
        headerRow.createCell(2).setCellValue("ma");
        headerRow.createCell(3).setCellValue("ten");
        headerRow.createCell(4).setCellValue("anh");
        headerRow.createCell(5).setCellValue("gioiTinh");
        headerRow.createCell(6).setCellValue("ngaySinh");
        headerRow.createCell(7).setCellValue("diaChi");
        headerRow.createCell(8).setCellValue("email");
        headerRow.createCell(9).setCellValue("sdt");
        headerRow.createCell(10).setCellValue("soCanCuocCongDan");
        headerRow.createCell(11).setCellValue("matKhau");
        headerRow.createCell(12).setCellValue("ngayTao");
        headerRow.createCell(13).setCellValue("ngayCapNhat");
        headerRow.createCell(14).setCellValue("trangThai");

        int rowNum = 1;
        for (NhanVien nhanVien : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(nhanVien.getId());
            row.createCell(1).setCellValue(nhanVien.getVaiTro().getId());
            row.createCell(2).setCellValue(nhanVien.getMa());
            row.createCell(3).setCellValue(nhanVien.getTen());
            row.createCell(4).setCellValue(nhanVien.getAnh());
            row.createCell(5).setCellValue(nhanVien.getGioiTinh() != null ? nhanVien.getGioiTinh().booleanValue() : false);
            row.createCell(6).setCellValue(String.valueOf(nhanVien.getNgaySinh()));
            row.createCell(7).setCellValue(nhanVien.getDiaChi());
            row.createCell(8).setCellValue(nhanVien.getEmail());
            row.createCell(9).setCellValue(nhanVien.getSdt());
            row.createCell(10).setCellValue(nhanVien.getSoCanCuocCongDan());
            row.createCell(11).setCellValue(nhanVien.getMatKhau());
            row.createCell(12).setCellValue(String.valueOf(nhanVien.getNgayTao()));
            row.createCell(13).setCellValue(String.valueOf(nhanVien.getNgayCapNhat()));
            row.createCell(14).setCellValue(String.valueOf(nhanVien.getTrangThai()));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    @Async
    @Override
    public NhanVienResponse create(CreateNhanVienRequest createNhanVienRequest) {
        Boolean passValidation = true;
        if (createNhanVienRequest.getAnh() != null){
            if (createNhanVienRequest.getAnh(). length() > 250) {
                passValidation = false;
                throw new DataIntegrityViolationException("Tên ảnh quá dài. Hãy sửa lại tên ảnh!");
            }
        }
        if (passValidation) {
            try {
                NhanVien nhanVien = nhanVienMapper.createNhanVienRequestToNhanVienEntity(createNhanVienRequest);
                nhanVien.setMa(GenCode.generateNhanVienCode());
                String genPassWord = GenCode.generatePassWordNhanVien();
//            nhanVien.setMatKhau(genPassWord); // Đặt mật khẩu chưa mã hóa vào đối tượng NhanVien
                nhanVien.setNgayTao(LocalDate.now());
                nhanVien.setTrangThai(ApplicationConstant.TrangThaiTaiKhoan.ACTIVE);

                if(nhanVienRepository.findBySdt(nhanVien.getSdt()) != null){
                    throw new InternalServerException("Số điện thoại này đã tồn tại " + nhanVien.getSdt());
                }

                if(nhanVienRepository.findBySoCanCuocCongDan(nhanVien.getSoCanCuocCongDan()) != null){
                    throw new InternalServerException("Số căn cước công dân này đã tồn tại " + nhanVien.getSoCanCuocCongDan());
                }

                if(nhanVienRepository.findByEmail(nhanVien.getEmail()) != null){
                    throw new InternalServerException("Email này đã tồn tại " + nhanVien.getEmail());
                }

                customEmailSender.signupNhanVienSendEmail(nhanVien, genPassWord);
                System.out.println(genPassWord);
                String encodedPassword = passwordEncoder.encode(genPassWord);
                nhanVien.setMatKhau(encodedPassword);
                return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.save(nhanVien));

            } catch (DataIntegrityViolationException ex) {
                throw new DuplicateRecordException("Failed to create nhan vien. Possibly duplicate record." + ex);
            }
        } else {
            return null;
        }
    }

//    @Override
//    public List<NhanVienInfo> listEmailAndSdtAndCccdNhanVien() {
//        List<NhanVien> nhanVienList = nhanVienRepository.findAll();
//        List<NhanVienInfo> nhanVienListInfo = new ArrayList<>();
//        for (NhanVien nhanVien : nhanVienList) {
//            NhanVienInfo nhanVienInfo = new NhanVienInfo();
//            nhanVienInfo.setEmail(nhanVien.getEmail());
//            nhanVienInfo.setSdt(nhanVien.getSdt());
//            nhanVienInfo.setSoCanCuocCongDan(nhanVien.getSoCanCuocCongDan());
//            nhanVienListInfo.add(nhanVienInfo);
//        }
//        return nhanVienListInfo;
//    }

    @Override
    @Transactional
    public NhanVienResponse update(UpdateNhanVienRequest updateNhanVienRequest, Integer id) {
        try {

            NhanVien nhanVien = nhanVienRepository.findById(id).orElseThrow(() -> new NotFoundException("NhanVien not found with id " + id));
            VaiTro vaiTro = nhanVien.getVaiTro();

            if (vaiTro == null || vaiTro.getId() == null) {
                throw new NotFoundException("VaiTro not found with id " + id);
            }

            NhanVien updateNhanVien = nhanVienMapper.updateNhanVienRequestToNhanVienEntity(updateNhanVienRequest);

            for (NhanVien kh : nhanVienRepository.findAll()){
                if(kh.getEmail().equals(updateNhanVien.getEmail()) && kh.getId() != nhanVien.getId()){
                    throw new InternalServerException("Email này đã tồn tại " + kh.getEmail());
                }
            }

            for (NhanVien kh : nhanVienRepository.findAll()){
                if(kh.getSoCanCuocCongDan().equals(updateNhanVien.getSoCanCuocCongDan()) && kh.getId() != nhanVien.getId()){
                    throw new InternalServerException("Số căn cước công dân này đã tồn tại " + kh.getSoCanCuocCongDan());
                }
            }

            for (NhanVien kh : nhanVienRepository.findAll()){
                if(kh.getSdt().equals(updateNhanVien.getSdt()) && kh.getId() != nhanVien.getId()){
                    throw new InternalServerException("Số điện thoại này đã tồn tại " + kh.getSdt());
                }
            }

            nhanVien.setVaiTro(updateNhanVien.getVaiTro());
            nhanVien.setTen(updateNhanVien.getTen());
            nhanVien.setAnh(updateNhanVien.getAnh());
            nhanVien.setGioiTinh(updateNhanVien.getGioiTinh());
            nhanVien.setNgaySinh(updateNhanVien.getNgaySinh());
            nhanVien.setSoCanCuocCongDan(updateNhanVien.getSoCanCuocCongDan());
            nhanVien.setDiaChi(updateNhanVien.getDiaChi());
            nhanVien.setEmail(updateNhanVien.getEmail());
            nhanVien.setSdt(updateNhanVien.getSdt());
            nhanVien.setNgayCapNhat(LocalDate.now());

            return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.save(nhanVien));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update nhan vien. Possibly duplicate record." + ex);
        }
    }

    @Override
    public NhanVienResponse getOne(Integer id) {
        NhanVien nhanVien = nhanVienRepository.findById(id).orElseThrow(() -> new NotFoundException("NhanVien not found with id " + id));
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVien);
    }

    @Override
    public NhanVienResponse getOneNhanVienByEmail(String email) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVien);
    }

    @Override
    public void removeOrRevert(String trangThaiNhanVien ,Integer id) {
        nhanVienRepository.removeOrRevert(trangThaiNhanVien, id);
    }


    @Override
    public LoginMesage loginNhanVien(NhanVienDto nhanVienDto) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(nhanVienDto.getEmail());
        if (nhanVien == null) {
            return new LoginMesage("Thông tin đăng nhập không chính xác!", false);
        }
        String password = nhanVienDto.getMatKhau();
        String encodedPassword = nhanVien.getMatKhau();
        boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
        if (isPwdRight) {
            return new LoginMesage("Đăng nhập thành công", true);
        } else {
            return new LoginMesage("Thông tin đăng nhập không chính xác!", false);
        }
    }

    @Override
    public boolean changeEmail(NhanVienDto nhanVienDto, String newEmailNv) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(nhanVienDto.getEmail());
        if (nhanVien == null) {
            return false;
        }
        String currentPassword = nhanVien.getMatKhau();
        String encodedCurrentPassword = nhanVien.getMatKhau();
        boolean isCurrentPasswordCorrect = passwordEncoder.matches(currentPassword, encodedCurrentPassword);
        if (isCurrentPasswordCorrect) {
            nhanVien.setEmail(newEmailNv);
            nhanVienRepository.save(nhanVien);
            return true;
        } else {
            return false;
        }


    }

    @Override
    public void sendForgotPasswordEmailForNhanVien(String email) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        if (nhanVien != null) {
            String newPlainTextPassword = GenCode.generatePassWordNhanVien();
            String encodedNewPassword = passwordEncoder.encode(newPlainTextPassword);
            nhanVien.setMatKhau(encodedNewPassword);
            nhanVienRepository.save(nhanVien);
            customEmailSender.sendForgotPasswordEmailForNhanVien(nhanVien, newPlainTextPassword);
        } else {
            throw new ResourceNotFoundException("Không tìm thấy thông tin nhân viên trong hệ thống.");
        }
    }
    @Override
    public void updatePassword(String newPassword, String email) {
        this.nhanVienRepository.updatePassword(newPassword, email);
    }

    @Override
    public void updateImageNV(String image, String email) {
        this.nhanVienRepository.updateImageNV(image, email);
    }

}