package com.example.website_ban_ao_the_thao_ps.model.mapper.impl;

import com.example.website_ban_ao_the_thao_ps.entity.QuyDoiDiem;
import com.example.website_ban_ao_the_thao_ps.model.mapper.QuyDoiDiemMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDoiDiemResponse;
import jakarta.annotation.Generated;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2023-03-29T01:09:18+0700", comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (JetBrains s.r.o.)")
public class QuyDoiDiemMapperImpl implements QuyDoiDiemMapper {
    @Autowired
    ModelMapper modelMapper;
    @Override
    public QuyDoiDiem quyDoiDiemResponseToQuyDoiDiemEntity(QuyDoiDiemResponse quyDoiDiemResponse) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDoiDiem quyDoiDiem = modelMapper.map(quyDoiDiemResponse, QuyDoiDiem.class);
        return quyDoiDiem;
    }

    @Override
    public QuyDoiDiemResponse quyDoiDiemEntityToQuyDoiDiemResponse(QuyDoiDiem quyDoiDiem) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDoiDiemResponse quyDoiDiemResponse = modelMapper.map(quyDoiDiem, QuyDoiDiemResponse.class);
        return quyDoiDiemResponse;
    }

    @Override
    public QuyDoiDiem createQuyDoiDiemRequestToQuyDoiDiemEntity(CreateQuyDoiDiemRequest createQuyDoiDiemRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDoiDiem quyDoiDiem = modelMapper.map(createQuyDoiDiemRequest, QuyDoiDiem.class);
        return quyDoiDiem;
    }

    @Override
    public QuyDoiDiem updateQuyDoiDiemRequestToQuyDoiDiemEntity(UpdateQuyDoiDiemRequest updateQuyDoiDiemRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        QuyDoiDiem quyDoiDiem = modelMapper.map(updateQuyDoiDiemRequest, QuyDoiDiem.class);
        return quyDoiDiem;
    }

    @Override
    public List<QuyDoiDiemResponse> listQuyDoiDiemEntityToQuyDoiDiemResponse(List<QuyDoiDiem> quyDoiDiemList) {
        List<QuyDoiDiemResponse> list = new ArrayList<>(quyDoiDiemList.size());
        for (QuyDoiDiem th : quyDoiDiemList) {
            list.add(quyDoiDiemEntityToQuyDoiDiemResponse(th));
        }
        return list;
    }
}
