package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.CauThu;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CauThuResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CauThuMapper {

    CauThu cauThuResponseToCauThuEntity(CauThuResponse cauThuResponse);

    CauThuResponse cauThuEntityToCauThuResponse(CauThu cauThu);

    CauThu createCauThuRequestToCauThuEntity(CreateCauThuRequest createCauThuRequest);

    CauThu updateCauThuRequestToCauThuEntity(UpdateCauThuRequest updateCauThuRequest);

    List<CauThuResponse> listCauThuEntityToCauThuResponse(List<CauThu> cauThuList);
}
