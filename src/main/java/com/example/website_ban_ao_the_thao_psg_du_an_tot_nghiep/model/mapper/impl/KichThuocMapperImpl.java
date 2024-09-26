package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.KichThuoc;
import com.example.website_ban_ao_the_thao_ps.model.mapper.KichThuocMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKichThuocRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKichThuocRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.KichThuocResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class KichThuocMapperImpl implements KichThuocMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public KichThuoc kichThuocResponseToKichThuocEntity(KichThuocResponse kichThuocResponse) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KichThuoc kichThuoc = modelMapper.map(kichThuocResponse, KichThuoc.class);
        return kichThuoc;
    }

    @Override
    public KichThuocResponse kichThuocEntityToKichThuocResponse(KichThuoc kichThuoc) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KichThuocResponse kichThuocResponse = modelMapper.map(kichThuoc, KichThuocResponse.class);
        return kichThuocResponse;
    }

    @Override
    public KichThuoc createKichThuocRequestToKichThuocEntity(CreateKichThuocRequest createKichThuocRequest) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KichThuoc kichThuoc = modelMapper.map(createKichThuocRequest, KichThuoc.class);
        return kichThuoc;
    }

    @Override
    public KichThuoc updateKichThuocRequestToKichThuocEntity(UpdateKichThuocRequest updateKichThuocRequest) {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        KichThuoc kichThuoc = modelMapper.map(updateKichThuocRequest, KichThuoc.class);
        return kichThuoc;
    }

    @Override
    public List<KichThuocResponse> listKichThuocEntityToKichThuocResponses(List<KichThuoc> kichThuocList) {
        List<KichThuocResponse> list = new ArrayList<>(kichThuocList.size());
        for (KichThuoc kt : kichThuocList
             ) {
            list.add(kichThuocEntityToKichThuocResponse(kt));
        }
        return list;
    }
}
