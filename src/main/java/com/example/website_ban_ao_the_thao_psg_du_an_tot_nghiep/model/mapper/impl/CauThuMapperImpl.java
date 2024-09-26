package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.CauThu;
import com.example.website_ban_ao_the_thao_ps.model.mapper.CauThuMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CauThuResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class CauThuMapperImpl implements CauThuMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CauThu cauThuResponseToCauThuEntity(CauThuResponse cauThuResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CauThu cauThu = modelMapper.map(cauThuResponse, CauThu.class);
        return cauThu;
    }

    @Override
    public CauThuResponse cauThuEntityToCauThuResponse(CauThu cauThu) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CauThuResponse cauThuResponse = modelMapper.map(cauThu, CauThuResponse.class);
        return cauThuResponse;
    }

    @Override
    public CauThu createCauThuRequestToCauThuEntity(CreateCauThuRequest createCauThuRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CauThu cauThu = modelMapper.map(createCauThuRequest, CauThu.class);
        return cauThu;
    }

    @Override
    public CauThu updateCauThuRequestToCauThuEntity(UpdateCauThuRequest updateCauThuRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CauThu cauThu = modelMapper.map(updateCauThuRequest, CauThu.class);
        return cauThu;
    }

    @Override
    public List<CauThuResponse> listCauThuEntityToCauThuResponse(List<CauThu> cauThuList) {
        List<CauThuResponse> list = new ArrayList<>(cauThuList.size());
        for (CauThu cl : cauThuList) {
            list.add(cauThuEntityToCauThuResponse(cl));
        }
        return list;
    }
}
