package com.example.website_ban_ao_the_thao_ps.common;

import java.util.Random;

public class GenCode {
    private static final String chatLieu = "CL0";
    private static final String chuongTrinhTichDiem = "TD0";
    private static final String sanPham = "SP0";
    private static final String voucher = "VC0";
    private static final String loaiSanPham = "LSP0";
    private static final String nhaSanXuat = "NhaSX0";
    private static final String kichThuoc = "KT0";
    private static final String coAo = "CA0";
    private static final String congNghe = "CN0";
    private static final String nuocSanXuat = "NuocSX0";
    private static final String dongSanPham = "DSP0";
    private static final String thuongHieu = "TH0";
    private static final String cauThu = "CT0";
    private static final String hoaDon = "HD0";
    private static final String vaiTro = "VT0";
    private static final String thuHang = "TH0";
    private static  final String nhanVien="NV0";
    private static  final String khachHang="KH0";
    private static  final String quyDinh="QD0";
    private static  final String passWordKhachHang="PWKH";
    private static  final String passWordNhanVien="PWNV";
    private static  final String maGiaoDichTienMat="MGDTM00";
    private static final int NUMBER_LENGTH_Khach_Hang_And_NhanVien = 8;
    private static final int NUMBER_LENGTH = 5;
    private static final int NUMBER_HOADON_LENGTH = 10;
    private static final int NUMBER_GiaoDich_LENGTH = 10;

    public static String generateChatLieuCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return chatLieu + formattedNumber;
    }
    public static String generateChuongTrinhTichDiemCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return chuongTrinhTichDiem + formattedNumber;
    }
    public static String generateQuyDinhCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return quyDinh + formattedNumber;
    }
    public static String generateVoucherCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return voucher + formattedNumber;
    }
    public static String generateNhanVienCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return nhanVien + formattedNumber;
    }
    public static String generateKhachHangCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return khachHang + formattedNumber;
    }

    public static String generateSanPhamCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return sanPham + formattedNumber;
    }

    public static String generateLoaiSanPhamCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return loaiSanPham + formattedNumber;
    }

    public static String generateNhaSanXuatCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return nhaSanXuat + formattedNumber;
    }

    public static String generateKichThuocCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return kichThuoc + formattedNumber;
    }

    public static String generateCoAoCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return coAo + formattedNumber;
    }

    public static String generateCongNgheCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return congNghe + formattedNumber;
    }

    public static String generateNuocSanXuatCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return nuocSanXuat + formattedNumber;
    }

    public static String generateDongSanPhamCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return dongSanPham + formattedNumber;
    }

    public static String generateThuongHieuCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return thuongHieu + formattedNumber;
    }

    public static String generateCauThuCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return cauThu + formattedNumber;
    }

    public static String generateVaiTroCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return vaiTro + formattedNumber;
    }

    public static String generateThuHangCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH + "d", randomNumber);
        return thuHang + formattedNumber;
    }

    public static String generatePassWordKhachHang() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH_Khach_Hang_And_NhanVien));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH_Khach_Hang_And_NhanVien + "d", randomNumber);
        return passWordKhachHang + formattedNumber;
    }

    public static String generatePassWordNhanVien() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_LENGTH_Khach_Hang_And_NhanVien));
        String formattedNumber = String.format("%0" + NUMBER_LENGTH_Khach_Hang_And_NhanVien + "d", randomNumber);
        return passWordNhanVien + formattedNumber;
    }

    public static String generateHoaDonCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_HOADON_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_HOADON_LENGTH + "d", randomNumber);
        return hoaDon + formattedNumber;
    }

    public static String generateGiaoDichCode() {
        Random random = new Random();
        int randomNumber = random.nextInt((int) Math.pow(10, NUMBER_GiaoDich_LENGTH));
        String formattedNumber = String.format("%0" + NUMBER_GiaoDich_LENGTH + "d", randomNumber);
        return maGiaoDichTienMat + formattedNumber;
    }



}
