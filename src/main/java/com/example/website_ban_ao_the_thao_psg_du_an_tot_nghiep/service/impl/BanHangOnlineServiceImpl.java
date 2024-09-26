package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.CustomEmailSender;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.config.VnpayConfig;

import com.example.website_ban_ao_the_thao_ps.entity.*;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.*;

import com.example.website_ban_ao_the_thao_ps.model.response.GioHangChiTietResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_ps.repository.*;
import com.example.website_ban_ao_the_thao_ps.service.BanHangOnlineService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class BanHangOnlineServiceImpl implements BanHangOnlineService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    private DiaChiRepository diaChiRepository;
    @Autowired
    private GioHangChiTietMapper gioHangChiTietMapper;
    @Autowired
    private DiaChiMapper diaChiMapper;
    @Autowired
    private KhachHangMapper khachHangMapper;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private GioHangMapper gioHangMapper;
    @Autowired
    private HoaDonMapper hoaDonMapper;
    @Autowired
    private GioHangRepository gioHangRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private LichSuHoaDonRepository lichSuHoaDonRepository;

    @Autowired
    private CustomEmailSender customEmailSender;
    @Override
    public GioHangChiTietResponse congSoLuongGioHangChiTiet(Integer id) {

        GioHangChiTiet ghct = gioHangChiTietRepository.findById(id).orElseThrow(() -> new NotFoundException("GioHangChiTiet not found with id " + id));

        int soLuongHienTai = ghct.getSoLuong();
        int soLuongTonKho = ghct.getChiTietSanPham().getSoLuong();
        if (soLuongHienTai + 1 > soLuongTonKho) {
            throw new NotFoundException("Số lượng vượt quá số lượng tồn kho");
        }
        ghct.setSoLuong(soLuongHienTai + 1);
        ghct.setDonGia(ghct.getDonGia().add(ghct.getGiaBan()));
        return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTietRepository.save(ghct));


    }
    @Override
    public GioHangChiTietResponse capNhapSoLuongGioHangChiTiet(Integer id, Integer soLuong) {
        GioHangChiTiet ghct = gioHangChiTietRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy id Giỏ Hàng Chi Tiết"));
        if (soLuong <= 0) {
            gioHangChiTietRepository.deleteById(ghct.getId());
            return null;
        } else {
            ghct.setSoLuong(soLuong);
            ghct.setDonGia(ghct.getGiaBan().multiply(BigDecimal.valueOf(soLuong)));
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTietRepository.save(ghct));
        }
    }
    @Override
    public GioHangChiTietResponse delete(Integer id) {
        GioHangChiTiet ghct = gioHangChiTietRepository.findById(id).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + id));
        gioHangChiTietRepository.delete(ghct);
        return null;
    }

    @Override
    public GioHangChiTietResponse updateSoLuongTrongGioHang(Integer id, Integer soLuong) {

        GioHangChiTiet ghct = gioHangChiTietRepository.findById(id).orElseThrow(() -> new NotFoundException("HoaDonChiTiet not found with id " + id));

        int soLuongTonKho = ghct.getChiTietSanPham().getSoLuong();
        if (soLuong > soLuongTonKho) {
            throw new NotFoundException("Số lượng vượt quá số lượng tồn kho");
        }

        if (soLuong == 0) {
            gioHangChiTietRepository.deleteById(id);
            return null;
        } else {
            ghct.setSoLuong(soLuong);
            ghct.setDonGia(ghct.getGiaBan().multiply(BigDecimal.valueOf(soLuong)));
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTietRepository.save(ghct));
        }

    }


    @Override
    public GioHangChiTietResponse truSoLuongGioHangChiTiet(Integer id) {

        GioHangChiTiet ghct = gioHangChiTietRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy id Giỏ Hàng Chi Tiết"));
        int soLuongMoi = ghct.getSoLuong() - 1;
        if (soLuongMoi <= 0) {
            gioHangChiTietRepository.deleteById(ghct.getId());
            return null;
        } else {
            ghct.setSoLuong(soLuongMoi);
            ghct.setDonGia(ghct.getDonGia().subtract(ghct.getGiaBan()));
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTietRepository.save(ghct));
        }
    }
    @Override
    public GioHangResponse getOneGioHang(String email) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);
        Integer idGh = khachHang.getGioHangList().get(0).getId();
        GioHang gioHang = gioHangRepository.findById(idGh).orElseThrow(() -> new NotFoundException("Không tìm thấy id Giỏ Hàng"));

        return gioHangMapper.gioHangEntityToGioHangResponse(gioHang);
    }
    @Override
    public GioHangResponse getOneGioHangKhongTaiKhoan(Integer id) {
        GioHang gioHang = gioHangRepository.findById(id).orElseThrow(null);
        return gioHangMapper.gioHangEntityToGioHangResponse(gioHang);
    }
    @Override
    public KhachHangResponse getOneKhachHang(String email) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);
        return khachHangMapper.khachHangEntityToKhachHangResponse(khachHang);
    }

    @Override
    public GioHangChiTietResponse addGioHangChiTiet(Integer idctsp, Integer soLuong, String email) {

        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);

        List<GioHang> gioHangList = khachHang.getGioHangList();
        GioHang gioHang = gioHangList.get(0);

        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idctsp)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy id Chi Tiet San Pham"));
        int soLuongTrongKho = chiTietSanPham.getSoLuong();
        if (soLuong > soLuongTrongKho) {
            throw new IllegalArgumentException("Số lượng sản phẩm thêm vào vượt quá số lượng tồn kho.");
        }
        GioHangChiTiet existingChiTiet = gioHang.getGioHangChiTietList().stream()
                .filter(ghct -> ghct.getChiTietSanPham().getId().equals(chiTietSanPham.getId()))
                .findFirst()
                .orElse(null);
        if (existingChiTiet != null) {
            int tongSoLuong = existingChiTiet.getSoLuong() + soLuong;
            if (tongSoLuong > soLuongTrongKho) {
                throw new IllegalArgumentException("Tổng số lượng sản phẩm trong giỏ hàng vượt quá số lượng tồn kho.");
            }
            existingChiTiet.setSoLuong(existingChiTiet.getSoLuong() + soLuong);
            existingChiTiet.setDonGia(chiTietSanPham.getSanPham().getGia().multiply(BigDecimal.valueOf(existingChiTiet.getSoLuong())));
            existingChiTiet.setNgayCapNhat(LocalDate.now());
            gioHangChiTietRepository.save(existingChiTiet);
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(existingChiTiet);
        } else {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTiet.setChiTietSanPham(chiTietSanPham);
            gioHangChiTiet.setGiaBan(chiTietSanPham.getSanPham().getGia());
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setDonGia(chiTietSanPham.getSanPham().getGia().multiply(BigDecimal.valueOf(soLuong)));
            gioHangChiTiet.setTrangThai(ApplicationConstant.TrangThaiGioHang.CANCELLED);
            gioHangChiTiet.setNgayTao(LocalDate.now());
            gioHangChiTietRepository.save(gioHangChiTiet);
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTiet);
        }
    }
    @Override
    public GioHangChiTietResponse addGioHangChiTietKhongTk(Integer idCtsp, Integer soLuong, String id) {
        GioHang gioHang = gioHangRepository.findById(Integer.valueOf(id)).orElseThrow(null);
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idCtsp).orElseThrow(() -> new NotFoundException("Không tìm thấy id Chi Tiet San Pham"));
        int soLuongTrongKho = chiTietSanPham.getSoLuong();
        if (soLuong > soLuongTrongKho) {
            throw new IllegalArgumentException("Số lượng sản phẩm trong giỏ hàng vượt quá số lượng tồn kho.");
        }
        Optional<GioHangChiTiet> existingChiTietOptional = gioHang.getGioHangChiTietList().stream()
                .filter(ghct -> ghct.getChiTietSanPham().getId().equals(chiTietSanPham.getId()))
                .findFirst();
        if (existingChiTietOptional.isPresent()) {
            GioHangChiTiet existingChiTiet = existingChiTietOptional.get();
            int tongSoLuong = existingChiTiet.getSoLuong() + soLuong;
            if (tongSoLuong > soLuongTrongKho) {
                throw new IllegalArgumentException("Tổng số lượng sản phẩm trong giỏ hàng vượt quá số lượng tồn kho.");
            }
            int soLuongMoi = existingChiTiet.getSoLuong() + soLuong;
            BigDecimal giaBanMoi = chiTietSanPham.getSanPham().getGia().multiply(BigDecimal.valueOf(soLuongMoi));
            existingChiTiet.setChiTietSanPham(chiTietSanPham);
            existingChiTiet.setGioHang(gioHang);
            existingChiTiet.setSoLuong(soLuongMoi);
//            existingChiTiet.setGiaBan(existingChiTiet.getGiaBan());
            existingChiTiet.setDonGia(giaBanMoi);
            existingChiTiet.setNgayCapNhat(LocalDate.now());
            gioHangChiTietRepository.save(existingChiTiet);
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(existingChiTiet);
        } else {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTiet.setChiTietSanPham(chiTietSanPham);
            gioHangChiTiet.setGiaBan(chiTietSanPham.getSanPham().getGia());
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setDonGia(chiTietSanPham.getSanPham().getGia().multiply(BigDecimal.valueOf(soLuong))); // Số lượng mặc định là 1
            gioHangChiTiet.setTrangThai(ApplicationConstant.TrangThaiGioHang.CANCELLED);
            gioHangChiTiet.setNgayTao(LocalDate.now());
            gioHangChiTietRepository.save(gioHangChiTiet);
            return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTiet);
        }
    }
    @Override
    public HoaDonResponse addHoaDon(Integer idDiaChi, Integer ma, String email) {
        KhachHang khachHang = khachHangRepository.findKhachHangByEmail(email);
        DiaChi diaChi = diaChiRepository.findById(idDiaChi).orElse(null);
        if (diaChi == null) {
            throw new NotFoundException("Không tìm thấy địa chỉ");
        }
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setMa(String.valueOf(ma));
        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setDiaChi(diaChi.getTenTinh() + "," + diaChi.getTenQuanHuyen() + "," + diaChi.getTenPhuongXa() + "," + diaChi.getDiaChiChiTiet());
        hoaDon.setHinhThucBanHang(ApplicationConstant.HinhThucBanHang.ONLINE);
        hoaDon.setPhuongThucThanhToan(ApplicationConstant.HinhThucThanhToan.BANKING);
        hoaDon.setSdtNguoiNhan(diaChi.getSdt());
        hoaDon.setTenNguoiNhan(diaChi.getHoTen());
        hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.PENDING_CHECKOUT);
        hoaDonRepository.save(hoaDon);
        return null;
    }
    @Override
    public HoaDonResponse addHoaDonKhongTk(Integer idDiaChi, Integer ma, Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElseThrow(null);
        DiaChi diaChi = diaChiRepository.findById(idDiaChi).orElse(null);
        if (diaChi == null) {
            throw new NotFoundException("Không tìm thấy địa chỉ");
        }
        HoaDon hoaDon = new HoaDon();
        hoaDon.setKhachHang(khachHang);
        hoaDon.setMa(String.valueOf(ma));
        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setDiaChi(diaChi.getTenTinh() + "," + diaChi.getTenQuanHuyen() + "," + diaChi.getTenPhuongXa() + "," + diaChi.getDiaChiChiTiet());
        hoaDon.setHinhThucBanHang(ApplicationConstant.HinhThucBanHang.ONLINE);
        hoaDon.setPhuongThucThanhToan(ApplicationConstant.HinhThucThanhToan.BANKING);
        hoaDon.setSdtNguoiNhan(diaChi.getSdt());
        hoaDon.setTenNguoiNhan(diaChi.getHoTen());
        hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.PENDING_CHECKOUT);
        hoaDonRepository.save(hoaDon);
        return null;
    }


    @Override
    public String createOrder(String orderInfor, String urlReturn, String gia, String mahd) {

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


        vnp_Params.put("vnp_Amount", String.valueOf(Integer.parseInt(gia) * 100));

        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfor);
        vnp_Params.put("vnp_OrderType", orderType);

        HoaDon hoaDon = hoaDonRepository.findHoaDonByMa(mahd);
        hoaDon.setMa(GenCode.generateHoaDonCode());
        hoaDon.setMaGiaoDichChuyenKhoan(vnp_TxnRef);
        hoaDonRepository.save(hoaDon);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        urlReturn += VnpayConfig.vnp_Returnurl;
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
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
        System.out.println("pa" + paymentUrl);
        return paymentUrl;
    }

    @Override
    public GioHangResponse addGioHang() {
        GioHang gioHang = new GioHang();
        gioHang.setTrangThai(ApplicationConstant.TrangThaiGioHang.PENDING);
        gioHangRepository.save(gioHang);
        System.out.println("gìdj" + gioHang);
        return gioHangMapper.gioHangEntityToGioHangResponse(gioHang);
    }

    @Override
    public KhachHangResponse getOneKhachHangKhongTk(Integer id) {
        KhachHang khachHang = khachHangRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy id Khach Hang"));
        return khachHangMapper.khachHangEntityToKhachHangResponse(khachHang);
    }

    @Override
    public int luuHoaDon(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = VnpayConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                String totalPrice = request.getParameter("vnp_Amount");
                String vnp_TxnRef = request.getParameter("vnp_TxnRef");


                HoaDon hoaDon = hoaDonRepository.findHoaDonByMaGiaoDichChuyenKhoan(vnp_TxnRef);
                // tìm khách hàng
                KhachHang khachHang = hoaDon.getKhachHang();
                // tìm giỏ hàng
                GioHang gioHang = khachHang.getGioHangList().get(0);

                System.out.println("Khách Hang" + khachHang.getId());
                List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();
// tru so lg
                LichSuHoaDon lichSuHoaDon=new LichSuHoaDon();
                lichSuHoaDon.setHoaDon(hoaDon);
                lichSuHoaDon.setLoaiLichSuHoaDon(ApplicationConstant.LoaiLichSuHoaDon.CREATED);
                lichSuHoaDon.setNgayTao(LocalDateTime.now());
                lichSuHoaDon.setMoTa("Đơn hàng đã thanh toán");
                lichSuHoaDonRepository.save(lichSuHoaDon);

                for (GioHangChiTiet ghct : gioHang.getGioHangChiTietList()) {
                    if (ghct.getChiTietSanPham().getSoLuong() < ghct.getSoLuong()) {
                        return 0;
                    } else {
                        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                        hoaDonChiTiet.setHoaDon(hoaDon);
                        hoaDonChiTiet.setChiTietSanPham(ghct.getChiTietSanPham());
                        hoaDonChiTiet.setSoLuong(ghct.getSoLuong());
                        hoaDonChiTiet.setTrangThai(ApplicationConstant.TrangThaiHoaDonChiTiet.APPROVED);

                        // Tính và gán giá trị donGia
                        BigDecimal donGia = ghct.getGiaBan().multiply(BigDecimal.valueOf(ghct.getSoLuong()));
                        hoaDonChiTiet.setDonGia(donGia);

                        // Cộng dồn tổng số tiền cho hoa don
                        hoaDonChiTietList.add(hoaDonChiTiet);
                        gioHangChiTietRepository.delete(ghct);
                        ChiTietSanPham chiTietSanPham = ghct.getChiTietSanPham();
                        chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - ghct.getSoLuong());
                        chiTietSanPhamRepository.save(chiTietSanPham);
                    }
                }
