package com.example.website_ban_ao_the_thao_ps.config;

import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDonChiTiet;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.HinhThucBanHang.ONLINE;

@Component
public class PDFExporterConfig {

    public String formatLocalDateTime(LocalDate localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTime.format(formatter);
    }

    public String formatLocalDateTimeNgay(LocalDate localDateTime) {
        int ngay = localDateTime.getDayOfMonth();
        int thang = localDateTime.getMonthValue();
        int nam = localDateTime.getYear();

        String ngayThangNam = String.format("Ngay %d Thang %d Nam %d", ngay, thang, nam);

        return ngayThangNam;
    }

    public String formatNumberVietNam(BigDecimal number) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return currencyFormat.format(number);
    }

    private String getHoaDonTenKhachHang(HoaDon hoaDon) {
        String ten = hoaDon.getTenNguoiNhan();
        return ten;
    }

    private String getHoaDonSdtKhachHang(HoaDon hoaDon) {
        String ten = hoaDon.getSdtNguoiNhan();
        return ten;
    }

    private String getHoaDonDiaChiKhachHang(HoaDon hoaDon) {
        String ten = hoaDon.getDiaChi();
        return ten;
    }

    private String getTenLoaiHoaDon(HoaDon hoaDon) {
        String ten = "Tai Quay";
        if (hoaDon.getHinhThucBanHang() == ONLINE) {
            ten = "Giao hang";
        }
        return ten;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(Color.BLUE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8);

        Font font = FontFactory.getFont("Times New Roman", "UTF-8", true, 11, Font.BOLD);

        cell.setPhrase(new Phrase("STT", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ten san pham", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("So luong", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Don gia", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Thanh tien", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, HoaDon hoaDon) {

        int stt = 1;
        Font font = FontFactory.getFont("Times New Roman", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        BigDecimal tongThanhTien = BigDecimal.ZERO;
        BigDecimal tienShip = hoaDon.getSoTienVanChuyen();

        BigDecimal giamGiaVoucher = BigDecimal.ZERO;
        if (hoaDon.getSoDiemTru() != null) {
            giamGiaVoucher = hoaDon.getThanhTien();
        }

        for (HoaDonChiTiet hdct : hoaDon.getHoaDonChiTietList()) {
//            stt
            PdfPCell sttCell = new PdfPCell(new Phrase(String.valueOf(stt)));
            sttCell.setPadding(10f);
            sttCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(sttCell);

//             sản phẩm
            String tenSanPham = hdct.getChiTietSanPham().getSanPham().getTen();
            Paragraph sanPham = new Paragraph(tenSanPham, font);
            PdfPCell sanPhamCell = new PdfPCell();
            sanPhamCell.setPadding(10f);
            sanPhamCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            sanPhamCell.addElement(sanPham);
            table.addCell(sanPhamCell);

//            số lượng
            PdfPCell soLuongCell = new PdfPCell(new Phrase(String.valueOf(hdct.getSoLuong())));
            soLuongCell.setPadding(10f);
            soLuongCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(soLuongCell);

//            đơn giá
            PdfPCell donGiaCell = new PdfPCell(new Phrase(currencyFormat.format(hdct.getDonGia())));
            donGiaCell.setPadding(10f);
            donGiaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(donGiaCell);

//            thành tiền
            BigDecimal thanhTien = BigDecimal.valueOf(hdct.getSoLuong())
                    .multiply(hdct.getDonGia());
            PdfPCell thanhTienCell = new PdfPCell(new Phrase(currencyFormat.format(thanhTien)));
            thanhTienCell.setPadding(10f);
            thanhTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(thanhTienCell);

            tongThanhTien = tongThanhTien.add(thanhTien);
            stt++;
        }

//        tổng thành tiền
        PdfPCell tongTienCell = new PdfPCell(new Phrase("Tong tien"));
        tongTienCell.setColspan(4);
        tongTienCell.setPadding(10f);
        tongTienCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell tongTienValueCell = new PdfPCell(new Phrase(currencyFormat.format(tongThanhTien), font));
        tongTienValueCell.setPadding(10f);
        tongTienValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tongTienCell);
        table.addCell(tongTienValueCell);

//        Tiền giảm giá
        if (hoaDon.getSoDiemTru() != null || hoaDon.getSoDiemTru() == 0) {
            PdfPCell giamGiaCell = new PdfPCell(new Phrase("So diem tru"));
            giamGiaCell.setColspan(4);
            giamGiaCell.setPadding(10f);
            giamGiaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell giamGiaValueCell = new PdfPCell(new Phrase(currencyFormat.format(hoaDon.getSoDiemTru()), font));
            giamGiaValueCell.setPadding(10f);
            giamGiaValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(giamGiaCell);
            table.addCell(giamGiaValueCell);
        }

//        tiền ship
        PdfPCell tienShipCell = new PdfPCell(new Phrase("Tien ship"));
        tienShipCell.setColspan(4);
        tienShipCell.setPadding(10f);
        tienShipCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell tienShipValueCell = new PdfPCell(new Phrase(currencyFormat.format(tienShip), font));
        tienShipValueCell.setPadding(10f);
        tienShipValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tienShipCell);
        table.addCell(tienShipValueCell);

//      Tổng thanh toán
        PdfPCell tongThanhToanCell = new PdfPCell(new Phrase("Tong thanh toan"));
        tongThanhToanCell.setColspan(4);
        tongThanhToanCell.setPadding(10f);
        tongThanhToanCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell tongThanhToanValueCell = new PdfPCell(new Phrase(currencyFormat.format(hoaDon.getThanhTien())));
        tongThanhToanValueCell.setPadding(10f);
        tongThanhToanValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(tongThanhToanCell);
        table.addCell(tongThanhToanValueCell);
    }

    public void export(HttpServletResponse response, HoaDon hoaDon) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        String imagePath = "src/main/java/com/example/website_ban_ao_the_thao_ps/config/images/logo.png";
        Image image = Image.getInstance(imagePath);
        image.scaleAbsolute(200, 50);

        Font fontTitle = FontFactory.getFont("Times New Roman", "UTF-8", true, 18, 1);

        Font fontContent = FontFactory.getFont("Times New Roman", "UTF-8", true, 14, 1);

        Font fontInfo = FontFactory.getFont("Times New Roman", "UTF-8", true, 10);

        Font fontInfoKhach = FontFactory.getFont("Times New Roman", "UTF-8", true, 10);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 5.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table, hoaDon);

