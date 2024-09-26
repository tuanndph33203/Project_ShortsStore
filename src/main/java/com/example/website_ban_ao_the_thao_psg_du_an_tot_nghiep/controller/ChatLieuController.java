package com.example.website_ban_ao_the_thao_ps.controller;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.service.ChatLieuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;
import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.INACTIVE;

@RequestMapping("/api/chat-lieu")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class ChatLieuController {
    @Autowired
    ChatLieuService chatLieuService;

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(chatLieuService.pageChatLieu(pageNo, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "search",required = false) String search,
                                    @RequestParam(defaultValue = "0", name = "pageNo", required = false) Integer pageNo,
                                    @RequestParam(defaultValue = "5", name = "pageSize", required = false) Integer pageSize) {
        return ResponseEntity.ok(chatLieuService.pageSearchChatLieu(pageNo, pageSize, search));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listChatLieuAcitve() {
        return ResponseEntity.ok(chatLieuService.listChatLieuResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChatLieuId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chatLieuService.getOneChatLieu(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChatLieu(@Valid @RequestBody CreateChatLieuRequest createChatLieuRequest, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(chatLieuService.createChatLieu(createChatLieuRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChatLieu(@Valid @RequestBody UpdateChatLieuRequest updateChatLieuRequest, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.ok(chatLieuService.updateChatLieu(updateChatLieuRequest, id));
    }

    @PutMapping("/revert/{id}")
    public ResponseEntity<?> revertChatLieu(@PathVariable("id") Integer id) {
        chatLieuService.removeOrRevertChatLieu(String.valueOf(ACTIVE), id);
        return ResponseEntity.ok("Revert Success");
    }


    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeChatLieu(@PathVariable("id") Integer id) {
        chatLieuService.removeOrRevertChatLieu(String.valueOf(INACTIVE), id);
        return ResponseEntity.ok("Remove Success");
    }
}
