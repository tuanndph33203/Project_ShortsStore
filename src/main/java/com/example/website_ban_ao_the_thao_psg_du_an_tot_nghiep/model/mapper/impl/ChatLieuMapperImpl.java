package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.ChatLieu;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChatLieuMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChatLieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChatLieuResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class ChatLieuMapperImpl implements ChatLieuMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ChatLieu chatLieuResponseToChatLieuEntity(ChatLieuResponse chatLieuResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChatLieu chatLieu = modelMapper.map(chatLieuResponse, ChatLieu.class);
        return chatLieu;
    }

    @Override
    public ChatLieuResponse chatLieuEntityToChatLieuResponse(ChatLieu chatLieu) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChatLieuResponse chatLieuResponse = modelMapper.map(chatLieu, ChatLieuResponse.class);
        return chatLieuResponse;
    }

    @Override
    public ChatLieu createChatLieuRequestToChatLieuEntity(CreateChatLieuRequest createChatLieuRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChatLieu chatLieu = modelMapper.map(createChatLieuRequest, ChatLieu.class);
        return chatLieu;
    }

    @Override
    public ChatLieu updateChatLieuRequestToChatLieuEntity(UpdateChatLieuRequest updateChatLieuRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChatLieu chatLieu = modelMapper.map(updateChatLieuRequest, ChatLieu.class);
        return chatLieu;
    }

    @Override
    public List<ChatLieuResponse> listChatLieuEntityToChatLieuResponse(List<ChatLieu> chatLieuList) {
        List<ChatLieuResponse> list = new ArrayList<>(chatLieuList.size());
        for (ChatLieu cl : chatLieuList) {
            list.add(chatLieuEntityToChatLieuResponse(cl));
        }
        return list;
    }
}
