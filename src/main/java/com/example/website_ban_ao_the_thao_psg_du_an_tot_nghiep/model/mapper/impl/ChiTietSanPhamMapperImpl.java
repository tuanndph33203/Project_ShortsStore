package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChiTietSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChiTietSanPhamResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class ChiTietSanPhamMapperImpl implements ChiTietSanPhamMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ChiTietSanPham chiTietSanPhamResponseTochiTietSanPhamEntity(ChiTietSanPhamResponse chiTietSanPhamResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChiTietSanPham chiTietSanPham = modelMapper.map(chiTietSanPhamResponse, ChiTietSanPham.class);
        return chiTietSanPham;
    }

    @Override
    public ChiTietSanPhamResponse chiTietSanPhamEntityTochiTietSanPhamResponse(ChiTietSanPham chiTietSanPham) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChiTietSanPhamResponse chiTietSanPhamResponse = modelMapper.map(chiTietSanPham, ChiTietSanPhamResponse.class);
        return chiTietSanPhamResponse;
    }

    @Override
    public ChiTietSanPham createChiTietSanPhamRequestToChiTietSanPhamEntity(CreateChiTietSanPhamRequest createchiTietSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChiTietSanPham chiTietSanPham = modelMapper.map(createchiTietSanPhamRequest, ChiTietSanPham.class);
        return chiTietSanPham;
    }

    @Override
    public ChiTietSanPham updateChiTietSanPhamRequestToChiTietSanPhamEntity(UpdateChiTietSanPhamRequest updatechiTietSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChiTietSanPham chiTietSanPham = modelMapper.map(updatechiTietSanPhamRequest, ChiTietSanPham.class);
        return chiTietSanPham;
    }

    @Override
    public List<ChiTietSanPhamResponse> listchiTietSanPhamEntityTochiTietSanPhamResponse(List<ChiTietSanPham> chiTietSanPhamList) {
        List<ChiTietSanPhamResponse> list = new ArrayList<>(chiTietSanPhamList.size());
        for (ChiTietSanPham ctsp : chiTietSanPhamList) {
            list.add(chiTietSanPhamEntityTochiTietSanPhamResponse(ctsp));
        }
        return list;
    }
}
