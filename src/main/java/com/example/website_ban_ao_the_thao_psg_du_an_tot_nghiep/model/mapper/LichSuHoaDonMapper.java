package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.LichSuHoaDon;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateLichSuHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateLichSuHoaDonRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.LichSuHoaDonResponse;

import java.util.List;

public interface LichSuHoaDonMapper {
    LichSuHoaDon lichSuHoaDonResponseToLichSuHoaDonEntity(LichSuHoaDonResponse lichSuHoaDonResponse);

    LichSuHoaDonResponse lichSuHoaDonEntityToLichSuHoaDonResponse(LichSuHoaDon lichSuHoaDon);

    LichSuHoaDon createLichSuHoaDonRequestToLichSuHoaDonEntity(CreateLichSuHoaDonRequest createLichSuHoaDonRequest);

    LichSuHoaDon updateLichSuHoaDonRequestToLichSuHoaDonEntity(UpdateLichSuHoaDonRequest updateLichSuHoaDonRequest);

    List<LichSuHoaDonResponse> listLichSuHoaDonEntityToLichSuHoaDonResponse(List<LichSuHoaDon> lichSuHoaDonList);
}
