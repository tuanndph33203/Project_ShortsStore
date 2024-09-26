package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChuongTrinhTichDiemMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ThuHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChuongTrinhTichDiemResponse;
import com.example.website_ban_ao_the_thao_ps.repository.ChuongTrinhTichDiemRepository;
import com.example.website_ban_ao_the_thao_ps.repository.ThuHangRepository;
import com.example.website_ban_ao_the_thao_ps.repository.TiLeQuyDoiThuHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.ChuongTrinhTichDiemService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiChuongTrinhTichDiem.ACTIVE;

@Component
public class ChuongTrinhTichDiemServiceImpl implements ChuongTrinhTichDiemService {

    @Autowired
    ChuongTrinhTichDiemRepository chuongTrinhTichDiemRepository;

    @Autowired
    ChuongTrinhTichDiemMapper chuongTrinhTichDiemMapper;

    @Autowired
    private TiLeQuyDoiThuHangRepository tiLeQuyDoiThuHangRepository;

    @Autowired
    private ThuHangRepository thuHangRepository;

    @Autowired
    private ThuHangMapper thuHangMapper;

    @Override
    public Page<ChuongTrinhTichDiemResponse> pageChuongTrinhTichDiem(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ChuongTrinhTichDiem> chuongTrinhTichDiemPage = chuongTrinhTichDiemRepository.findAll(pageable);
        return chuongTrinhTichDiemPage.map(chuongTrinhTichDiemMapper::chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse);
    }

