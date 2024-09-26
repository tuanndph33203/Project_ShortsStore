package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.LoaiSanPham;
import com.example.website_ban_ao_the_thao_ps.model.mapper.LoaiSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.LoaiSanPhamResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class LoaiSanPhamMapperImpl implements LoaiSanPhamMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public LoaiSanPham loaiSanPhamResponseToLoaiSanPhamEntity(LoaiSanPhamResponse loaiSanPhamResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LoaiSanPham loaiSanPham = modelMapper.map(loaiSanPhamResponse, LoaiSanPham.class);
        return loaiSanPham;
    }

    @Override
    public LoaiSanPhamResponse loaiSanPhamEntityToLoaiSanPhamResponse(LoaiSanPham loaiSanPham) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LoaiSanPhamResponse loaiSanPhamResponse = modelMapper.map(loaiSanPham, LoaiSanPhamResponse.class);
        return loaiSanPhamResponse;
    }

    @Override
    public LoaiSanPham createLoaiSanPhamRequestToLoaiSanPhamEntity(CreateLoaiSanPhamRequest createLoaiSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LoaiSanPham loaiSanPham = modelMapper.map(createLoaiSanPhamRequest, LoaiSanPham.class);
        return loaiSanPham;
    }

    @Override
    public LoaiSanPham updateLoaiSanPhamRequestToLoaiSanPhamEntity(UpdateLoaiSanPhamRequest updateLoaiSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        LoaiSanPham loaiSanPham = modelMapper.map(updateLoaiSanPhamRequest, LoaiSanPham.class);
        return loaiSanPham;
    }

    @Override
    public List<LoaiSanPhamResponse> listLoaiSanPhamEntityToLoaiSanPhamResponses(List<LoaiSanPham> loaiSanPhamList) {
        List<LoaiSanPhamResponse> list = new ArrayList<>(loaiSanPhamList.size());
        for (LoaiSanPham lsp : loaiSanPhamList
             ) {
            list.add(loaiSanPhamEntityToLoaiSanPhamResponse(lsp));
        }
        return list;
    }
}
