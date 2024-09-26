package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.HoaDonResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HoaDonMapper {
    HoaDon hoaDonResponseToHoaDonEntity(HoaDonResponse hoaDonResponse);

    HoaDonResponse hoaDonEntityToHoaDonResponse(HoaDon hoaDon);

    HoaDon createHoaDonRequestToHoaDonEntity(CreateHoaDonRequest createHoaDonRequest);

    HoaDon updateHoaDonRequestToHoaDonEntity(UpdateHoaDonRequest updateHoaDonRequest);

    List<HoaDonResponse> listHoaDonEntityToHoaDonResponse(List<HoaDon> listHoaDon);
}
