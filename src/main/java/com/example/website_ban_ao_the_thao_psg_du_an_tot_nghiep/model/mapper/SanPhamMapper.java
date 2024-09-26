package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.SanPham;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.SanPhamResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SanPhamMapper {

    SanPham sanPhamResponseToSanPhamEntity(SanPhamResponse sanPhamResponse);

    SanPhamResponse sanPhamEntityToSanPhamResponse(SanPham sanPham);

    SanPham createSanPhamRequestToSanPhamEntity(CreateSanPhamRequest createSanPhamRequest);

    SanPham updateSanPhamRequestToSanPhamEntity(UpdateSanPhamRequest updateSanPhamRequest);

    List<SanPhamResponse> listSanPhamEntityToSanPhamResponse(List<SanPham> sanPhamList);
}
