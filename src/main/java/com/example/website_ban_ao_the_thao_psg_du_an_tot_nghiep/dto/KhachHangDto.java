package com.example.website_ban_ao_the_thao_ps.dto;

import java.time.LocalDate;

public class KhachHangDto {

    private Boolean gioiTinh;

    private String diaChi;

    private LocalDate ngaySinh;



    private String ten;

    private String email;

    private String sdt;


    private String matKhau;

    public KhachHangDto() {
    }


    public KhachHangDto(Boolean gioiTinh, String ten, String email, String sdt, String matKhau, String diaChi, LocalDate ngaySinh) {
        this.gioiTinh = gioiTinh;
        this.ten = ten;
        this.email = email;
        this.sdt = sdt;
        this.matKhau = matKhau;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
