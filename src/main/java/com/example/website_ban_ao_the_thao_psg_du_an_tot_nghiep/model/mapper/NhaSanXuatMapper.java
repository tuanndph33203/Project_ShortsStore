package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.NhaSanXuat;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhaSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NhaSanXuatResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NhaSanXuatMapper {

    NhaSanXuat nhaSanXuatResponseToNhaSanXuatEntity(NhaSanXuatResponse nhaSanXuatResponse);

    NhaSanXuatResponse nhaSanXuatEntityToNhaSanXuatResponse(NhaSanXuat nhaSanXuat);

    NhaSanXuat createNhaSanXuatRequestToNhaSanXuatEntity(CreateNhaSanXuatRequest createNhaSanXuatRequest);

    NhaSanXuat updateNhaSanXuatRequestToNhaSanXuatEntity(UpdateNhaSanXuatRequest updateNhaSanXuatRequest);

    List<NhaSanXuatResponse> listNhaSanXuatEntityToNhaSanXuatRespnse(List<NhaSanXuat> nhaSanXuatList);
}
