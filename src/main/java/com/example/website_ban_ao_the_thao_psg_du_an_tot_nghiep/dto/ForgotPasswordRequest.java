package com.example.website_ban_ao_the_thao_ps.dto;

public class ForgotPasswordRequest {
    private String email;
    private String soCanCuocCongDan;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String email, String soCanCuocCongDan) {
        this.email = email;
        this.soCanCuocCongDan = soCanCuocCongDan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoCanCuocCongDan() {
        return soCanCuocCongDan;
    }

    public void setSoCanCuocCongDan(String soCanCuocCongDan) {
        this.soCanCuocCongDan = soCanCuocCongDan;
    }
}
