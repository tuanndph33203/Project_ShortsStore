package com.example.website_ban_ao_the_thao_ps.controller;


import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.dto.ChangeEmailRequest;
import com.example.website_ban_ao_the_thao_ps.dto.ChangePasswordRequest;
import com.example.website_ban_ao_the_thao_ps.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_ps.dto.LoginMesage;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.exception.EmailExistenceResponse;
import com.example.website_ban_ao_the_thao_ps.exception.ResourceNotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.repository.DiaChiRepository;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.DiaChiService;
import com.example.website_ban_ao_the_thao_ps.service.KhachHangService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiTaiKhoan.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiTaiKhoan.INACTIVE;

@RequestMapping("/api/khach-hang")
@RestController
@CrossOrigin(origins = "*", maxAge = -1)
public class KhachHangController {
    @Autowired
    KhachHangService khachHangService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private DiaChiService diaChiService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageKhachHang(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(name = "trangThai",required = false) ApplicationConstant.TrangThaiTaiKhoan trangThai,
                                    @RequestParam(name = "thuHangId",required = false) Integer thuHangId,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(khachHangService.pageSearchKhachHang(pageNo, pageSize, search, trangThai, thuHangId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listKhachHangAcitve() {
        return ResponseEntity.ok(khachHangService.listKhachHangResponse());
    }

    @GetMapping("/list-email-sdt")
    public ResponseEntity<?> listEmailAndSdtKhachHang() {
        return ResponseEntity.ok(khachHangService.listKhachHangInfo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKhachHangId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(khachHangService.getOne(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getKhachHangEmail(@PathVariable String email) {
        return ResponseEntity.ok(khachHangService.findKhachHangByEmail(email));
    }




    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        byte[] excelFile = khachHangService.exportExcelKhachHang();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
        response.getOutputStream().write(excelFile);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createKhachHang(@Valid @RequestBody CreateKhachHangRequest createKhachHangRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(khachHangService.create(createKhachHangRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKhachHang(@Valid @RequestBody UpdateKhachHangRequest updateKhachHangRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(khachHangService.update(updateKhachHangRequest, id));
    }


    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertKhachHang(@PathVariable("id") Integer id) {
        khachHangService.removeOrRevert(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeKhachHang(@PathVariable("id") Integer id) {
        khachHangService.removeOrRevert(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        KhachHangDto khachHangDto = new KhachHangDto();
        khachHangDto.setEmail(changePasswordRequest.getEmail());
        khachHangDto.setMatKhau(changePasswordRequest.getCurrentPassword());
        boolean isPasswordChanged = khachHangService.changePassword(khachHangDto, changePasswordRequest.getNewPassword());
        if (isPasswordChanged) {
            return ResponseEntity.ok().body("Đổi mật khẩu thành công");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Đổi mật khẩu không thành công. Vui lòng kiểm tra lại email và mật khẩu cũ.");
        }
    }

    @PutMapping("/update-info")
    public ResponseEntity<String> updatePersonalInfo(HttpSession session, @RequestBody KhachHangDto khachHangDto, @RequestHeader("Authorization") String authorization) {
        try {
            Integer khachHangID;
            if (session.getAttribute("khachHangID") != null) {
                khachHangID = (Integer) session.getAttribute("khachHangID");
            } else {
                khachHangID = Integer.parseInt(authorization.substring(7));
                session.setAttribute("khachHangID", khachHangID);
            }
            boolean success = khachHangService.suaThongTin(khachHangDto, khachHangID);
            if (success) {
                return new ResponseEntity<>("Thông tin khách hàng đã được cập nhật thành công.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Không thể cập nhật thông tin khách hàng. Người dùng không tồn tại.", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Không thể xác định người dùng hiện tại.", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExistence(@RequestParam String email) {
        try {
            boolean emailExists = khachHangService.checkMail(email);
            Map<String, Boolean> response = new HashMap<>();
            response.put("exists", emailExists);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("error", true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
       try {
           khachHangService.sendForgotPasswordEmail(email);
           return ResponseEntity.ok().body("Mật khẩu mới đã được gửi đến địa chỉ email của bạn.");
       }catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }


    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@RequestBody KhachHangDto khachHangDto, @RequestParam String newEmail) {
        boolean isChanged = khachHangService.changeEmail(khachHangDto, newEmail);
        if (isChanged) {
            return ResponseEntity.ok("Thay đổi email thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vui lòng nhập lại mật khẩu của mình");
        }
    }

    @PutMapping("/update-image/{email}")
    public ResponseEntity<?> updateImage(@PathVariable("email") String email, @RequestParam("image") String image){
        this.khachHangService.updateImage(image, email);
        return ResponseEntity.ok("Updated image");
    }
}
