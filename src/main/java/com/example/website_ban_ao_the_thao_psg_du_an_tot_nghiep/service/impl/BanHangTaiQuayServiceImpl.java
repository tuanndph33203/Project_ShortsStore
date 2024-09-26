package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.CustomEmailSender;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
//import com.example.website_ban_ao_the_thao_ps.config.Config;
import com.example.website_ban_ao_the_thao_ps.config.VnpayConfig;
import com.example.website_ban_ao_the_thao_ps.dto.RequestPayment;
import com.example.website_ban_ao_the_thao_ps.dto.ResponsePayment;
import com.example.website_ban_ao_the_thao_ps.dto.UpdateThanhToanHoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.*;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.HoaDonChiTietMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.HoaDonMapper;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonChiTietResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import com.example.website_ban_ao_the_thao_ps.repository.*;
import com.example.website_ban_ao_the_thao_ps.service.BanHangTaiQuayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.HinhThucBanHang.DELIVERY;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.HinhThucBanHang.STORE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiHoaDon.*;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiHoaDonChiTiet.APPROVED;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiHoaDonChiTiet.REVERSE;

@Component
public class BanHangTaiQuayServiceImpl implements BanHangTaiQuayService {

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    ChuongTrinhTichDiemRepository chuongTrinhTichDiemRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    HoaDonMapper hoaDonMapper;

    @Autowired
    HoaDonChiTietMapper hoaDonChiTietMapper;

    @Autowired
    private CustomEmailSender customEmailSender;

    @Override
    public List<HoaDonResponse> getAllHoaDonCho() {
        return hoaDonMapper.listHoaDonEntityToHoaDonResponse(hoaDonRepository.getHoaDonByTrangThai(CONFIRMED));
    }

