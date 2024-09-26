package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import com.example.website_ban_ao_the_thao_ps.model.mapper.TiLeQuyDoiThuHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.TiLeQuyDoiThuHangResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class TiLeQuyDoiThuHangMapperImpl implements TiLeQuyDoiThuHangMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public TiLeQuyDoiThuHang tiLeQuyDoiThuHangResponseToTiLeQuyDoiThuHangEntity(TiLeQuyDoiThuHangResponse tiLeQuyDoiThuHangResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        TiLeQuyDoiThuHang tiLeQuyDoiThuHang = modelMapper.map(tiLeQuyDoiThuHangResponse, TiLeQuyDoiThuHang.class);
        return tiLeQuyDoiThuHang;
    }

    @Override
    public TiLeQuyDoiThuHangResponse tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(TiLeQuyDoiThuHang tiLeQuyDoiThuHang) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        TiLeQuyDoiThuHangResponse tiLeQuyDoiThuHangResponse = modelMapper.map(tiLeQuyDoiThuHang, TiLeQuyDoiThuHangResponse.class);
        return tiLeQuyDoiThuHangResponse;
    }

    @Override
    public TiLeQuyDoiThuHang createTiLeQuyDoiThuHangRequestToTiLeQuyDoiThuHangEntity(CreateTiLeQuyDoiThuHangRequest createTiLeQuyDoiThuHangRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        TiLeQuyDoiThuHang tiLeQuyDoiThuHang = modelMapper.map(createTiLeQuyDoiThuHangRequest, TiLeQuyDoiThuHang.class);
        return tiLeQuyDoiThuHang;
    }

    @Override
    public TiLeQuyDoiThuHang updateTiLeQuyDoiThuHangRequestToTiLeQuyDoiThuHangEntity(UpdateTiLeQuyDoiThuHangRequest updateTiLeQuyDoiThuHangRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        TiLeQuyDoiThuHang tiLeQuyDoiThuHang = modelMapper.map(updateTiLeQuyDoiThuHangRequest, TiLeQuyDoiThuHang.class);
        return tiLeQuyDoiThuHang;
    }

    @Override
    public List<TiLeQuyDoiThuHangResponse> listTiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList) {
        List<TiLeQuyDoiThuHangResponse> list = new ArrayList<>(tiLeQuyDoiThuHangList.size());
        for (TiLeQuyDoiThuHang th : tiLeQuyDoiThuHangList) {
            list.add(tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(th));
        }
        return list;
    }
}
