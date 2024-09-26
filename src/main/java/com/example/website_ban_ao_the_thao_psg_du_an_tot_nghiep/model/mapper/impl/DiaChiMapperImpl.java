package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.DiaChi;
import com.example.website_ban_ao_the_thao_ps.model.mapper.DiaChiMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDiaChiRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDiaChiRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.DiaChiResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class DiaChiMapperImpl implements DiaChiMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public DiaChi diaChiResponseToDiaChiEntity(DiaChiResponse diaChiResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DiaChi diaChi = modelMapper.map(diaChiResponse, DiaChi.class);
        return diaChi;
    }

    @Override
    public DiaChiResponse diaChiEntityToDiaChiResponse(DiaChi diaChi) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DiaChiResponse diaChiResponse = modelMapper.map(diaChi, DiaChiResponse.class);
        return diaChiResponse;
    }

    @Override
    public DiaChi createDiaChiRequestToDiaChiEntity(CreateDiaChiRequest createDiaChiRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DiaChi diaChi = modelMapper.map(createDiaChiRequest, DiaChi.class);
        return diaChi;
    }

    @Override
    public DiaChi updateDiaChiRequestToDiaChiEntity(UpdateDiaChiRequest updateDiaChiRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DiaChi diaChi = modelMapper.map(updateDiaChiRequest, DiaChi.class);
        return diaChi;
    }

    @Override
    public List<DiaChiResponse> listDiaChiEntityToDiaChiResponse(List<DiaChi> diaChiList) {
        List<DiaChiResponse> list = new ArrayList<>(diaChiList.size());
        for (DiaChi dc : diaChiList
             ) {
            list.add(diaChiEntityToDiaChiResponse(dc));
        }
        return list;
    }
}
