package com.example.website_ban_ao_the_thao_ps.dto;

public class ChangeEmailRequest {
    private KhachHangDto khachHangDto;

    private String email;
    private String matKhau;
    private String newEmail;

    public ChangeEmailRequest() {
    }

    public ChangeEmailRequest(KhachHangDto khachHangDto, String email, String matKhau, String newEmail) {
        this.khachHangDto = khachHangDto;
        this.email = email;
        this.matKhau = matKhau;
        this.newEmail = newEmail;
    }

    public KhachHangDto getKhachHangDto() {
        return khachHangDto;
    }

    public void setKhachHangDto(KhachHangDto khachHangDto) {
        this.khachHangDto = khachHangDto;
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

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