    @Override
    public Page<ChuongTrinhTichDiemResponse> pageSearchChuongTrinhTichDiem(Integer pageNo, Integer size, String search, String trangThai) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));

        ApplicationConstant.TrangThaiChuongTrinhTichDiem trangThaiChuongTrinhTichDiem = null;
        if (trangThai != null) {
            trangThaiChuongTrinhTichDiem = ApplicationConstant.TrangThaiChuongTrinhTichDiem.valueOf(trangThai);
        }

        Page<ChuongTrinhTichDiem> chuongTrinhTichDiemPage = chuongTrinhTichDiemRepository.pageSearch(pageable, search, trangThaiChuongTrinhTichDiem);
        return chuongTrinhTichDiemPage.map(chuongTrinhTichDiemMapper::chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse);
    }

    @Override
    public List<ChuongTrinhTichDiemResponse> listChuongTrinhTichDiemResponse() {
        return chuongTrinhTichDiemMapper.listChuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(chuongTrinhTichDiemRepository.getChuongTrinhTichDiemByTrangThai(ACTIVE));
    }

    @Override
    public ChuongTrinhTichDiemResponse createChuongTrinhTichDiem(CreateChuongTrinhTichDiemRequest createChuongTrinhTichDiemRequest) {
        try {
            Boolean passValidation = true;
            List<ChuongTrinhTichDiem> chuongTrinhTichDiemOriginal = this.chuongTrinhTichDiemRepository.findAll();
            LocalDateTime startDateBeforeTwentyHours = createChuongTrinhTichDiemRequest.getThoiGianBatDau().plusHours(24);
            if (createChuongTrinhTichDiemRequest.getThoiGianKetThuc().isBefore(startDateBeforeTwentyHours)){
                passValidation = false;
                throw new IllegalArgumentException("Thời gian kết thúc và thời gian bắt đầu phi cách nhau 24 giờ");
            }
            for (ChuongTrinhTichDiem chuongTrinhTichDiemValidation : chuongTrinhTichDiemOriginal) {
                if (chuongTrinhTichDiemValidation.getTrangThai().equals(ACTIVE)){
                    passValidation = false;
                    throw new IllegalArgumentException("Vẫn còn chương trình tích điểm đang hoạt động");
                }
            }
            if (createChuongTrinhTichDiemRequest.getThoiGianBatDau().isAfter(createChuongTrinhTichDiemRequest.getThoiGianKetThuc()) || createChuongTrinhTichDiemRequest.getThoiGianBatDau().isEqual(createChuongTrinhTichDiemRequest.getThoiGianKetThuc())){
                passValidation = false;
                throw new IllegalArgumentException("Thời gian kết thúc là thời gian sau của thời gian bắt đầu");
            }
            if (passValidation == true) {
                // Chuyển đổi yêu cầu tạo chương trình tích điểm thành đối tượng chương trình tích điểm
                ChuongTrinhTichDiem chuongTrinhTichDiem = chuongTrinhTichDiemMapper.createChuongTrinhTichDiemRequestToChuongTrinhTichDiemEntity(createChuongTrinhTichDiemRequest);

                // Sinh mã chương trình tích điểm và thiết lập ngày tạo và trạng thái
                chuongTrinhTichDiem.setMa(GenCode.generateChuongTrinhTichDiemCode());
                chuongTrinhTichDiem.setNgayTao(LocalDate.now());
                chuongTrinhTichDiem.setTrangThai(ACTIVE);

                // Lấy danh sách thứ hạng hoạt động
                List<ThuHang> thuHangList = thuHangRepository.getThuHangByTrangThai(ApplicationConstant.TrangThaiThuHang.ACTIVE);

                // Lưu chương trình tích điểm
                ChuongTrinhTichDiem cttd = chuongTrinhTichDiemRepository.save(chuongTrinhTichDiem);

                List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList = chuongTrinhTichDiem.getTiLeQuyDoiThuHangList();

                List<TiLeQuyDoiThuHang> newTiLeQuyDoiThuHangList = new ArrayList<>(); // Tạo danh sách mới

                for (int i = 0; i < thuHangList.size(); i++) {
                    ThuHang thuHang = thuHangList.get(i);
                    TiLeQuyDoiThuHang tlqd = tiLeQuyDoiThuHangList.get(i);

                    TiLeQuyDoiThuHang newTiLeQuyDoiThuHang = new TiLeQuyDoiThuHang();
                    newTiLeQuyDoiThuHang.setThuHang(thuHang);
                    newTiLeQuyDoiThuHang.setChuongTrinhTichDiem(cttd);
                    newTiLeQuyDoiThuHang.setHeSoNhan(tlqd.getHeSoNhan());
                    newTiLeQuyDoiThuHang.setNguongSoTien(tlqd.getNguongSoTien().divide(BigDecimal.valueOf(tlqd.getHeSoNhan())));
                    newTiLeQuyDoiThuHang.setTiLeChuyendoi(1);
                    newTiLeQuyDoiThuHang.setTrangThai(ApplicationConstant.TrangThaiTiLeQuyDoiThuHang.ACTIVE);
                    newTiLeQuyDoiThuHangList.add(newTiLeQuyDoiThuHang);
                }
                this.tiLeQuyDoiThuHangRepository.saveAll(newTiLeQuyDoiThuHangList);

                // Chuyển đổi đối tượng chương trình tích điểm thành đối tượng response và trả về
                return chuongTrinhTichDiemMapper.chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(chuongTrinhTichDiem);
            } else {
                return null;
            }
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Không thể tạo tích điểm. Có thể bản ghi bị trùng lặp." + ex);
        }
    }

    @Override
    public ChuongTrinhTichDiemResponse updateChuongTrinhTichDiem(UpdateChuongTrinhTichDiemRequest updateChuongTrinhTichDiemRequest, Integer id) {
        try {
            // Lấy chương trình tích điểm từ cơ sở dữ liệu
            ChuongTrinhTichDiem chuongTrinhTichDiem = chuongTrinhTichDiemRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy chương trình tích điểm với ID " + id));

            // Tạo danh sách tỷ lệ quy đổi thứ hạng từ request
            List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList = updateChuongTrinhTichDiemRequest.getTiLeQuyDoiThuHangList();

            // Tạo map chứa danh sách tỷ lệ quy đổi thứ hạng hiện có
            Map<Integer, TiLeQuyDoiThuHang> existingTiLeQuyDoiList = tiLeQuyDoiThuHangList.stream()
                    .collect(Collectors.toMap(TiLeQuyDoiThuHang::getId, Function.identity()));

            // Tạo danh sách tỷ lệ quy đổi thứ hạng mới
            List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangs = new ArrayList<>();

            // Duyệt qua danh sách tỷ lệ quy đổi thứ hạng từ request
            for (TiLeQuyDoiThuHang thuHang : updateChuongTrinhTichDiemRequest.getTiLeQuyDoiThuHangList()) {
                TiLeQuyDoiThuHang tiLeQuyDoiThuHang = existingTiLeQuyDoiList.get(thuHang.getId());
                if (tiLeQuyDoiThuHang != null) {
                    // Cập nhật thông tin tỷ lệ quy đổi thứ hạng
                    tiLeQuyDoiThuHang.setChuongTrinhTichDiem(chuongTrinhTichDiem);
                    tiLeQuyDoiThuHang.setTiLeChuyendoi(thuHang.getTiLeChuyendoi());
                    tiLeQuyDoiThuHang.setNguongSoTien(thuHang.getNguongSoTien());
                    tiLeQuyDoiThuHang.setHeSoNhan(thuHang.getHeSoNhan());
                    tiLeQuyDoiThuHang.setNgayCapNhat(LocalDate.now());
                    tiLeQuyDoiThuHang.setTrangThai(ApplicationConstant.TrangThaiTiLeQuyDoiThuHang.ACTIVE);

                    // Lưu tỷ lệ quy đổi thứ hạng đã cập nhật
                    this.tiLeQuyDoiThuHangRepository.save(tiLeQuyDoiThuHang);
                    tiLeQuyDoiThuHangs.add(tiLeQuyDoiThuHang);
                }
            }

            // Cập nhật thông tin chương trình tích điểm
            chuongTrinhTichDiem.setTiLeQuyDoiThuHangList(tiLeQuyDoiThuHangList);
            chuongTrinhTichDiem.setThoiGianBatDau(updateChuongTrinhTichDiemRequest.getThoiGianBatDau());
            chuongTrinhTichDiem.setThoiGianKetThuc(updateChuongTrinhTichDiemRequest.getThoiGianKetThuc());
            chuongTrinhTichDiem.setTen(updateChuongTrinhTichDiemRequest.getTen());
            chuongTrinhTichDiem.setNgayCapNhat(LocalDate.now());

            // Lưu lại chương trình tích điểm đã cập nhật
            chuongTrinhTichDiemRepository.save(chuongTrinhTichDiem);

            // Chuyển đổi entity chương trình tích điểm thành response
            return chuongTrinhTichDiemMapper.chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(chuongTrinhTichDiem);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Cập nhật tích điểm thất bại. Có thể có bản ghi trùng lặp." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Cập nhật tích điểm thất bại do lỗi không xác định." + ex);
        }
    }


    @Override
    public ChuongTrinhTichDiemResponse getOneChuongTrinhTichDiem(Integer id) {
        ChuongTrinhTichDiem chuongTrinhTichDiem = chuongTrinhTichDiemRepository.findById(id).orElseThrow(()
                -> new NotFoundException("ChuongTrinhTichDiem not found with id " + id));
        return chuongTrinhTichDiemMapper.chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(chuongTrinhTichDiem);
    }

    @Override
    public void removeOrRevertChuongTrinhTichDiem(String trangThaiChuongTrinhTichDiem, Integer id) {
        if (trangThaiChuongTrinhTichDiem.equals("ACTIVE")){
            System.out.println("trangThaiChuongTrinhTichDiem " + trangThaiChuongTrinhTichDiem);
            Boolean passValidation = true;
            List<ChuongTrinhTichDiem> chuongTrinhTichDiemOriginal = this.chuongTrinhTichDiemRepository.findAll();
            for (ChuongTrinhTichDiem chuongTrinhTichDiemValidation : chuongTrinhTichDiemOriginal) {
                if (chuongTrinhTichDiemValidation.getTrangThai().equals(ACTIVE)){
                    passValidation = false;
                    throw new IllegalArgumentException("Vẫn còn chương trình tích điểm đang hoạt động");
                }
            }
            if (passValidation == true) {
                chuongTrinhTichDiemRepository.removeOrRevert(trangThaiChuongTrinhTichDiem, id);
            }
        } else {
            chuongTrinhTichDiemRepository.removeOrRevert(trangThaiChuongTrinhTichDiem, id);
        }
    }

    @Override
    public byte[] exportExcelTichDiem() throws IOException {
        List<TiLeQuyDoiThuHang> data = tiLeQuyDoiThuHangRepository.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ChuongTrinhTichDiem");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("idChuongTrinhTichDiem");
        headerRow.createCell(1).setCellValue("maChuongTrinhTichDiem");
        headerRow.createCell(2).setCellValue("tenChuongTrinhTichDiem");
        headerRow.createCell(3).setCellValue("thoiGianBatDauChuongTrinhTichDiem");
        headerRow.createCell(4).setCellValue("thoiGianKetThucChuongTrinhTichDiem");
        headerRow.createCell(5).setCellValue("ngayTaoChuongTrinhTichDiem");
        headerRow.createCell(6).setCellValue("ngayCapNhatChuongTrinhTichDiem");
        headerRow.createCell(7).setCellValue("trangThaiChuongTrinhTichDiem");
        headerRow.createCell(8).setCellValue("idTiLeQuyDoiThuHang");
        headerRow.createCell(9).setCellValue("thuHang");
        headerRow.createCell(10).setCellValue("heSoNhanTiLeQuyDoiThuHang");
        headerRow.createCell(11).setCellValue("nguongSoTienTiLeQuyDoiThuHang");
        headerRow.createCell(12).setCellValue("tiLeChuyenDoiTiLeQuyDoiThuHang");
        headerRow.createCell(13).setCellValue("ngayCapNhatTiLeQuyDoiThuHang");
        headerRow.createCell(14).setCellValue("ngayTaoTiLeQuyDoiThuHang");
        headerRow.createCell(15).setCellValue("trangThaiTiLeQuyDoiThuHang");

        int rowNum = 1;
        for (TiLeQuyDoiThuHang tiLeQuyDoiThuHang : data) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getId(), ""));
            row.createCell(1).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getMa(), ""));
            row.createCell(2).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getTen(), ""));
            row.createCell(3).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getThoiGianBatDau(), ""));
            row.createCell(4).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getThoiGianKetThuc(), ""));
            row.createCell(5).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getNgayTao(), ""));
            row.createCell(6).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getNgayCapNhat(), ""));
            row.createCell(7).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getChuongTrinhTichDiem().getTrangThai(), ""));
            row.createCell(8).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getId(), ""));
            row.createCell(9).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getThuHang().getId(), ""));
            row.createCell(10).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getHeSoNhan(), ""));
            row.createCell(11).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getNguongSoTien(), ""));
            row.createCell(12).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getTiLeChuyendoi(), ""));
            row.createCell(13).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getNgayCapNhat(), ""));
            row.createCell(14).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getNgayTao(), ""));
            row.createCell(15).setCellValue(Objects.toString(tiLeQuyDoiThuHang.getTrangThai(), ""));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

}
