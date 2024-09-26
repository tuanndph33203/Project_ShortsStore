package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.NhaSanXuat;
import com.example.website_ban_ao_the_thao_ps.model.mapper.NhaSanXuatMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NhaSanXuatResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class NhaSanXuatMapperImpl implements NhaSanXuatMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public NhaSanXuat nhaSanXuatResponseToNhaSanXuatEntity(NhaSanXuatResponse nhaSanXuatResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhaSanXuat nhaSanXuat = modelMapper.map(nhaSanXuatResponse, NhaSanXuat.class);
        return nhaSanXuat;
    }

    @Override
    public NhaSanXuatResponse nhaSanXuatEntityToNhaSanXuatResponse(NhaSanXuat nhaSanXuat) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhaSanXuatResponse nhaSanXuatResponse = modelMapper.map(nhaSanXuat, NhaSanXuatResponse.class);
        return nhaSanXuatResponse;
    }

    @Override
    public NhaSanXuat createNhaSanXuatRequestToNhaSanXuatEntity(CreateNhaSanXuatRequest createNhaSanXuatRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhaSanXuat nhaSanXuat = modelMapper.map(createNhaSanXuatRequest, NhaSanXuat.class);
        return nhaSanXuat;
    }

    @Override
    public NhaSanXuat updateNhaSanXuatRequestToNhaSanXuatEntity(UpdateNhaSanXuatRequest updateNhaSanXuatRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhaSanXuat nhaSanXuat = modelMapper.map(updateNhaSanXuatRequest, NhaSanXuat.class);
        return nhaSanXuat;
    }

    @Override
    public List<NhaSanXuatResponse> listNhaSanXuatEntityToNhaSanXuatRespnse(List<NhaSanXuat> nhaSanXuatList) {
        List<NhaSanXuatResponse> list = new ArrayList<>(nhaSanXuatList.size());
        for (NhaSanXuat nsx : nhaSanXuatList) {
            list.add(nhaSanXuatEntityToNhaSanXuatResponse(nsx));
        }
        return list;
    }
}
