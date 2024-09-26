package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.NhanVienResponse;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.KhachHangService;
import com.example.website_ban_ao_the_thao_ps.service.NhanVienService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class  LoginController {


    @Autowired
    KhachHangService khachHangService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    NhanVienService nhanVienService;

    @PostMapping("/khach-hang")
    public ResponseEntity<?> loginKH(@RequestBody KhachHangResponse khresponse, HttpServletResponse response) {
        KhachHangResponse khachHangLogin = khachHangService.findKhachHangByEmail(khresponse.getEmail());

        if (khachHangLogin != null && passwordEncoder.matches(khresponse.getMatKhau(), khachHangLogin.getMatKhau())) {
            Cookie cookie = new Cookie("email", khachHangLogin.getEmail());
            response.addCookie(cookie);
            System.out.println(cookie+ "aaaaa");
            return new ResponseEntity<>(khachHangLogin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/nhan-vien")
    public ResponseEntity<?> loginNV(@RequestBody NhanVien nhanVien, HttpServletResponse response) {
        NhanVienResponse nhanVienLogin = nhanVienService.findByEmail(nhanVien.getEmail());
        if (nhanVienLogin != null && passwordEncoder.matches(nhanVien.getMatKhau(), nhanVienLogin.getMatKhau())) {
            Cookie cookie = new Cookie("email", nhanVienLogin.getEmail());

            response.addCookie(cookie);
            return new ResponseEntity<>(nhanVienLogin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}