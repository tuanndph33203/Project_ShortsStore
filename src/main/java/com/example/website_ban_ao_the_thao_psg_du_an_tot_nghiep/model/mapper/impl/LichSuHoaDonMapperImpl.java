package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.LichSuHoaDon;
import com.example.website_ban_ao_the_thao_ps.model.mapper.LichSuHoaDonMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateLichSuHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateLichSuHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.LichSuHoaDonResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
public class LichSuHoaDonMapperImpl implements LichSuHoaDonMapper {
    @Autowired
    ModelMapper modelMapper;
    @Override
    public LichSuHoaDon lichSuHoaDonResponseToLichSuHoaDonEntity(LichSuHoaDonResponse lichSuHoaDonResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LichSuHoaDon lichSuHoaDon = modelMapper.map(lichSuHoaDonResponse, LichSuHoaDon.class);
        return lichSuHoaDon;
    }

    @Override
    public LichSuHoaDonResponse lichSuHoaDonEntityToLichSuHoaDonResponse(LichSuHoaDon lichSuHoaDon) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LichSuHoaDonResponse lichSuHoaDonResponse = modelMapper.map(lichSuHoaDon, LichSuHoaDonResponse.class);
        return lichSuHoaDonResponse;
    }

    @Override
    public LichSuHoaDon createLichSuHoaDonRequestToLichSuHoaDonEntity(CreateLichSuHoaDonRequest createLichSuHoaDonRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LichSuHoaDon lichSuHoaDon = modelMapper.map(createLichSuHoaDonRequest, LichSuHoaDon.class);
        return lichSuHoaDon;
    }

    @Override
    public LichSuHoaDon updateLichSuHoaDonRequestToLichSuHoaDonEntity(UpdateLichSuHoaDonRequest updateLichSuHoaDonRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LichSuHoaDon lichSuHoaDon = modelMapper.map(updateLichSuHoaDonRequest, LichSuHoaDon.class);
        return lichSuHoaDon;
    }

    @Override
    public List<LichSuHoaDonResponse> listLichSuHoaDonEntityToLichSuHoaDonResponse(List<LichSuHoaDon> lichSuHoaDonList) {
        List<LichSuHoaDonResponse> list = new ArrayList<>(lichSuHoaDonList.size());
        for (LichSuHoaDon th : lichSuHoaDonList) {
            list.add(lichSuHoaDonEntityToLichSuHoaDonResponse(th));
        }
        return list;
    }
}
