package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChiTietSanPhamResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChiTietSanPhamMapper {
    
    ChiTietSanPham chiTietSanPhamResponseTochiTietSanPhamEntity(ChiTietSanPhamResponse chiTietSanPhamResponse);
    
    ChiTietSanPhamResponse chiTietSanPhamEntityTochiTietSanPhamResponse(ChiTietSanPham chiTietSanPham);
    
    ChiTietSanPham createChiTietSanPhamRequestToChiTietSanPhamEntity(CreateChiTietSanPhamRequest createchiTietSanPhamRequest);
    
    ChiTietSanPham updateChiTietSanPhamRequestToChiTietSanPhamEntity(UpdateChiTietSanPhamRequest updatechiTietSanPhamRequest);
    
    List<ChiTietSanPhamResponse> listchiTietSanPhamEntityTochiTietSanPhamResponse(List<ChiTietSanPham> chiTietSanPhamList);
}