    @Override
    public HoaDonResponse getOneHoaDon(Integer id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + id));
        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDon);
    }

    @Override
    public void deleteHoaDonChiTiet(Integer id) {
        hoaDonChiTietRepository.deleteById(id);
    }


    @Override
    public HoaDonResponse createHoaDonCho(String email) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        if (nhanVien != null) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMa(GenCode.generateHoaDonCode());
            hoaDon.setNgayTao(LocalDate.now());
            hoaDon.setTrangThai(CONFIRMED);
            hoaDon.setNhanVien(nhanVien);
            HoaDon hd = hoaDonRepository.save(hoaDon);
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hd);
            lichSuHoaDon.setNhanVien(nhanVien);
            lichSuHoaDon.setMoTa("Tạo hoá đơn cho khách");
            lichSuHoaDon.setNgayTao(LocalDateTime.now());
            lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CREATED);
            lichSuHoaDonRepository.save(lichSuHoaDon);
            return hoaDonMapper.hoaDonEntityToHoaDonResponse(hd);
        }
        return null;
    }

    @Override
    public void createHoaDonChiTiet(Integer idHoaDon, Integer idCtsp) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHoaDon));
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(idCtsp).orElseThrow(() -> new NotFoundException("ChiTietSanPham not found with id " + idCtsp));

        // Tìm xem có HoaDonChiTiet nào có cùng id ChiTietSanPham không

        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDon.getHoaDonChiTietList().stream()
                .filter(hdct -> hdct.getChiTietSanPham().getId().equals(ctsp.getId()))
                .findFirst();
        if (hoaDonChiTietOptional.isEmpty()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(ctsp);
            hoaDonChiTiet.setSoLuong(1);
            hoaDonChiTiet.setGiaBan(ctsp.getSanPham().getGia());
            hoaDonChiTiet.setDonGia(ctsp.getSanPham().getGia());
            hoaDonChiTiet.setNgayTao(LocalDate.now());
            hoaDonChiTiet.setTrangThai(APPROVED);
            hoaDonChiTietRepository.save(hoaDonChiTiet);
        } else {
            HoaDonChiTiet hdct = hoaDonChiTietOptional.get();
            hdct.setSoLuong(hdct.getSoLuong() + 1);
            hdct.setDonGia(hdct.getDonGia().add(hdct.getGiaBan()));
            hdct.setNgayCapNhat(hdct.getNgayCapNhat());
            hoaDonChiTietRepository.save(hdct);
        }
    }

    @Override
    public HoaDonChiTietResponse truSoLuongHoaDonChiTiet(Integer idHdct) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idHdct));
        int soLuongMoi = hdct.getSoLuong() - 1;
        if (soLuongMoi <= 0) {
            hoaDonChiTietRepository.deleteById(idHdct);
            return null;
        } else {
            hdct.setSoLuong(soLuongMoi);
            hdct.setDonGia(hdct.getDonGia().subtract(hdct.getGiaBan()));
            return hoaDonChiTietMapper.hoaDonChiTietEntityToHoaDonChiTietResponse(hoaDonChiTietRepository.save(hdct));
        }
    }


    @Override
    public HoaDonChiTietResponse congSoLuongHoaDonChiTiet(Integer idHdct) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idHdct));

        int soLuongHienTai = hdct.getSoLuong();
        int soLuongTonKho = hdct.getChiTietSanPham().getSoLuong();
        if (soLuongHienTai + 1 > soLuongTonKho) {
            throw new NotFoundException("Số lượng vượt quá số lượng tồn kho");
        }
        hdct.setSoLuong(soLuongHienTai + 1);
        hdct.setDonGia(hdct.getDonGia().add(hdct.getGiaBan()));
        return hoaDonChiTietMapper.hoaDonChiTietEntityToHoaDonChiTietResponse(hoaDonChiTietRepository.save(hdct));
    }

    @Override
    public HoaDonChiTietResponse updateSoLuongAndDonGiaHoaDonChiTiet(Integer idHdct, Integer soLuong) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idHdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idHdct));

        int soLuongTonKho = hdct.getChiTietSanPham().getSoLuong();
        if (soLuong > soLuongTonKho) {
            throw new NotFoundException("Số lượng vượt quá số lượng tồn kho");
        }

        if (soLuong == 0) {
            hoaDonChiTietRepository.deleteById(idHdct);
            return null;
        } else {
            hdct.setSoLuong(soLuong);
            hdct.setDonGia(hdct.getGiaBan().multiply(BigDecimal.valueOf(soLuong)));
            return hoaDonChiTietMapper.hoaDonChiTietEntityToHoaDonChiTietResponse(hoaDonChiTietRepository.save(hdct));
        }
    }


    @Override
    public HoaDonResponse updateKhachHangHoaDon(Integer idHd, Integer idKhachHang) {
        HoaDon hoaDon = hoaDonRepository.findById(idHd)
                .orElseThrow(() -> new NotFoundException("HoaDon không tồn tại với id " + idHd));

        KhachHang khachHang = khachHangRepository.findById(idKhachHang)
                .orElse(null);

        hoaDon.setKhachHang(khachHang);

        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDonRepository.save(hoaDon));
    }

    @Override
    public HoaDonResponse updateDiaChiHoaDon(Integer idHd, LocalDate ngayMuonNhan, String diaChi, String tenNguoiNhan, String sdtNguoiNhan, String sdtNguoiShip) {
        HoaDon hoaDon = hoaDonRepository.findById(idHd).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHd));
        hoaDon.setDiaChi(diaChi);
        hoaDon.setTenNguoiNhan(tenNguoiNhan);
        hoaDon.setSdtNguoiNhan(sdtNguoiNhan);
        hoaDon.setSdtNguoiShip(sdtNguoiShip);
        return hoaDonMapper.hoaDonEntityToHoaDonResponse(hoaDonRepository.save(hoaDon));
    }

    @Override
    public void updateTrangThaiHoaDon(Integer idHoaDon, ApplicationConstant.TrangThaiHoaDon trangThaiHoaDon,String email) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(email);
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHoaDon));
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNhanVien(nhanVien);
        lichSuHoaDon.setNgayTao(LocalDateTime.now());
        switch (trangThaiHoaDon) {
            case SHIPPING:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.SHIPPING);
                lichSuHoaDon.setMoTa("Đã giao cho đơn vị vận chuyển");
                break;
            case CONFIRMED:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CONFIRMED);
                lichSuHoaDon.setMoTa("Đã xác nhận thông tin thanh toán");
                List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
                for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                    ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                    ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                    chiTietSanPhamRepository.save(ctsp);
                }
                break;
            case APPROVED:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.APPROVED);
                lichSuHoaDon.setMoTa("Đơn hàng thành công");

                List<ChuongTrinhTichDiem> chuongTrinhTichDiemActiveList = chuongTrinhTichDiemRepository.findAll().stream()
                        .filter(cttd -> ApplicationConstant.TrangThaiChuongTrinhTichDiem.ACTIVE == cttd.getTrangThai())
                        .collect(Collectors.toList());

                if (!chuongTrinhTichDiemActiveList.isEmpty()) {
                    ChuongTrinhTichDiem chuongTrinhTichDiem = chuongTrinhTichDiemActiveList.get(0);
                    List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList = chuongTrinhTichDiem.getTiLeQuyDoiThuHangList();

                    if (tiLeQuyDoiThuHangList != null) {
                        for (TiLeQuyDoiThuHang tlqd : tiLeQuyDoiThuHangList) {
                            ThuHang thuHangKhachHang = hoaDon.getKhachHang().getThuHang();
                            ThuHang thuHangTlqd = tlqd.getThuHang();

                            if (thuHangKhachHang != null && thuHangTlqd != null && thuHangKhachHang.getId() == thuHangTlqd.getId()) {
                                hoaDon.setSoDiemCong(Double.valueOf(String.valueOf(hoaDon.getThanhTien().divide(tlqd.getNguongSoTien()))));
                                // Break out of the loop if the condition is met (optional, depending on your logic)
                                break;
                            }
                        }
                    }
                }
                hoaDonRepository.save(hoaDon);
                break;
            case CANCELLED:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CANCELLED);
                lichSuHoaDon.setMoTa("Huỷ đơn hàng thành công");
                List<HoaDonChiTiet> hdctList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);

                for (HoaDonChiTiet hdct : hdctList) {
                    hdct.setTrangThai(REVERSE);

                    if (REVERSE.equals(hdct.getTrangThai())) {
                        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy ChiTietSanPham"));

                        ctsp.setSoLuong(ctsp.getSoLuong() + hdct.getSoLuong());
                        chiTietSanPhamRepository.save(ctsp);
                    }
                }
                hoaDonChiTietRepository.saveAll(hdctList);
                hoaDon.setThanhTien(BigDecimal.valueOf(0));
                hoaDon.setTrangThai(CANCELLED);
                hoaDonRepository.save(hoaDon);
                break;
            case REVERSE:
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.REVERSED);
                lichSuHoaDon.setMoTa("Trả hàng");
                break;
            default:
                break;
        }
        lichSuHoaDonRepository.save(lichSuHoaDon);
        System.out.println(trangThaiHoaDon);
        hoaDonRepository.updateTrangThaiHoaDon(String.valueOf(trangThaiHoaDon), idHoaDon);
    }

    @Override
    public void updateTrangThaiHoaDonChiTiet(Integer idhdct, Integer soLuong) {
        HoaDonChiTiet hdct = hoaDonChiTietRepository.findById(idhdct).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + idhdct));
        ChiTietSanPham ctsp = hdct.getChiTietSanPham();

        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không hợp lệ.");
        }
        if (soLuong > ctsp.getSoLuong()) {
            throw new IllegalArgumentException("Số lượng vượt quá số lượng trong hóa đơn chi tiết.");
        }

        ctsp.setSoLuong(ctsp.getSoLuong() + soLuong);
        chiTietSanPhamRepository.save(ctsp);

        HoaDon hoaDon = hdct.getHoaDon();
        BigDecimal sanPhamGia = ctsp.getSanPham().getGia();
        BigDecimal giamTien = sanPhamGia.multiply(BigDecimal.valueOf(soLuong));
        hoaDon.setThanhTien(hoaDon.getThanhTien().subtract(giamTien));

        if (hoaDon.getHoaDonChiTietList().isEmpty()) {
            hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.REVERSE);
        }
        hoaDonRepository.save(hoaDon);
        hoaDonChiTietRepository.updateTrangThaiHoaDonChiTiet(REVERSE, idhdct);
    }

    @Override
    public void thanhToanHoaDon(Integer idHd, UpdateThanhToanHoaDon updateThanhToanHoaDon) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(updateThanhToanHoaDon.getEmail());
        HoaDon hoaDon = hoaDonRepository.findById(idHd).orElseThrow(() -> new NotFoundException("HoaDon not found with id " + idHd));
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
        try {
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                if (hdct.getSoLuong() > ctsp.getSoLuong()) {
                    throw new NotFoundException("Số lượng sản phẩm trong giỏ hàng không được vượt quá số lượng sản phẩm trong kho");
                }
            }
        } catch (NotFoundException e) {
            System.out.println(e);
            // Handle the exception or log it appropriately
        } catch (Exception e) {
            // Handle other exceptions
            System.out.println(e);
        }
        hoaDon.setMoTa(updateThanhToanHoaDon.getMoTa());
        hoaDon.setNgayMuonNhan(updateThanhToanHoaDon.getNgayMuonNhan());
        if (updateThanhToanHoaDon.getPhuongThucThanhToan() == ApplicationConstant.HinhThucThanhToan.CASH) {
            hoaDon.setSoTienMat(updateThanhToanHoaDon.getThanhTien());
        } else {
            hoaDon.setSoTienChuyenKhoan(updateThanhToanHoaDon.getThanhTien());
        }
        hoaDon.setPhanTramGiamGia(updateThanhToanHoaDon.getPhanTramGiamGia());
        hoaDon.setSoTienVanChuyen(updateThanhToanHoaDon.getSoTienVanChuyen());
        hoaDon.setPhuongThucThanhToan(updateThanhToanHoaDon.getPhuongThucThanhToan());
        hoaDon.setThanhTien(updateThanhToanHoaDon.getThanhTien());
        hoaDon.setDiaChi(updateThanhToanHoaDon.getDiaChi());
        hoaDon.setTenNguoiNhan(updateThanhToanHoaDon.getTenNguoiNhan());
        hoaDon.setSdtNguoiNhan(updateThanhToanHoaDon.getSdtNguoiNhan());
        hoaDon.setSdtNguoiShip(updateThanhToanHoaDon.getSdtNguoiShip());
        hoaDon.setHinhThucBanHang(updateThanhToanHoaDon.getHinhThucBanHang());
        HoaDon hd = hoaDonRepository.save(hoaDon);
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNhanVien(null);
        lichSuHoaDon.setNgayTao(LocalDateTime.now());

        if (hd.getPhuongThucThanhToan() == ApplicationConstant.HinhThucThanhToan.CASH && hd.getHinhThucBanHang() == STORE) {
            hd.setMaGiaoDichTienMat(GenCode.generateGiaoDichCode());
            hd.setSoTienMat(hoaDon.getThanhTien());
            hd.setTrangThai(ApplicationConstant.TrangThaiHoaDon.APPROVED);
            List<ChuongTrinhTichDiem> chuongTrinhTichDiemActiveList = chuongTrinhTichDiemRepository.findAll().stream()
                    .filter(cttd -> ApplicationConstant.TrangThaiChuongTrinhTichDiem.ACTIVE == cttd.getTrangThai())
                    .collect(Collectors.toList());

            if (!chuongTrinhTichDiemActiveList.isEmpty()) {
                ChuongTrinhTichDiem chuongTrinhTichDiem = chuongTrinhTichDiemActiveList.get(0);

                List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList = chuongTrinhTichDiem.getTiLeQuyDoiThuHangList();

                if (tiLeQuyDoiThuHangList != null) {
                    for (TiLeQuyDoiThuHang tlqd : tiLeQuyDoiThuHangList) {
                        ThuHang thuHangKhachHang = hd.getKhachHang().getThuHang();
                        ThuHang thuHangTlqd = tlqd.getThuHang();

                        if (thuHangKhachHang != null && thuHangTlqd != null && thuHangKhachHang.getId() == thuHangTlqd.getId()) {
                            hd.setSoDiemCong(Double.valueOf(String.valueOf(hd.getThanhTien().divide(tlqd.getNguongSoTien()))));
                            break;
                        }
                    }
                }
            }
            lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.APPROVED);
            lichSuHoaDon.setMoTa("Đơn hàng thành công");
            lichSuHoaDon.setNhanVien(nhanVien);
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                chiTietSanPhamRepository.save(ctsp);
            }
        }
        if (hd.getPhuongThucThanhToan() == ApplicationConstant.HinhThucThanhToan.CASH && hd.getHinhThucBanHang() == DELIVERY) {
            hd.setMaGiaoDichTienMat(GenCode.generateGiaoDichCode());
            hd.setSoTienMat(hoaDon.getThanhTien());
            hd.setTrangThai(ApplicationConstant.TrangThaiHoaDon.PAYMENT_SUCCESS);
            lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CONFIRMED);
            lichSuHoaDon.setMoTa("Đơn hàng thanh toán thành công, chờ giao cho đơn vị vận chuyển ");
            lichSuHoaDon.setNhanVien(nhanVien);
            for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                chiTietSanPhamRepository.save(ctsp);
            }
        }

        if (hd.getPhuongThucThanhToan() == ApplicationConstant.HinhThucThanhToan.BANKING) {
            lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CONFIRMED);
            lichSuHoaDon.setMoTa("Chờ xác nhận thông tin thanh toán");
            lichSuHoaDon.setNhanVien(nhanVien);
            hd.setTrangThai(CONFIRMED);
        }

        hoaDonRepository.save(hd);
        lichSuHoaDonRepository.save(lichSuHoaDon);

        if (hoaDon.getKhachHang().getEmail() != null) {
            this.customEmailSender.sendInvoiceEmail(hoaDon);
        }
    }

    @Override
    public List<HoaDonResponse> getAllListHoaDonResponseStatusPending(ApplicationConstant.TrangThaiHoaDon trangThai) {
        return hoaDonMapper.listHoaDonEntityToHoaDonResponse(hoaDonRepository.getHoaDonsByTrangThai(trangThai));
    }

    @Override
    public String createOrder(String orderInfor, String urlReturn, String gia, String maHd) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);


        vnp_Params.put("vnp_Amount", String.valueOf(Long.parseLong(gia) * 100));

        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        HoaDon hoaDon = hoaDonRepository.findHoaDonByMa(maHd);
        hoaDon.setMaGiaoDichChuyenKhoan(vnp_TxnRef);
        hoaDonRepository.save(hoaDon);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        urlReturn += VnpayConfig.vnp_Returnurl1;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public int luuHoaDon(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII);
            fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");
        String signValue = VnpayConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                String totalPrice = request.getParameter("vnp_Amount");
                String vnp_TxnRef = request.getParameter("vnp_TxnRef");

                HoaDon hoaDon = hoaDonRepository.findHoaDonByMaGiaoDichChuyenKhoan(vnp_TxnRef);
                hoaDon.setSoTienChuyenKhoan(BigDecimal.valueOf(Long.parseLong(totalPrice)));
                if (hoaDon.getHinhThucBanHang() == STORE) {
                    hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.APPROVED);
                    LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
                    lichSuHoaDon.setHoaDon(hoaDon);
                    lichSuHoaDon.setNhanVien(hoaDon.getNhanVien());
                    lichSuHoaDon.setNgayTao(LocalDateTime.now());
                    lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.APPROVED);
                    lichSuHoaDon.setMoTa("Đơn hàng thành thông");
                    List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
                    for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                        ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                        chiTietSanPhamRepository.save(ctsp);
                    }
                }
                if (hoaDon.getHinhThucBanHang() == DELIVERY) {
                    hoaDon.setTrangThai(PAYMENT_SUCCESS);
                    LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
                    lichSuHoaDon.setHoaDon(hoaDon);
                    lichSuHoaDon.setNhanVien(hoaDon.getNhanVien());
                    lichSuHoaDon.setNgayTao(LocalDateTime.now());
                    lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CONFIRMED);
                    lichSuHoaDon.setMoTa("Chờ giao cho đơn vị vận chuyển");
                    List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.getHoaDonChiTietByHoaDon(hoaDon);
                    for (HoaDonChiTiet hdct : hoaDonChiTietList) {
                        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(hdct.getChiTietSanPham().getId()).get();
                        ctsp.setSoLuong(ctsp.getSoLuong() - hdct.getSoLuong());
                        chiTietSanPhamRepository.save(ctsp);
                    }
                }
                hoaDonRepository.save(hoaDon);
                return 1;
            }
        } else {
            return -1;
        }
        return 0;
    }
}
