package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.model.mapper.HoaDonMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
public class HoaDonMapperImpl implements HoaDonMapper {

    @Autowired
    ModelMapper mapper;

    @Override
    public HoaDon hoaDonResponseToHoaDonEntity(HoaDonResponse hoaDonResponse) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDon hoaDon = mapper.map(hoaDonResponse, HoaDon.class);
        return hoaDon;
    }

    @Override
    public HoaDonResponse hoaDonEntityToHoaDonResponse(HoaDon hoaDon) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDonResponse hoaDonResponse = mapper.map(hoaDon, HoaDonResponse.class);
        return hoaDonResponse;
    }

    @Override
    public HoaDon createHoaDonRequestToHoaDonEntity(CreateHoaDonRequest createHoaDonRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDon hoaDon = mapper.map(createHoaDonRequest, HoaDon.class);
        return hoaDon;
    }

    @Override
    public HoaDon updateHoaDonRequestToHoaDonEntity(UpdateHoaDonRequest updateHoaDonRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDon hoaDon = mapper.map(updateHoaDonRequest, HoaDon.class);
        return hoaDon;
    }

    @Override
    public List<HoaDonResponse> listHoaDonEntityToHoaDonResponse(List<HoaDon> listHoaDon) {
        List<HoaDonResponse> list = new ArrayList<>(listHoaDon.size());
        for (HoaDon hd : listHoaDon) {
            list.add(hoaDonEntityToHoaDonResponse(hd));
        }
        return list;
    }
}
