package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.ThuongHieu;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ThuongHieuMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuongHieuResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class ThuongHieuMapperImpl implements ThuongHieuMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ThuongHieu thuongHieuResponseToThuongHieuEntity(ThuongHieuResponse thuongHieuResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuongHieu thuongHieu = modelMapper.map(thuongHieuResponse, ThuongHieu.class);
        return thuongHieu;
    }

    @Override
    public ThuongHieuResponse thuongHieuEntityToThuongHieuResponse(ThuongHieu thuongHieu) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuongHieuResponse thuongHieuResponse = modelMapper.map(thuongHieu, ThuongHieuResponse.class);
        return thuongHieuResponse;
    }

    @Override
    public ThuongHieu createThuongHieuRequestToThuongHieuEntity(CreateThuongHieuRequest createThuongHieuRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuongHieu thuongHieu = modelMapper.map(createThuongHieuRequest, ThuongHieu.class);
        return thuongHieu;
    }

    @Override
    public ThuongHieu updateThuongHieuRequestToThuongHieuEntity(UpdateThuongHieuRequest updateThuongHieuRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuongHieu thuongHieu = modelMapper.map(updateThuongHieuRequest, ThuongHieu.class);
        return thuongHieu;
    }

    @Override
    public List<ThuongHieuResponse> listThuongHieuEntityToThuongHieuResponse(List<ThuongHieu> thuongHieuList) {
        List<ThuongHieuResponse> list = new ArrayList<>(thuongHieuList.size());
        for (ThuongHieu th : thuongHieuList) {
            list.add(thuongHieuEntityToThuongHieuResponse(th));
        }
        return list;
    }
}
