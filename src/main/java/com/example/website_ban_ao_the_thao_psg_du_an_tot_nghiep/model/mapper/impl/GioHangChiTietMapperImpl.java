package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.GioHangChiTiet;
import com.example.website_ban_ao_the_thao_ps.model.mapper.GioHangChiTietMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateGioHangChiTietRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateGioHangChiTietRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangChiTietResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
public class GioHangChiTietMapperImpl implements GioHangChiTietMapper {

    @Autowired
    ModelMapper mapper;

    @Override
    public GioHangChiTiet gioHangChiTietResponseToGioHangChiTietEntity(GioHangChiTietResponse gioHangChiTietResponse) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHangChiTiet gioHangChiTiet = mapper.map(gioHangChiTietResponse, GioHangChiTiet.class);
        return gioHangChiTiet;
    }

    @Override
    public GioHangChiTietResponse gioHangChiTietEntityToGioHangChiTietResponse(GioHangChiTiet gioHangChiTiet) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHangChiTietResponse gioHangChiTietResponse = mapper.map(gioHangChiTiet, GioHangChiTietResponse.class);
        return gioHangChiTietResponse;
    }

    @Override
    public GioHangChiTiet createGioHangChiTietRequestToGioHangChiTietEntity(CreateGioHangChiTietRequest createGioHangChiTietRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHangChiTiet gioHangChiTiet = mapper.map(createGioHangChiTietRequest, GioHangChiTiet.class);
        return gioHangChiTiet;
    }

    @Override
    public GioHangChiTiet updateGioHangChiTietRequestToGioHangChiTietEntity(UpdateGioHangChiTietRequest updateGioHangChiTietRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        GioHangChiTiet gioHangChiTiet = mapper.map(updateGioHangChiTietRequest, GioHangChiTiet.class);
        return gioHangChiTiet;
    }

    @Override
    public List<GioHangChiTietResponse> listGioHangChiTietEntityToGioHangChiTietResponse(List<GioHangChiTiet> listGioHangChiTiet) {
        List<GioHangChiTietResponse> list = new ArrayList<>(listGioHangChiTiet.size());
        for (GioHangChiTiet hd : listGioHangChiTiet) {
            list.add(gioHangChiTietEntityToGioHangChiTietResponse(hd));
        }
        return list;
    }
}
