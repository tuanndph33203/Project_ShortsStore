
//.....
//        ..                    ...=++=..
//        .:++=:..                ...=*=::++..
//        .:+=:=+=...             ..:*=:-=--+=..
//        ..=+--:-++:.....:::--------*-:=++-:=*..
//        ..+=-==--=*=+***+==-------=-----==:-*-.
//        .-+=-==-----------------------------=*:..
//        .=+=----------------------------------+*:.
//        .:+=------:...:----....-----------------=*-.
//        ..=+=-------:...:----...:=#+++**=----------=+:.
//        .:++---++===++-:---------+-+#%%%*++----------=+:.
//        ..-+=---+=+%@@%**=--------=+=@@@@@%*#=----------++:.
//        ..:++----++*%@@@@#*+--------*=+@@@@@%+#------------==..
//        .:++-----++=#@@@%*#=---------*:=*###**=------------=+-.
//        ..:++-------++-+*+=*=-=#%%*..::-+#****---::::---------++..
//        ..-+-:......::-----:...........-:...............:-----=+-.
//        .-*=:.............=-...:#*:...:=+.................:----==..
//        .=*:..............:+%%+:..........................:----=+:.
//        .+*...............................................:----=+=.
//        .=+................... .... ......................:-----*+.
//        .=*:..............................................------++.
//        .:+-.............   .-++===+:...................:-------++:
//        ..:+-............  .-=:.#:.-*......  ..........:--------=+:
//        ..=+-..........  .+-..*:..#:...... .........:---------=+:.
//        ..-=--....... .:*-..*-..*-..............:-----------=+:.
//        ...-*-......-*=-:.*-:--*=:..........:-------------=+:.
//        .-+=-...:*+---:.*-.----=##*-.::-----------------=+:
//        .=+----------:.+=*-:----------------------------++:
//        .++-----------*-..-*::--------------------------++.
//        :+=--------+*=......:*-.------------------------*+.
//        .:==----+*+-:.....  ...:=**###*=----------------=+-.
//        .:==--:-:........    ...........:---------------=+:.
//        .-=--:..........    .............:--------------==..
//        .-+--:..........    ..............:-------------==.
//        .:=-:..............................------------=+-.
//        .............      ..............................


        package com.example.website_ban_ao_the_thao_ps.scheduled;

import com.example.website_ban_ao_the_thao_ps.common.CustomEmailSender;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.QuyDinh;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.model.mapper.KhachHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.QuyDinhMapper;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDinhResponse;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.repository.QuyDinhRepository;
import com.example.website_ban_ao_the_thao_ps.repository.ThuHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class QuyDinhScheduled {

    @Autowired
    private QuyDinhRepository quyDinhRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private ThuHangRepository thuHangRepository;
    @Autowired
    private CustomEmailSender customEmailSender;

    /**
     * ************************************** TÙNG ANH ĐÃ Ở ĐÂY
     * **************************************
     * Phương thức này được sử dụng để thông báo reset dữ liệu khách hàng.
     * Nếu ngày hiện tại trùng khớp với ngày đặt lại thứ hạng,
     * thì dữ liệu khách hàng sẽ được reset và gửi email thông báo đến khách hàng.
     * Nếu ngày hiện tại trùng khớp với ngày thông báo trước reset,
     * thì sẽ gửi email thông báo đến khách hàng.
     */

    @Scheduled(fixedRate = 10)
    public void thongBaoReset() {
        LocalDate currentDateTime = LocalDate.now();
        List<QuyDinh> listQuyDinh = quyDinhRepository.findByNgayCapNhatByTrangThai();

        for (QuyDinh quyDinh : listQuyDinh) {
            LocalDate ngayDatLaiThuHang = quyDinh.getNgayDatLaiThuHang();
            LocalDate ngayThongBao = ngayDatLaiThuHang.minusDays(30);

            if (ngayDatLaiThuHang.isEqual(currentDateTime)) {
                resetKhachHangData(ngayDatLaiThuHang);
            } else if (ngayThongBao.isEqual(currentDateTime)) {
                notifyKhachHangForReset();
            }
        }
    }

    /**
     * Phương thức này được sử dụng để reset dữ liệu khách hàng.
     * Dữ liệu số lượng đơn hàng thành công và số tiền đã chi tiêu về không sẽ được reset.
     * Trạng thái quy định sẽ được cập nhật sau khi reset thứ hạng.
     * Sau đó, email thông báo sẽ được gửi đến từng khách hàng.
     *
     * @param ngayDatLaiThuHang Ngày đặt lại thứ hạng
     */
    private void resetKhachHangData(LocalDate ngayDatLaiThuHang) {
        List<KhachHang> khachHangList = khachHangRepository.findAll();
        quyDinhRepository.updateTrangThaiSauResetThuHang(ngayDatLaiThuHang);

        ThuHang thuHangDefault = thuHangRepository.findBySoLuongDonHangToiThieuAndSoTienKhachChiToiThieu(0, BigDecimal.ZERO);

        khachHangList.forEach(khachHang -> {
            khachHang.setThuHang(thuHangDefault);
            khachHang.setSoLuongDonHangThanhCong(0);
            khachHang.setSoTienDaChiTieu(BigDecimal.ZERO);
            this.khachHangRepository.save(khachHang);
            customEmailSender.resetRankAlert(khachHang);
        });
    }

    /**
     * Phương thức này được sử dụng để thông báo đến khách hàng về việc sắp reset thứ hạng.
     * Email thông báo sẽ được gửi đến từng khách hàng.
     */
    private void notifyKhachHangForReset() {
        List<KhachHang> khachHangList = khachHangRepository.findAll();

        for (KhachHang khachHang : khachHangList) {
            customEmailSender.willResetRank(khachHang);
        }
    }
}