// Gán tổng số tiền cho hoaDon
                // Convert 'totalPrice' to BigDecimal
                BigDecimal totalPriceBigDecimal = BigDecimal.valueOf(Long.parseLong(totalPrice));

// Divide 'totalPriceBigDecimal' by 100 with rounding mode HALF_UP (or choose an appropriate rounding mode)
                BigDecimal dividedValue = totalPriceBigDecimal.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

// Set the result to 'thanhTien' in 'hoaDon'
                hoaDon.setThanhTien(dividedValue);

                hoaDon.setSoTienChuyenKhoan(dividedValue);
                hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.PENDING);
                hoaDonChiTietRepository.saveAll(hoaDonChiTietList);


                hoaDonRepository.save(hoaDon);

                if (hoaDon.getKhachHang().getEmail() != null) {
                    this.customEmailSender.sendInvoiceEmail(hoaDon);
                }
                return 1;
            } else {
                String vnp_TxnRef = request.getParameter("vnp_TxnRef");
                HoaDon hoaDon = hoaDonRepository.findHoaDonByMaGiaoDichChuyenKhoan(vnp_TxnRef);
                hoaDonRepository.delete(hoaDon);

                return 0;
            }
        } else {
            return -1;
        }
    }

    @Override
    public GioHangChiTietResponse updateSizeGioHangChiTiet(Integer idGhct, Integer idCtsp, Integer soLuong) {
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(idGhct).orElseThrow(() -> new NotFoundException("Không tìm thấy id Gio hang chi tiet"));
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idCtsp).orElseThrow(() -> new NotFoundException("Không tìm thấy id chi tiet san pham"));
        gioHangChiTiet.setChiTietSanPham(chiTietSanPham);
        gioHangChiTiet.setSoLuong(soLuong);
        gioHangChiTiet.setGiaBan(chiTietSanPham.getSanPham().getGia());
        gioHangChiTiet.setDonGia(chiTietSanPham.getSanPham().getGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSoLuong())));
        return gioHangChiTietMapper.gioHangChiTietEntityToGioHangChiTietResponse(gioHangChiTietRepository.save(gioHangChiTiet));
    }

    @Override
    public List<ChiTietSanPham> loadSizeSanPham(Integer idCtsp) {
        return null;
    }

    @Override
    public KhachHangResponse thongTinDiaChiKhongTk(String tenXa, String hoTen, String tenQuan, String tenTinh, String diaChiChiTiet, String email, String sdt, Integer idGh) {
        KhachHang existingKhachHang = khachHangRepository.findKhachHangByEmailAndTrangThai(email, ApplicationConstant.TrangThaiTaiKhoan.ACTIVE);
        if (existingKhachHang != null) {
            throw new IllegalArgumentException("Email đã tồn tại với trạng thái ACTIVE.");
        }


        GioHang gioHang = gioHangRepository.findById(idGh).orElseThrow(() -> new RuntimeException("GioHang not found with id: " + idGh));
        KhachHang khachHang = new KhachHang();

        System.out.println("idGh" + idGh);
        gioHang.setKhachHang(khachHang);
        khachHang.setEmail(email);
        khachHang.setSdt(sdt);
        khachHang.setNgayTao(LocalDate.now());
        khachHangRepository.save(khachHang);

        DiaChi diaChi = new DiaChi();
        diaChi.setKhachHang(khachHang);
        diaChi.setDiaChiChiTiet(diaChiChiTiet);
        diaChi.setSdt(sdt);
        diaChi.setHoTen(hoTen);
        diaChi.setTenPhuongXa(tenXa);
        diaChi.setTenTinh(tenTinh);
        diaChi.setTenQuanHuyen(tenQuan);
        diaChi.setTrangThai(ApplicationConstant.TrangThaiDiaChi.DEFAULT);

        khachHangRepository.save(khachHang);
        diaChiRepository.save(diaChi);
        System.out.println("khackhangId" + khachHang.getId());

        return khachHangMapper.khachHangEntityToKhachHangResponse(khachHang);
    }

    @Override
    public KhachHangResponse SuathongTinDiaChiKhongTk(String tenXa, String hoTen, String tenQuan, String tenTinh, String diaChiChiTiet, String email, String sdt, Integer idKh) {
        if (idKh == null) {
            throw new IllegalArgumentException("idKh không được phép là null");
        }

        KhachHang khachHang = khachHangRepository.findById(idKh).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy khách hàng với id: " + idKh));

        // Thiết lập thông tin cho khachHang
        khachHang.setEmail(email);
        khachHang.setSdt(sdt);
        khachHang.setNgayTao(LocalDate.now());

        // Lưu khachHang một lần
        khachHangRepository.save(khachHang);

        DiaChi diaChi = khachHang.getDiaChiList().get(0);
        // Tạo và lưu địa chỉ mới
        diaChi.setDiaChiChiTiet(diaChiChiTiet);
        diaChi.setHoTen(hoTen);
        diaChi.setTenPhuongXa(tenXa);
        System.out.println("Tên Xã"+ tenXa);
        diaChi.setTenTinh(tenTinh);
        diaChi.setTenQuanHuyen(tenQuan);

        diaChiRepository.save(diaChi);

        // Trả về response
        return khachHangMapper.khachHangEntityToKhachHangResponse(khachHang);
    }

    @Override
    public HoaDonResponse huyDonHangOnline(Integer id) {
        HoaDon hoaDon=hoaDonRepository.findById(id).orElseThrow(null);
        hoaDon.setTrangThai(ApplicationConstant.TrangThaiHoaDon.PENDING_CANCELLED);
        hoaDonRepository.save(hoaDon);
        return null;
    }

    @Override
    public KhachHang findKhachHangtest( ApplicationConstant.TrangThaiTaiKhoan trangThaiTaiKhoan,String email) {
        KhachHang khachHang=khachHangRepository.findKhachHangByEmailAndTrangThai(email, ApplicationConstant.TrangThaiTaiKhoan.ACTIVE);
        return khachHang;
    }


}