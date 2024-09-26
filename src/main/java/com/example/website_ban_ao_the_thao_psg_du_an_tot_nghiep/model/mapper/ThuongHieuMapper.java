package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.ThuongHieu;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuongHieuResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThuongHieuMapper {

    ThuongHieu thuongHieuResponseToThuongHieuEntity(ThuongHieuResponse thuongHieuResponse);

    ThuongHieuResponse thuongHieuEntityToThuongHieuResponse(ThuongHieu thuongHieu);

    ThuongHieu createThuongHieuRequestToThuongHieuEntity(CreateThuongHieuRequest createThuongHieuRequest);

    ThuongHieu updateThuongHieuRequestToThuongHieuEntity(UpdateThuongHieuRequest updateThuongHieuRequest);

    List<ThuongHieuResponse> listThuongHieuEntityToThuongHieuResponse(List<ThuongHieu> thuongHieuList);
}
