package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuHangResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThuHangMapper {

    ThuHang thuHangResponseToThuHangEntity(ThuHangResponse thuHangResponse);

    ThuHangResponse thuHangEntiyToThuHangResponse(ThuHang thuHang);

    ThuHang createThuHangRequestToThuHangEntity(CreateThuHangRequest createThuHangRequest);

    ThuHang updateThuHangRequestToThuHangEntity(UpdateThuHangRequest updateThuHangRequest);

    List<ThuHangResponse> listThuHangEntityToThuHangResponse(List<ThuHang> thuHangMapperList);
}
