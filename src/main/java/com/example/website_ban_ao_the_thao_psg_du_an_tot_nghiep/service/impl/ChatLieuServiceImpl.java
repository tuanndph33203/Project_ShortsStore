package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.ChatLieu;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChatLieuMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChatLieuResponse;
import com.example.website_ban_ao_the_thao_ps.repository.ChatLieuRepository;
import com.example.website_ban_ao_the_thao_ps.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ChatLieuServiceImpl implements ChatLieuService {
    @Autowired
    ChatLieuRepository chatLieuRepository;

    @Autowired
    ChatLieuMapper chatLieuMapper;

    @Override
    public Page<ChatLieuResponse> pageChatLieu(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ChatLieu> chatLieuPage = chatLieuRepository.findAll(pageable);
        return chatLieuPage.map(chatLieuMapper::chatLieuEntityToChatLieuResponse);
    }

    @Override
    public Page<ChatLieuResponse> pageSearchChatLieu(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ChatLieu> chatLieuPage = chatLieuRepository.pageSearch(pageable, search);
        return chatLieuPage.map(chatLieuMapper::chatLieuEntityToChatLieuResponse);
    }

    @Override
    public List<ChatLieuResponse> listChatLieuResponse() {
        return chatLieuMapper.listChatLieuEntityToChatLieuResponse(chatLieuRepository.getChatLieuByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public ChatLieuResponse createChatLieu(CreateChatLieuRequest createChatLieuRequest) {
        try {
            ChatLieu chatLieu = chatLieuMapper.createChatLieuRequestToChatLieuEntity(createChatLieuRequest);
            chatLieu.setMa(GenCode.generateChatLieuCode());
            chatLieu.setNgayTao(LocalDate.now());
            chatLieu.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return chatLieuMapper.chatLieuEntityToChatLieuResponse(chatLieuRepository.save(chatLieu));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create chat lieu. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create chat lieu due to an unexpected error." + ex);
        }
    }

    @Override
    public ChatLieuResponse updateChatLieu(UpdateChatLieuRequest updateChatLieuRequest, Integer id) {
        try {
            ChatLieu chatLieu = chatLieuRepository.findById(id).orElseThrow(() -> new NotFoundException("ChatLieu not found with id " + id));
            ChatLieu updateChatLieu = chatLieuMapper.updateChatLieuRequestToChatLieuEntity(updateChatLieuRequest);
            chatLieu.setTen(updateChatLieu.getTen());
            chatLieu.setNgayCapNhat(LocalDate.now());
            return chatLieuMapper.chatLieuEntityToChatLieuResponse(chatLieuRepository.save(chatLieu));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update chat lieu. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update chat lieu due to an unexpected error." + ex);
        }
    }

    @Override
    public ChatLieuResponse getOneChatLieu(Integer id) {
        ChatLieu chatLieu = chatLieuRepository.findById(id).orElseThrow(() -> new NotFoundException("ChatLieu not found with id " + id));
        return chatLieuMapper.chatLieuEntityToChatLieuResponse(chatLieu);
    }

    @Override
    public void removeOrRevertChatLieu(String trangThaiChatLieu, Integer id) {
        chatLieuRepository.removeOrRevert(trangThaiChatLieu, id);
    }
}
