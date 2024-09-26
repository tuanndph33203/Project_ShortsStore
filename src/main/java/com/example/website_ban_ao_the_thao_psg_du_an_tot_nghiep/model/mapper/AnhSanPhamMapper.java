package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.AnhSanPham;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateAnhSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateAnhSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.AnhSanPhamResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnhSanPhamMapper {

    AnhSanPham anhSanPhamResponseToAnhSanPhamEntity(AnhSanPhamResponse anhSanPhamResponse);

    AnhSanPhamResponse anhSanPhamEntityToAnhSanPhamResponse(AnhSanPham anhSanPham);

    AnhSanPham createAnhSanPhamRequestToAnhSanPhamEntity(CreateAnhSanPhamRequest createAnhSanPhamRequest);

    AnhSanPham updateAnhSanPhamRequestToAnhSanPhamEntity(UpdateAnhSanPhamRequest updateAnhSanPhamRequest);

    List<AnhSanPhamResponse> listAnhSanPhamEntityToAnhSanPhamResponse(List<AnhSanPham> anhSanPhamList);
}
