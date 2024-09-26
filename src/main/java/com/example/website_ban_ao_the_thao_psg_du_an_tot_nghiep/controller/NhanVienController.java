package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.dto.ForgotPasswordRequest;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_ps.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_ps.dto.NhanVienDto;
import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.exception.ResourceNotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.service.NhanVienService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiTaiKhoan.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiTaiKhoan.INACTIVE;

@RequestMapping("/api/nhan-vien")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class NhanVienController {
    @Autowired
    NhanVienService nhanVienService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageNhanVien(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(name = "trangThai", required = false) ApplicationConstant.TrangThaiTaiKhoan trangThai,
                                    @RequestParam(name = "vaiTroId", required = false) Integer vaiTroId,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(nhanVienService.pageSearchNhanVien(pageNo, pageSize, search, trangThai, vaiTroId));
    }

    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = nhanVienService.exportExcelNhanVien();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listNhanVienAcitve() {
        return ResponseEntity.ok(nhanVienService.listNhanVienResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNhanVienId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(nhanVienService.getOne(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getNhanVienByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(nhanVienService.getOneNhanVienByEmail(email));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNhanVien(@Valid @RequestBody CreateNhanVienRequest createNhanVienRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhanVienService.create(createNhanVienRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNhanVien(@Valid @RequestBody UpdateNhanVienRequest updateNhanVienRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(nhanVienService.update(updateNhanVienRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertNhanVien(@PathVariable("id") Integer id) {
        nhanVienService.removeOrRevert(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeNhanVien(@PathVariable("id") Integer id) {
        nhanVienService.removeOrRevert(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }

    @PostMapping("/changeEmailNv")
    public ResponseEntity<String> changeEmail(@RequestBody NhanVienDto nhanVienDto, @RequestParam String newEmail) {
        boolean isChanged = nhanVienService.changeEmail(nhanVienDto, newEmail);
        if (isChanged) {
            return ResponseEntity.ok("Thay đổi email thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lại mật khẩu của mình");
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> sendForgotPasswordEmailForNhanVien(@RequestBody ForgotPasswordRequest request) {
        try {
            nhanVienService.sendForgotPasswordEmailForNhanVien(request.getEmail());
            return ResponseEntity.ok("Mật khẩu đã được gửi lại vào email của nhân viên.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update-password/{email}")
    public ResponseEntity<?> updatePassword(@PathVariable("email") String email, @RequestParam("newPassword") String newPassword){
        this.nhanVienService.updatePassword(newPassword, email);
        return ResponseEntity.ok("Password was updated");
    }

    @PutMapping("/update-image/{email}")
    public ResponseEntity<?> updateImage(@PathVariable("email") String email, @RequestParam("image") String image){
        this.nhanVienService.updateImageNV(image, email);
        return ResponseEntity.ok("Image was updated");
    }
}
