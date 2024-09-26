package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.NuocSanXuat;
import com.example.website_ban_ao_the_thao_ps.model.mapper.NuocSanXuatMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NuocSanXuatResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class NuocSanXuatMapperImpl implements NuocSanXuatMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public NuocSanXuat nuocSanXuatResponseToNuocSanXuatEntity(NuocSanXuatResponse nuocSanXuatResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NuocSanXuat nuocSanXuat = modelMapper.map(nuocSanXuatResponse, NuocSanXuat.class);
        return nuocSanXuat;
    }

    @Override
    public NuocSanXuatResponse nuocSanXuatEntityToNuocSanXuatResponse(NuocSanXuat nuocSanXuat) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NuocSanXuatResponse nuocSanXuatResponse = modelMapper.map(nuocSanXuat, NuocSanXuatResponse.class);
        return nuocSanXuatResponse;
    }

    @Override
    public NuocSanXuat createNuocSanXuatRequestToNuocSanXuatEntity(CreateNuocSanXuatRequest createNuocSanXuatRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NuocSanXuat nuocSanXuat = modelMapper.map(createNuocSanXuatRequest, NuocSanXuat.class);
        return nuocSanXuat;
    }

    @Override
    public NuocSanXuat updateNuocSanXuatRequestToNuocSanXuatEntity(UpdateNuocSanXuatRequest updateNuocSanXuatRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NuocSanXuat nuocSanXuat = modelMapper.map(updateNuocSanXuatRequest, NuocSanXuat.class);
        return nuocSanXuat;
    }

    @Override
    public List<NuocSanXuatResponse> listNuocSanXuatEntityToNuocSanXuatResponses(List<NuocSanXuat> nuocSanXuatList) {
        List<NuocSanXuatResponse> list = new ArrayList<>(nuocSanXuatList.size());
        for (NuocSanXuat nsx : nuocSanXuatList
             ) {
            list.add(nuocSanXuatEntityToNuocSanXuatResponse(nsx));
        }
        return list;
    }
}
