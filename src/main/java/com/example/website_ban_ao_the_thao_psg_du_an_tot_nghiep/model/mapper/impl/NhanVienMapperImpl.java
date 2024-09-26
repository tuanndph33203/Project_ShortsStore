package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.model.mapper.NhanVienMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NhanVienResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class NhanVienMapperImpl implements NhanVienMapper {
    @Autowired
    ModelMapper modelMapper;
    @Override
    public NhanVien nhanVienResponseToNhanVienEntity(NhanVienResponse nhanVienResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhanVien nhanVien =modelMapper.map(nhanVienResponse, NhanVien.class);
        return nhanVien;
    }

    @Override
    public NhanVienResponse nhanVienEntityToNhanVienResponse(NhanVien nhanVien) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhanVienResponse nhanVienResponse =modelMapper.map(nhanVien, NhanVienResponse.class);
        return nhanVienResponse;
    }

    @Override
    public NhanVien createNhanVienRequestToNhanVienEntity(CreateNhanVienRequest createNhanVienRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhanVien nhanVien =modelMapper.map(createNhanVienRequest, NhanVien.class);
        return nhanVien;
    }

    @Override
    public NhanVien updateNhanVienRequestToNhanVienEntity(UpdateNhanVienRequest updateNhanVienRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        NhanVien nhanVien =modelMapper.map(updateNhanVienRequest, NhanVien.class);
        return nhanVien;
    }



    @Override
    public List<NhanVienResponse> listNhanVienEntityToNhanVienResponse(List<NhanVien> nhanVienList) {
        List<NhanVienResponse> list = new ArrayList<>(nhanVienList.size());
        for (NhanVien th : nhanVienList) {
            list.add(nhanVienEntityToNhanVienResponse(th));
        }
        return list;
    }
    }

