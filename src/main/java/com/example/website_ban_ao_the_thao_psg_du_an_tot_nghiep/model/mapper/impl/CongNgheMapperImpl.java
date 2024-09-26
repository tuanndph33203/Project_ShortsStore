package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.CongNghe;
import com.example.website_ban_ao_the_thao_ps.model.mapper.CongNgheMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CongNgheResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class CongNgheMapperImpl implements CongNgheMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CongNghe congNgheResponseToCongNgheEntity(CongNgheResponse congNgheResponse) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CongNghe congNghe = this.modelMapper.map(congNgheResponse, CongNghe.class);
        return congNghe;
    }

    @Override
    public CongNgheResponse congNgheEntityToCongNgheResponse(CongNghe congNghe) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CongNgheResponse congNgheResponse = this.modelMapper.map(congNghe, CongNgheResponse.class);
        return congNgheResponse;
    }

    @Override
    public CongNghe createCongNgheRequestToCongNgheEntity(CreateCongNgheRequest createCongNgheRequest) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CongNghe congNghe = this.modelMapper.map(createCongNgheRequest, CongNghe.class);
        return congNghe;
    }

    @Override
    public CongNghe updateCongNgheRequestToCongNgheEntity(UpdateCongNgheRequest updateCongNgheRequest) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CongNghe congNghe = this.modelMapper.map(updateCongNgheRequest, CongNghe.class);
        return congNghe;
    }

    @Override
    public List<CongNgheResponse> listCongNgheEntityToCongNgheResponses(List<CongNghe> congNgheList) {
        List<CongNgheResponse> list = new ArrayList<>(congNgheList.size());
        for (CongNghe cn : congNgheList
             ) {
            list.add(congNgheEntityToCongNgheResponse(cn));
        }
        return list;
    }
}
