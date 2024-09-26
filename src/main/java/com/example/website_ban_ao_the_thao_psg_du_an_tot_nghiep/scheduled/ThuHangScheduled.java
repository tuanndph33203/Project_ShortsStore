/*
 * .----------------.  .----------------.  .-----------------. .----------------.    .----------------.  .-----------------. .----------------.
 * | .--------------. || .--------------. || .--------------. || .--------------. |  | .--------------. || .--------------. || .--------------. |
 * | |  _________   | || | _____  _____ | || | ____  _____  | || |    ______    | |  | |      __      | || | ____  _____  | || |  ____  ____  | |
 * | | |  _   _  |  | || ||_   _||_   _|| || ||_   \|_   _| | || |  .' ___  |   | |  | |     /  \     | || ||_   \|_   _| | || | |_   ||   _| | |
 * | | |_/ | | \_|  | || |  | |    | |  | || |  |   \ | |   | || | / .'   \_|   | |  | |    / /\ \    | || |  |   \ | |   | || |   | |__| |   | |
 * | |     | |      | || |  | '    ' |  | || |  | |\ \| |   | || | | |    ____  | |  | |   / ____ \   | || |  | |\ \| |   | || |   |  __  |   | |
 * | |    _| |_     | || |   \ `--' /   | || | _| |_\   |_  | || | \ `.___]  _| | |  | | _/ /    \ \_ | || | _| |_\   |_  | || |  _| |  | |_  | |
 * | |   |_____|    | || |    `.__.'    | || ||_____|\____| | || |  `._____.'   | |  | ||____|  |____|| || ||_____|\____| | || | |____||____| | |
 * | |              | || |              | || |              | || |              | |  | |              | || |              | || |              | |
 * | '--------------' || '--------------' || '--------------' || '--------------' |  | '--------------' || '--------------' || '--------------' |
 * '----------------'  '----------------'  '----------------'  '----------------'    '----------------'  '----------------'  '----------------'
 */
package com.example.website_ban_ao_the_thao_ps.scheduled;

import com.example.website_ban_ao_the_thao_ps.common.CustomEmailSender;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.repository.ThuHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
/**
 * ************************************** TÙNG ANH ĐÃ Ở ĐÂY
 * **************************************
 * Lớp Scheduled này được sử dụng để cập nhật thuộc tính "ThuHang" của khách hàng dựa trên số lượng đơn hàng thành công và số tiền đã chi tiêu của khách hàng. Lớp này được chạy theo một lịch trình cố định.
 *
 * Trong phương thức "updateThuHang", danh sách khách hàng và danh sách "ThuHang" đang hoạt động được lấy từ cơ sở dữ liệu. Với mỗi khách hàng, thuộc tính "SoTienDaChiTieu" và "SoLuongDonHangThanhCong" của khách hàng được kiểm tra để tìm ra "ThuHang" phù hợp nhất.
 *
 * Nếu tìm thấy "ThuHang" phù hợp, khách hàng sẽ được cập nhật với "ThuHang" này. Nếu khách hàng chưa có "ThuHang" được chọn, "ThuHang" mặc định sẽ được gán cho khách hàng. Sau đó, một email thông báo về việc cập nhật "ThuHang" sẽ được gửi đến khách hàng.
 */
@EnableScheduling
@Component
public class ThuHangScheduled {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private ThuHangRepository thuHangRepository;

    @Autowired
    private CustomEmailSender customEmailSender;

    @Scheduled(fixedRate = 10000)
    public void updateThuHang() {
        List<KhachHang> khachHangs = khachHangRepository.findAll();
        List<ThuHang> activeThuHangList = thuHangRepository.getThuHangsByTrangThaiActive();
        khachHangs.forEach(khachHang -> {
            BigDecimal soTienDaChiTieu = khachHang.getSoTienDaChiTieu();
            Integer soLuongDonHangThanhCong = khachHang.getSoLuongDonHangThanhCong();

            // Kiểm tra null cho soLuongDonHangThanhCong
            if (soLuongDonHangThanhCong != null) {
                ThuHang selectedThuHang = activeThuHangList.stream()
                        .filter(thuHang -> thuHang.getSoLuongDonHangToiThieu() <= soLuongDonHangThanhCong &&
                                soTienDaChiTieu.compareTo(thuHang.getSoTienKhachChiToiThieu()) >= 0)
                        .max(Comparator.comparing(ThuHang::getSoLuongDonHangToiThieu))
                        .orElse(null);

                ThuHang thuHangDefault = thuHangRepository.findBySoLuongDonHangToiThieuAndSoTienKhachChiToiThieu(0, BigDecimal.ZERO);

                if (khachHang.getThuHang() == null) {
                    khachHang.setThuHang(thuHangDefault);
                    khachHangRepository.save(khachHang);
                }

                if (selectedThuHang != null && khachHang.getThuHang().getId() != selectedThuHang.getId()) {
                    khachHang.setThuHang(selectedThuHang);
                    khachHang.setThuHang(selectedThuHang);
                    khachHangRepository.save(khachHang);
                    customEmailSender.updateThuHangKhachHangSendEmail(khachHang);
                    System.out.println("This is Thu Hang Send Email");
                }
            }
        });
    }

//    public void updateThuHang() {
//        List<KhachHang> khachHangs = khachHangRepository.findAll();
//        List<ThuHang> activeThuHangList = thuHangRepository.getThuHangsByTrangThaiActive();
//        khachHangs.forEach(khachHang -> {
//            BigDecimal soTienDaChiTieu = khachHang.getSoTienDaChiTieu();
//            Integer soLuongDonHangThanhCong = khachHang.getSoLuongDonHangThanhCong();
//
//            ThuHang selectedThuHang = activeThuHangList.stream()
//                    .filter(thuHang -> thuHang.getSoLuongDonHangToiThieu() <= soLuongDonHangThanhCong &&
//                            soTienDaChiTieu.compareTo(thuHang.getSoTienKhachChiToiThieu()) >= 0)
//                    .max(Comparator.comparing(ThuHang::getSoLuongDonHangToiThieu))
//                    .orElse(null);
//
//            ThuHang thuHangDefault = thuHangRepository.findBySoLuongDonHangToiThieuAndSoTienKhachChiToiThieu(0, BigDecimal.ZERO);
//
//            if (khachHang.getThuHang() == null) {
//                khachHang.setThuHang(thuHangDefault);
//                khachHangRepository.save(khachHang);
//            }
//
//            if (selectedThuHang != null && khachHang.getThuHang().getId() != selectedThuHang.getId()) {
//                khachHang.setThuHang(selectedThuHang);
//                khachHang.setThuHang(selectedThuHang);
//                khachHangRepository.save(khachHang);
//                customEmailSender.updateThuHangkhachHangSendEmail(khachHang);
//                System.out.println("This is Thu Hang Send Email");
//            }
//        });
//    }

}
