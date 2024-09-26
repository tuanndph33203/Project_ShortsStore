package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKhachHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KhachHangMapper {

    KhachHang khachHangResponseToKhachHangEntity(KhachHangResponse khachHangResponse);

    KhachHangResponse khachHangEntityToKhachHangResponse(KhachHang khachHang);
    KhachHang createKhachHangRequestToKhachHangEntity(CreateKhachHangRequest createKhachHangRequest);

    KhachHang updateKhachHangRequestToKhachHangEntity(UpdateKhachHangRequest updateKhachHangRequest);

    List<KhachHangResponse> listKhachHangEntityToKhachHangResponse(List<KhachHang> khachHangList);

}
