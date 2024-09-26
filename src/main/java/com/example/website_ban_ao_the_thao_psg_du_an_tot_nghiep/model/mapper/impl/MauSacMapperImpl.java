package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.MauSac;
import com.example.website_ban_ao_the_thao_ps.model.mapper.MauSacMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.MauSacResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class MauSacMapperImpl implements MauSacMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public MauSac mauSacResponseToMauSacEntity(MauSacResponse mauSacResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        MauSac mauSac = modelMapper.map(mauSacResponse, MauSac.class);
        return mauSac;
    }

    @Override
    public MauSacResponse mauSacEntityToMauSacResponse(MauSac mauSac) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        MauSacResponse mauSacResponse = modelMapper.map(mauSac, MauSacResponse.class);
        return mauSacResponse;
    }

    @Override
    public MauSac createMauSacRequestToMauSacEntity(CreateMauSacRequest createMauSacRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        MauSac mauSac = modelMapper.map(createMauSacRequest, MauSac.class);
        return mauSac;
    }

    @Override
    public MauSac updateMauSacRequestToMauSacEntity(UpdateMauSacRequest updateMauSacRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        MauSac mauSac = modelMapper.map(updateMauSacRequest, MauSac.class);
        return mauSac;
    }

    @Override
    public List<MauSacResponse> listMauSacEntityToMauSacResponse(List<MauSac> mauSacList) {
        List<MauSacResponse> list = new ArrayList<>(mauSacList.size());
        for (MauSac ms : mauSacList) {
            list.add(mauSacEntityToMauSacResponse(ms));
        }
        return list;
    }
}
