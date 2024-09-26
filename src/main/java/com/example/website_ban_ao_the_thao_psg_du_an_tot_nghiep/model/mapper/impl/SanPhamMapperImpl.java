package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.SanPham;
import com.example.website_ban_ao_the_thao_ps.model.mapper.SanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.SanPhamResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class SanPhamMapperImpl implements SanPhamMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public SanPham sanPhamResponseToSanPhamEntity(SanPhamResponse sanPhamResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        SanPham sanPham = modelMapper.map(sanPhamResponse, SanPham.class);
        return sanPham;
    }

    @Override
    public SanPhamResponse sanPhamEntityToSanPhamResponse(SanPham sanPham) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        SanPhamResponse sanPhamResponse = modelMapper.map(sanPham, SanPhamResponse.class);
        return sanPhamResponse;
    }

    @Override
    public SanPham createSanPhamRequestToSanPhamEntity(CreateSanPhamRequest createSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        SanPham sanPham = modelMapper.map(createSanPhamRequest, SanPham.class);
        return sanPham;
    }

    @Override
    public SanPham updateSanPhamRequestToSanPhamEntity(UpdateSanPhamRequest updateSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        SanPham sanPham = modelMapper.map(updateSanPhamRequest, SanPham.class);
        return sanPham;
    }

    @Override
    public List<SanPhamResponse> listSanPhamEntityToSanPhamResponse(List<SanPham> sanPhamList) {
        List<SanPhamResponse> list = new ArrayList<>(sanPhamList.size());
        for (SanPham sp : sanPhamList
             ) {
            list.add(sanPhamEntityToSanPhamResponse(sp));
        }
        return list;
    }
}
