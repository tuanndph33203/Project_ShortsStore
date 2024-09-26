package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChatLieuResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatLieuService {
    Page<ChatLieuResponse> pageChatLieu(Integer pageNo, Integer size);

    Page<ChatLieuResponse> pageSearchChatLieu(Integer pageNo,Integer size,String search);

    List<ChatLieuResponse> listChatLieuResponse();

    ChatLieuResponse createChatLieu(CreateChatLieuRequest createChatLieuRequest);

    ChatLieuResponse updateChatLieu(UpdateChatLieuRequest updateChatLieuRequest, Integer id);

    ChatLieuResponse getOneChatLieu(Integer id);

    void removeOrRevertChatLieu(String trangThaiChatLieu,Integer id);
}
