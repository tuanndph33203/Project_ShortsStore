package com.example.website_ban_ao_the_thao_ps.dto;

import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;

public class LoginMesage {

    String message;

    private KhachHang khachHang;

    Boolean status;

    private boolean success;

    private int id;

    public LoginMesage(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LoginMesage(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

}
