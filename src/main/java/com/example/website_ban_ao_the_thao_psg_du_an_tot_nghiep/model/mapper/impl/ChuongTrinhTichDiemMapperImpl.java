package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChuongTrinhTichDiemMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChuongTrinhTichDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChuongTrinhTichDiemResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class ChuongTrinhTichDiemMapperImpl implements ChuongTrinhTichDiemMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ChuongTrinhTichDiem chuongTrinhTichDiemResponseToChuongTrinhTichDiemEntity(ChuongTrinhTichDiemResponse chuongTrinhTichDiemResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChuongTrinhTichDiem chuongTrinhTichDiem = modelMapper.map(chuongTrinhTichDiemResponse, ChuongTrinhTichDiem.class);
        return chuongTrinhTichDiem;
    }

    @Override
    public ChuongTrinhTichDiemResponse chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(ChuongTrinhTichDiem chuongTrinhTichDiem) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChuongTrinhTichDiemResponse chuongTrinhTichDiemResponse = modelMapper.map(chuongTrinhTichDiem, ChuongTrinhTichDiemResponse.class);
        return chuongTrinhTichDiemResponse;
    }

    @Override
    public ChuongTrinhTichDiem createChuongTrinhTichDiemRequestToChuongTrinhTichDiemEntity(CreateChuongTrinhTichDiemRequest createChuongTrinhTichDiemRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChuongTrinhTichDiem chuongTrinhTichDiem = modelMapper.map(createChuongTrinhTichDiemRequest, ChuongTrinhTichDiem.class);
        return chuongTrinhTichDiem;
    }

    @Override
    public ChuongTrinhTichDiem updateChuongTrinhTichDiemRequestToChuongTrinhTichDiemEntity(UpdateChuongTrinhTichDiemRequest updateChuongTrinhTichDiemRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        ChuongTrinhTichDiem chuongTrinhTichDiem = modelMapper.map(updateChuongTrinhTichDiemRequest, ChuongTrinhTichDiem.class);
        return chuongTrinhTichDiem;
    }

    @Override
    public List<ChuongTrinhTichDiemResponse> listChuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(List<ChuongTrinhTichDiem> chuongTrinhTichDiemList) {
        List<ChuongTrinhTichDiemResponse> list = new ArrayList<>(chuongTrinhTichDiemList.size());
        for (ChuongTrinhTichDiem th : chuongTrinhTichDiemList) {
            list.add(chuongTrinhTichDiemEntityToChuongTrinhTichDiemResponse(th));
        }
        return list;
    }
}
