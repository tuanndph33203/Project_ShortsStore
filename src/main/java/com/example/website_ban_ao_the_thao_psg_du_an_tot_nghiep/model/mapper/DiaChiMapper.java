package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.DiaChi;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDiaChiRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDiaChiRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.DiaChiResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiaChiMapper {

    DiaChi diaChiResponseToDiaChiEntity(DiaChiResponse diaChiResponse);

    DiaChiResponse diaChiEntityToDiaChiResponse(DiaChi diaChi);

    DiaChi createDiaChiRequestToDiaChiEntity(CreateDiaChiRequest createDiaChiRequest);

    DiaChi updateDiaChiRequestToDiaChiEntity(UpdateDiaChiRequest updateDiaChiRequest);

    List<DiaChiResponse> listDiaChiEntityToDiaChiResponse(List<DiaChi> diaChiList);
}
