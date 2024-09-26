package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.HoaDonChiTiet;
import com.example.website_ban_ao_the_thao_ps.model.mapper.HoaDonChiTietMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateHoaDonChiTietRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateHoaDonChiTietRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonChiTietResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
public class HoaDonChiTietMapperImpl implements HoaDonChiTietMapper {

    @Autowired
    ModelMapper mapper;

    @Override
    public HoaDonChiTiet hoaDonChiTietResponseToHoaDonChiTietEntity(HoaDonChiTietResponse hoaDonChiTietResponse) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDonChiTiet hoaDonChiTiet = mapper.map(hoaDonChiTietResponse, HoaDonChiTiet.class);
        return hoaDonChiTiet;
    }

    @Override
    public HoaDonChiTietResponse hoaDonChiTietEntityToHoaDonChiTietResponse(HoaDonChiTiet hoaDonChiTiet) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDonChiTietResponse hoaDonChiTietResponse = mapper.map(hoaDonChiTiet, HoaDonChiTietResponse.class);
        return hoaDonChiTietResponse;
    }

    @Override
    public HoaDonChiTiet createHoaDonChiTietRequestToHoaDonChiTietEntity(CreateHoaDonChiTietRequest createHoaDonChiTietRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDonChiTiet hoaDonChiTiet = mapper.map(createHoaDonChiTietRequest, HoaDonChiTiet.class);
        return hoaDonChiTiet;
    }

    @Override
    public HoaDonChiTiet updateHoaDonChiTietRequestToHoaDonChiTietEntity(UpdateHoaDonChiTietRequest updateHoaDonChiTietRequest) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        HoaDonChiTiet hoaDonChiTiet = mapper.map(updateHoaDonChiTietRequest, HoaDonChiTiet.class);
        return hoaDonChiTiet;
    }

    @Override
    public List<HoaDonChiTietResponse> listHoaDonChiTietEntityToHoaDonChiTietResponse(List<HoaDonChiTiet> listHoaDonChiTiet) {
        List<HoaDonChiTietResponse> list = new ArrayList<>(listHoaDonChiTiet.size());
        for (HoaDonChiTiet hd : listHoaDonChiTiet) {
            list.add(hoaDonChiTietEntityToHoaDonChiTietResponse(hd));
        }
        return list;
    }
}
