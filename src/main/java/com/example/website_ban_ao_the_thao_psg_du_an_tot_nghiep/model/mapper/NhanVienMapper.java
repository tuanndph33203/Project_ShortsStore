package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.NhanVien;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNhanVienRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NhanVienResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NhanVienMapper {
    NhanVien nhanVienResponseToNhanVienEntity(NhanVienResponse nhanVienResponse);

    NhanVienResponse nhanVienEntityToNhanVienResponse(NhanVien nhanVien);

    NhanVien createNhanVienRequestToNhanVienEntity(CreateNhanVienRequest createNhanVienRequest);

    NhanVien updateNhanVienRequestToNhanVienEntity(UpdateNhanVienRequest updateNhanVienRequest);

    List<NhanVienResponse>listNhanVienEntityToNhanVienResponse(List<NhanVien> nhanVienList);

}
