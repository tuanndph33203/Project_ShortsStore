package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.dto.KhachHangDto;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.model.mapper.KhachHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class KhachHangMapperImpl implements KhachHangMapper {

    @Autowired
    ModelMapper modelMapper;
    @Override
    public KhachHang khachHangResponseToKhachHangEntity(KhachHangResponse khachHangResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KhachHang khachHang =modelMapper.map(khachHangResponse, KhachHang.class);
        return khachHang;
    }

    @Override
    public KhachHangResponse khachHangEntityToKhachHangResponse(KhachHang khachHang) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KhachHangResponse khachHangResponse =modelMapper.map(khachHang, KhachHangResponse.class);
        return khachHangResponse;
    }

    @Override
    public KhachHang createKhachHangRequestToKhachHangEntity(CreateKhachHangRequest createKhachHangRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KhachHang khachHang =modelMapper.map(createKhachHangRequest, KhachHang.class);
        return khachHang;
    }

    @Override
    public KhachHang updateKhachHangRequestToKhachHangEntity(UpdateKhachHangRequest updateKhachHangRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KhachHang khachHang =modelMapper.map(updateKhachHangRequest, KhachHang.class);
        return khachHang;
    }

    @Override
    public List<KhachHangResponse> listKhachHangEntityToKhachHangResponse(List<KhachHang> khachHangList) {
        List<KhachHangResponse> list = new ArrayList<>(khachHangList.size());
        for (KhachHang th : khachHangList) {
            list.add(khachHangEntityToKhachHangResponse(th));
        }
        return list;
    }



}
