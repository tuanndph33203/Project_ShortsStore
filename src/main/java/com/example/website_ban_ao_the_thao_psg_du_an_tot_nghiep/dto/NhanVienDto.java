package com.example.website_ban_ao_the_thao_ps.dto;

public class NhanVienDto {

    private Integer id;
    private String ten;

    private String email;

    private String sdt;

    private String matKhau;

    public NhanVienDto() {
    }

    public NhanVienDto(Integer id, String ten, String email, String sdt, String matKhau) {
        this.id = id;
        this.ten = ten;
        this.email = email;
        this.sdt = sdt;
        this.matKhau = matKhau;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

}
