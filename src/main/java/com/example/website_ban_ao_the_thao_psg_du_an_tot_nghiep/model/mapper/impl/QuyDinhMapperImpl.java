package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.QuyDinh;
import com.example.website_ban_ao_the_thao_ps.model.mapper.QuyDinhMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDinhResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
@Component
public class QuyDinhMapperImpl implements QuyDinhMapper {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public QuyDinh quyDinhResponseToQuyDinhEntity(QuyDinhResponse quyDinhResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDinh quyDinh = modelMapper.map(quyDinhResponse, QuyDinh.class);
        return quyDinh;
    }

    @Override
    public QuyDinhResponse quyDinhEntityToQuyDinhResponse(QuyDinh quyDinh) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDinhResponse quyDinhResponse = modelMapper.map(quyDinh, QuyDinhResponse.class);
        return quyDinhResponse;
    }

    @Override
    public QuyDinh createQuyDinhRequestToQuyDinhEntity(CreateQuyDinhRequest createQuyDinhRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDinh quyDinh = modelMapper.map(createQuyDinhRequest, QuyDinh.class);
        return quyDinh;
    }

    @Override
    public QuyDinh updateQuyDinhRequestToQuyDinhEntity(UpdateQuyDinhRequest updateQuyDinhRequestQuyDinhRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDinh quyDinh = modelMapper.map(updateQuyDinhRequestQuyDinhRequest, QuyDinh.class);
        return quyDinh;
    }

    @Override
    public List<QuyDinhResponse>listQuyDinhEntityToQuyDinhResponse(List<QuyDinh> quyDinhList) {
        List<QuyDinhResponse> list = new ArrayList<>(quyDinhList.size());
        for (QuyDinh qd : quyDinhList
             ) {
            list.add(quyDinhEntityToQuyDinhResponse(qd));
        }
        return list;
    }
}
