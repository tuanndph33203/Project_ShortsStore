package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ThuHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuHangResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class ThuHangMapperImpl implements ThuHangMapper {
    
    @Autowired
    ModelMapper modelMapper;
    
    @Override
    public ThuHang thuHangResponseToThuHangEntity(ThuHangResponse thuHangResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuHang thuHang = modelMapper.map(thuHangResponse, ThuHang.class);
        return thuHang;
    }

    @Override
    public ThuHangResponse thuHangEntiyToThuHangResponse(ThuHang thuHang) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuHangResponse thuHangResponse = modelMapper.map(thuHang, ThuHangResponse.class);
        return thuHangResponse;
    }

    @Override
    public ThuHang createThuHangRequestToThuHangEntity(CreateThuHangRequest createThuHangRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuHang thuHang = modelMapper.map(createThuHangRequest, ThuHang.class);
        return thuHang;
    }

    @Override
    public ThuHang updateThuHangRequestToThuHangEntity(UpdateThuHangRequest updateThuHangRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ThuHang thuHang = modelMapper.map(updateThuHangRequest, ThuHang.class);
        return thuHang;
    }

    @Override
    public List<ThuHangResponse> listThuHangEntityToThuHangResponse(List<ThuHang> thuHangMapperList) {
        List<ThuHangResponse> list = new ArrayList<>(thuHangMapperList.size());
        for (ThuHang th : thuHangMapperList
             ) {
            list.add(thuHangEntiyToThuHangResponse(th));
        }
        return list;
    }
}
