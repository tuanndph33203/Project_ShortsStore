/**
 * Đây là một lớp được sử dụng để gửi email trong ứng dụng. Lớp này chứa các phương thức để gửi email cho khách hàng và nhân viên.
 * Các phương thức trong lớp này sử dụng JavaMailSender để gửi email thông qua giao thức SMTP.
 * Lớp này được sử dụng để gửi email cho việc thăng hạng, đăng ký tài khoản và thông báo về việc đặt lại thứ hạng.
 */
package com.example.website_ban_ao_the_thao_ps.common;

import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDonChiTiet;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class CustomEmailSender {

    private final JavaMailSender javaMailSender;

    @Autowired
    public CustomEmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng khi họ được thăng hạng.
     * Email chứa các thông tin về thứ hạng mới và các ưu đãi đặc biệt cho khách hàng.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void updateThuHangKhachHangSendEmail(KhachHang khachHang) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            mimeMessageHelper.setTo(khachHang.getEmail());
            mimeMessageHelper.setSubject("Chúc mừng! Bạn đã thăng hạng trong Hội viên Ưu đãi PSG Fashion");

            String htmlContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    "   body { font-family: 'Arial', sans-serif; }" +
                    "   .container { max-width: 600px; margin: 0 auto; }" +
                    "   .header { background-color: #007BFF; color: #FFF; text-align: center; padding: 10px; }" +
                    "   .content { padding: 20px; }" +
                    "   .congratulations { color: #28a745; font-weight: bold; }" +
                    "   /* Add your custom styles here */" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "   <div class='header'>" +
                    "       <h1>Chúc mừng! Bạn đã thăng hạng trong Hội viên Ưu đãi PSG Fashion</h1>" +
                    "   </div>" +
                    "   <div class='content'>" +
                    "       <p class='congratulations'>Chào bạn <b>" + khachHang.getTen() + "</b> thân mến,</p>" +
                    "       <p>" +
                    "           Chúc mừng bạn đã chính thức thăng hạng lên <b>" + khachHang.getThuHang().getTen() + "</b> trong Hội viên Ưu đãi PSG Fashion! " +
                    "           Đây là một thành quả đáng tự hào mà bạn đã đạt được và chúng tôi xin gửi lời chúc mừng chân thành nhất đến bạn." +
                    "       </p>" +
                    "       <p>" +
                    "           Chúng tôi biết rằng bạn đã luôn ủng hộ chúng tôi trong suốt thời gian qua và chúng tôi xin cảm ơn bạn vô cùng. " +
                    "           Thăng hạng của bạn không chỉ là một sự công nhận về sự hỗ trợ của bạn, mà còn là sự thể hiện về sự kiên nhẫn và " +
                    "           sự cam kết của bạn đối với thương hiệu PSG Fashion." +
                    "       </p>" +
                    "       <p>" +
                    "           Với thăng hạng này, bạn sẽ được hưởng nhiều ưu đãi độc quyền hơn, bao gồm:" +
                    "       </p>" +
                    "       <ul>" +
                    "           <li>Giảm giá đặc biệt cho các sản phẩm mới nhất của PSG Fashion.</li>" +
                    "           <li>Quyền truy cập trước vào các sự kiện và chương trình khuyến mãi sắp tới.</li>" +
                    "           <li>Đặc quyền tham gia vào các buổi triển lãm thời trang và hậu trường.</li>" +
                    "           <li>Quà tặng độc đáo và các ưu đãi đặc biệt khác dành riêng cho Hội viên Ưu đãi.</li>" +
                    "       </ul>" +
                    "       <p>" +
                    "           Chúng tôi rất mong muốn tiếp tục chia sẻ những trải nghiệm thú vị và mới mẻ với bạn trong tương lai. " +
                    "           Hãy tiếp tục ủng hộ PSG Fashion và chúng tôi cam kết sẽ không ngừng phấn đấu để mang đến cho bạn những sản phẩm " +
                    "           thời trang tốt nhất và những trải nghiệm mua sắm đáng nhớ." +
                    "       </p>" +
                    "       <p>" +
                    "           Nếu bạn có bất kỳ câu hỏi hoặc góp ý nào, xin vui lòng liên hệ với chúng tôi qua số điện thoại 0968661552 " +
                    "           hoặc email tunganh.devj@gmail.com. Chúng tôi luôn sẵn sàng hỗ trợ bạn." +
                    "       </p>" +
                    "       <p class='congratulations'>" +
                    "           Một lần nữa, chúc mừng bạn với sự thăng hạng đầy ý nghĩa này. Cảm ơn bạn vì đã là một phần quan trọng của cộng đồng PSG Fashion." +
                    "       </p>" +
                    "       <p>" +
                    "           Trân trọng," +
                    "           <br/>" +
                    "           Nguyễn Trọng Tùng Anh" +
                    "           <br/>" +
                    "           Giám đốc" +
                    "           <br/>" +
                    "           PSG Fashion" +
                    "       </p>" +
                    "   </div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error while sending mail!!!");
        }
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng khi họ đăng ký tài khoản thành công.
     * Email chứa thông tin đăng nhập của khách hàng.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void signupKhachHangSendEmail(KhachHang khachHang, String plainTextPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Đăng ký tài khoản thành công");
        message.setText("Chào anh/chị,\n" +
                "Dưới đây là thông tin tài khoản của bạn:\n" +
                "Tên đăng nhập (Email): " + khachHang.getEmail() + "\n" +
                "Mật khẩu: " + plainTextPassword + "\n" +
                "Vui lòng đăng nhập bằng thông tin này để sử dụng tài khoản của bạn.\n" +
                "\n" +
                "Trân trọng,\n" +
                "Cửa hàng bán áo thể thao PSG");
        javaMailSender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho nhân viên khi họ đăng ký tài khoản thành công.
     * Email chứa thông tin đăng nhập của nhân viên.
     *
     * @param nhanVien Đối tượng NhanVien chứa thông tin về nhân viên.
     */

    public void sendForgotPasswordEmail(KhachHang khachHang, String newPlainTextPassword){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Thay đổi mật khẩu của bạn");
        message.setText("Chào anh/chị, " +
                "Dưới đậy là mật khẩu mới của bạn" +
                "Mật khẩu: " + newPlainTextPassword + "\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập lại tài khoản của bạn" + "\n"+
                "Trân trọng" + "\n" +
                "Của hàng bán áo đấu thể thao PSG");
        javaMailSender.send(message);
    }

    public void sendForgotPasswordEmailForNhanVien(NhanVien nhanVien, String newPlainTextPassword){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Thay đổi mật khẩu của bạn");
        message.setText("Chào anh/chị, " +
                "Dưới đậy là mật khẩu mới của bạn" +
                "Mật khẩu: " + newPlainTextPassword + "\n" +
                "Vui lòng dùng mật khẩu này để đăng nhập lại tài khoản của bạn" + "\n"+
                "Trân trọng" + "\n" +
                "Của hàng bán áo đấu thể thao PSG");
        javaMailSender.send(message);
    }
    public void signupNhanVienSendEmail(NhanVien nhanVien, String genPassWord){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(nhanVien.getEmail());
        message.setSubject("Đăng ký tài khoản thành công");
        message.setText("Xin chào" +nhanVien.getTen()+",\n" +
                "\n" +
                "Chúng tôi rất vui mừng chào đón bạn gia nhập đội ngũ của cửa hàng bán áo PSG! Dưới đây là thông tin đăng nhập cần thiết để bạn có thể truy cập vào hệ thống của chúng tôi:\n" +
                "\n" +
                "Tài khoản (email): "+nhanVien.getEmail()+"\n" +
                "Mật khẩu: "+genPassWord+"\n" +
                "Chức vụ: "+nhanVien.getVaiTro().getTen()+"\n" +
                "\n" +
                "Vui lòng lưu ý rằng đây là mật khẩu tạm thời, và chúng tôi khuyến nghị bạn nên thay đổi mật khẩu ngay sau khi đăng nhập lần đầu. Hãy đảm bảo rằng bạn giữ thông tin đăng nhập này một cách cẩn thận để tránh rủi ro bất kỳ việc lạm dụng tài khoản nào.\n" +
                "\n" +
                "Nếu bạn gặp bất kỳ vấn đề gì trong quá trình đăng nhập hoặc sử dụng hệ thống, vui lòng liên hệ với bộ phận hỗ trợ của chúng tôi qua địa chỉ [địa chỉ email hỗ trợ] hoặc số điện thoại [số điện thoại hỗ trợ]. Chúng tôi luôn sẵn sàng hỗ trợ bạn.\n" +
                "\n" +
                "Chúc bạn một ngày làm việc hiệu quả và thú vị tại cửa hàng bán áo PSG! Chúng tôi hy vọng bạn sẽ đóng góp mạnh mẽ vào sự phát triển của đội ngũ và sự thành công của cửa hàng.\n" +
                "\n" +
                "Trân trọng,\n" +
                "Nguyễn Trọng Tùng Anh\n" +
                "Giám đốc Cửa hàng bán áo PSG");
        javaMailSender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng khi thứ hạng của họ được đặt lại về mặc định.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void resetRankAlert(KhachHang khachHang){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Thông báo đặt lại thứ hạng");
        message.setText("Xin chào " + khachHang.getTen() + ",\n\nĐã đến ngày reset lại thứ hạng của bạn về mặc định. " +
                "Cảm ơn vì bạn đã đông hành cùng chúng tôi.\n\nTrân trọng,\nWebsite bán áo thể thao PSG");
        javaMailSender.send(message);
    }

    /**
     * Phương thức này được sử dụng để gửi email thông báo cho khách hàng trước khi thứ hạng của họ được đặt lại về mặc định.
     *
     * @param khachHang Đối tượng KhachHang chứa thông tin về khách hàng.
     */
    public void willResetRank(KhachHang khachHang){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(khachHang.getEmail());
        message.setSubject("Thông báo đặt lại thứ hạng");
        message.setText("Xin chào " + khachHang.getTen() + ",\n\nĐến trước 1 tháng nữa, chúng ta sẽ đặt lại thứ hạng." +
                " Vui lòng chuẩn bị cho điều này.\n" +
                "\nTrân trọng," +
                "\nWebsite bán áo thể thao PSG");
        javaMailSender.send(message);
    }

    public void sendEmailWithAttachment(HoaDon hoaDon, Integer id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(hoaDon.getKhachHang().getEmail());
        message.setSubject("Hóa đơn " + hoaDon.getMa() + " tại cửa hàng bán áo đấu bóng đá PSG");
    }

    public void sendInvoiceEmail(HoaDon hoaDon) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(hoaDon.getKhachHang().getEmail());
            helper.setSubject("Đơn hàng - #" + hoaDon.getMa() + " tại website bán áo đấu thể thao PSG");
            helper.setText(generateInvoiceEmail(hoaDon), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle the exception (log or throw it)
            e.printStackTrace();
        }
    }

    private String generateInvoiceEmail(HoaDon hoaDon) {
        String invoiceLink = "http://localhost:8080/api/hoa-don/export/pdf?id=" + hoaDon.getId();
        String emailContent = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: 'Arial', sans-serif; }" +
                "h2 { color: #333; }" +
                "table { width: 100%; border-collapse: collapse; margin-top: 10px; }" +
                "th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }" +
                "th { background-color: #f2f2f2; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h2>Chi Tiết Hóa Đơn</h2>" +
                "<p><strong>Số Hóa Đơn:</strong> " + hoaDon.getMa() + "</p>" +
                "<p><strong>Ngày Lập Hóa Đơn:</strong> " + hoaDon.getNgayTao() + "</p>" +
                "<p><strong>Khách Hàng:</strong> " + hoaDon.getKhachHang().getTen() + "</p>" +
                "<p><strong>Địa Chỉ Nhận Hàng:</strong> " + hoaDon.getDiaChi() + "</p>" +
                "<p><strong>Điện Thoại:</strong> " + hoaDon.getSdtNguoiNhan() + "</p>" + "</br>" +
                "<h3>Danh Sách Sản Phẩm Đã Mua</h3>" +
                "<table>" +
                "<tr>" +
                "<th>Tên Sản Phẩm</th>" +
                "<th>Số Lượng</th>" +
                "<th>Đơn Giá</th>" +
                "<th>Thành Tiền</th>" +
                "</tr>";
        BigDecimal total = BigDecimal.ZERO; // Biến tạm để tích lũy tổng thành tiền
        for (HoaDonChiTiet chiTiet : hoaDon.getHoaDonChiTietList()) {
            BigDecimal thanhTien = chiTiet.getDonGia().multiply(BigDecimal.valueOf(chiTiet.getSoLuong()));
            total = total.add(thanhTien); // Thêm thành tiền vào tổng
            emailContent += String.format(
                    "<tr>" +
                            "<td>" + chiTiet.getChiTietSanPham().getSanPham().getTen() + "</td>" +
                            "<td>" + chiTiet.getSoLuong() + "</td>" +
                            "<td>" + chiTiet.getDonGia()  + "</td>" +
                            "<td>" + thanhTien + "</td>" +
                            "</tr>"
            );
        }
        String formattedCurrency = String.format("%.2f", total);
        if (hoaDon.getKhachHang().getEmail() != null) {
            emailContent += "</table>" +
                    "<p><strong>Tổng Cộng:</strong> " + formattedCurrency + " VND</p>" +
                    "<p>Cảm ơn bạn đã mua sắm tại cửa hàng chúng tôi!</p>" +
                    "<p>Để tải hóa đơn PDF, vui lòng nhấn vào đường link sau: <a href='" + invoiceLink + "'>Tải Hóa Đơn PDF</a></p>" +
                    "<p>Thông tin liên hệ:</p>" +
                    "<p>Địa Chỉ: Tầng 4, tòa nhà C+ Office, Thành Thái, Dịch Vọng, Cầu Giấy, Hà Nội</p>" +
                    "<p>Số Điện Thoại: 0968661552</p>" +
                    "<p>Email: psgshop.help@gmail.com</p>" +
                    "</body>" +
                    "</html>";
            return emailContent;
        }else {
            emailContent += "</table>" +
                    "<p><strong>Tổng Cộng:</strong> " + formattedCurrency + " VND</p>" +
                    "<p>Cảm ơn bạn đã mua sắm tại cửa hàng chúng tôi!</p>" +
                    "<p>Thông tin liên hệ:</p>" +
                    "<p>Địa Chỉ: Tầng 4, tòa nhà C+ Office, Thành Thái, Dịch Vọng, Cầu Giấy, Hà Nội</p>" +
                    "<p>Số Điện Thoại: 0968661552</p>" +
                    "<p>Email: psgshop.help@gmail.com</p>" +
                    "</body>" +
                    "</html>";
            return emailContent;
        }
    }
}
