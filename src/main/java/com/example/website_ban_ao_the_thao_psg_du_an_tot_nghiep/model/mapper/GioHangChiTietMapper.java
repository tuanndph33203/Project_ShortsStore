package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.GioHangChiTiet;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateGioHangChiTietRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateGioHangChiTietRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.GioHangChiTietResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GioHangChiTietMapper {
    GioHangChiTiet gioHangChiTietResponseToGioHangChiTietEntity(GioHangChiTietResponse gioHangChiTietResponse);

    GioHangChiTietResponse gioHangChiTietEntityToGioHangChiTietResponse(GioHangChiTiet gioHangChiTiet);

    GioHangChiTiet createGioHangChiTietRequestToGioHangChiTietEntity(CreateGioHangChiTietRequest createGioHangChiTietRequest);

    GioHangChiTiet updateGioHangChiTietRequestToGioHangChiTietEntity(UpdateGioHangChiTietRequest updateGioHangChiTietRequest);

    List<GioHangChiTietResponse> listGioHangChiTietEntityToGioHangChiTietResponse(List<GioHangChiTiet> listGioHangChiTiet);
}
