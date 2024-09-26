package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.DongSanPham;
import com.example.website_ban_ao_the_thao_ps.model.mapper.DongSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.DongSanPhamResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class DongSanPhamMapperImpl implements DongSanPhamMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public DongSanPham dongSanPhamResponseToDongSanPhamEntity(DongSanPhamResponse dongSanPhamResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DongSanPham dongSanPham = modelMapper.map(dongSanPhamResponse, DongSanPham.class);
        return dongSanPham;
    }

    @Override
    public DongSanPhamResponse dongSanPhamEntityToDongSanPhamResponse(DongSanPham dongSanPham) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DongSanPhamResponse dongSanPhamResponse = modelMapper.map(dongSanPham, DongSanPhamResponse.class);
        return dongSanPhamResponse;
    }

    @Override
    public DongSanPham createDongSanPhamRequestToDongSanPhamEntity(CreateDongSanPhamRequest createDongSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DongSanPham dongSanPham = modelMapper.map(createDongSanPhamRequest, DongSanPham.class);
        return dongSanPham;
    }

    @Override
    public DongSanPham updateDongSanPhamRequestToDongSanPhamEntity(UpdateDongSanPhamRequest updateDongSanPhamRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        DongSanPham dongSanPham = modelMapper.map(updateDongSanPhamRequest, DongSanPham.class);
        return dongSanPham;
    }

    @Override
    public List<DongSanPhamResponse> listDongSanPhamEntityToDongSanPhamResponse(List<DongSanPham> dongSanPhamList) {
        List<DongSanPhamResponse> list = new ArrayList<>(dongSanPhamList.size());
        for (DongSanPham th : dongSanPhamList) {
            list.add(dongSanPhamEntityToDongSanPhamResponse(th));
        }
        return list;
    }
}