//        logo - thông tin liên hệ
        PdfPTable headerTitleTable = new PdfPTable(2);
        headerTitleTable.setWidthPercentage(100);
        headerTitleTable.setWidths(new float[]{1.3f, 2.3f});

        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        headerTitleTable.addCell(imageCell);

        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        Paragraph diaChi = new Paragraph("Dia chi: Trinh Van Bo - Nam Tu Liem - Ha Noi", fontInfo);
        Paragraph sdt = new Paragraph("So dien thoai : 0968661552", fontInfo);
        Paragraph email = new Paragraph("Email : psgshop.help@gmail.com", fontInfo);

        diaChi.setAlignment(Element.ALIGN_RIGHT);
        sdt.setAlignment(Element.ALIGN_RIGHT);
        email.setAlignment(Element.ALIGN_RIGHT);

        infoCell.addElement(diaChi);
        infoCell.addElement(sdt);
        infoCell.addElement(email);

        headerTitleTable.addCell(infoCell);
//
//        Thông tin người nhận
        PdfPTable shipInfoTable = new PdfPTable(1);
        shipInfoTable.setWidthPercentage(100);
        shipInfoTable.setWidths(new float[]{1f});
        shipInfoTable.setSpacingBefore(10f);

        Paragraph columnName = new Paragraph("Thong tin khach hang", fontInfoKhach);
        PdfPCell columnNameCell = new PdfPCell();
        columnNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        columnNameCell.setBorder(Rectangle.NO_BORDER);
        columnNameCell.addElement(columnName);
        columnNameCell.setPadding(10);

        Paragraph khachHang = new Paragraph("Khach hang: " + getHoaDonTenKhachHang(hoaDon), fontInfoKhach);
        Paragraph sdtKhachHang = new Paragraph("Dien thoai:    " + getHoaDonSdtKhachHang(hoaDon), fontInfoKhach);
        Paragraph diaChiKhachHang = new Paragraph("Dia chi:         " + getHoaDonDiaChiKhachHang(hoaDon), fontInfoKhach);

        PdfPCell shipInfoCell = new PdfPCell();
//        shipInfoCell.setBorder(Rectangle.NO_BORDER);
        shipInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        shipInfoCell.addElement(khachHang);
        shipInfoCell.addElement(sdtKhachHang);
        shipInfoCell.addElement(diaChiKhachHang);
        shipInfoCell.setPadding(10);

        shipInfoTable.addCell(columnNameCell);
        shipInfoTable.addCell(shipInfoCell);
//
        //        Thông tin hóa đơn
        PdfPTable billInfoTable = new PdfPTable(1);
        billInfoTable.setWidthPercentage(100);
        billInfoTable.setWidths(new float[]{1f});
        billInfoTable.setSpacingBefore(10f);
        billInfoTable.getDefaultCell().setBorder(Rectangle.NO_BORDER); // Set table border to NO_BORDER

        Paragraph maHoaDon = new Paragraph("Ma hoa don: " + hoaDon.getMa(), fontInfoKhach);
        Paragraph ngayTao = new Paragraph("Ngay tao: " + formatLocalDateTime(hoaDon.getNgayTao()), fontInfoKhach);
        Paragraph loaiHoaDon = new Paragraph("Loai hoa don: " + getTenLoaiHoaDon(hoaDon), fontInfoKhach);

        PdfPCell infoHoaDonCell = new PdfPCell();
        infoHoaDonCell.setBorder(Rectangle.NO_BORDER);
        infoHoaDonCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        infoHoaDonCell.addElement(maHoaDon);
        infoHoaDonCell.addElement(ngayTao);
        infoHoaDonCell.addElement(loaiHoaDon);
        infoHoaDonCell.setPadding(10);

        billInfoTable.addCell(infoHoaDonCell);
//


        document.add(headerTitleTable);

        Paragraph title = new Paragraph("HOA DON BAN HANG", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        Paragraph ngayThang = new Paragraph(formatLocalDateTimeNgay(hoaDon.getNgayTao()), fontContent);
        ngayThang.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(ngayThang);

        document.add((billInfoTable));

        document.add(shipInfoTable);

        document.add(table);

        document.close();

    }
}
