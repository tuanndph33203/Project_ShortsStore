package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.LoaiSanPham;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.LoaiSanPhamResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoaiSanPhamMapper {

    LoaiSanPham loaiSanPhamResponseToLoaiSanPhamEntity(LoaiSanPhamResponse loaiSanPhamResponse);

    LoaiSanPhamResponse loaiSanPhamEntityToLoaiSanPhamResponse(LoaiSanPham loaiSanPham);

    LoaiSanPham createLoaiSanPhamRequestToLoaiSanPhamEntity(CreateLoaiSanPhamRequest createLoaiSanPhamRequest);

    LoaiSanPham updateLoaiSanPhamRequestToLoaiSanPhamEntity(UpdateLoaiSanPhamRequest updateLoaiSanPhamRequest);

    List<LoaiSanPhamResponse> listLoaiSanPhamEntityToLoaiSanPhamResponses(List<LoaiSanPham> loaiSanPhamList);
}
