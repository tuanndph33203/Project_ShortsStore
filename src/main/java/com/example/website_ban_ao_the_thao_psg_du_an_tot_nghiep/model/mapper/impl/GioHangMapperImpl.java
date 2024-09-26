package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.GioHang;
import com.example.website_ban_ao_the_thao_ps.model.mapper.GioHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateGioHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateGioHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
public class GioHangMapperImpl implements GioHangMapper {

    @Autowired
    ModelMapper mapper;

    @Override
    public GioHang gioHangResponseToGioHangEntity(GioHangResponse gioHangResponse) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHang gioHang = mapper.map(gioHangResponse, GioHang.class);
        return gioHang;
    }

    @Override
    public GioHangResponse gioHangEntityToGioHangResponse(GioHang gioHang) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHangResponse gioHangResponse = mapper.map(gioHang, GioHangResponse.class);
        return gioHangResponse;
    }

    @Override
    public GioHang createGioHangRequestToGioHangEntity(CreateGioHangRequest createGioHangRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHang gioHang = mapper.map(createGioHangRequest, GioHang.class);
        return gioHang;
    }

    @Override
    public GioHang updateGioHangRequestToGioHangEntity(UpdateGioHangRequest updateGioHangRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHang gioHang = mapper.map(updateGioHangRequest, GioHang.class);
        return gioHang;
    }

    @Override
    public List<GioHangResponse> listGioHangEntityToGioHangResponse(List<GioHang> listGioHang) {
        List<GioHangResponse> list = new ArrayList<>(listGioHang.size());
        for (GioHang hd : listGioHang) {
            list.add(gioHangEntityToGioHangResponse(hd));
        }
        return list;
    }
}
