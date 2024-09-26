package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.TiLeQuyDoiThuHangResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TiLeQuyDoiThuHangMapper {

    TiLeQuyDoiThuHang tiLeQuyDoiThuHangResponseToTiLeQuyDoiThuHangEntity(TiLeQuyDoiThuHangResponse tiLeQuyDoiThuHangResponse);

    TiLeQuyDoiThuHangResponse tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(TiLeQuyDoiThuHang tiLeQuyDoiThuHang);

    TiLeQuyDoiThuHang createTiLeQuyDoiThuHangRequestToTiLeQuyDoiThuHangEntity(CreateTiLeQuyDoiThuHangRequest createTiLeQuyDoiThuHangRequest);

    TiLeQuyDoiThuHang updateTiLeQuyDoiThuHangRequestToTiLeQuyDoiThuHangEntity(UpdateTiLeQuyDoiThuHangRequest updateTiLeQuyDoiThuHangRequest);

    List<TiLeQuyDoiThuHangResponse> listTiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(List<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangList);
}
