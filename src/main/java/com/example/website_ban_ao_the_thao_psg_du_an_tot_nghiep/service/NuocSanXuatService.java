package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateNuocSanXuatRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.NuocSanXuatResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NuocSanXuatService {
    Page<NuocSanXuatResponse> pageNuocSanXuat(Integer pageNo, Integer size);

    Page<NuocSanXuatResponse> pageSearchNuocSanXuat(Integer pageNo,Integer size,String search);

    List<NuocSanXuatResponse> listNuocSanXuatResponse();

    NuocSanXuatResponse createNuocSanXuat(CreateNuocSanXuatRequest createNuocSanXuatRequest);

    NuocSanXuatResponse updateNuocSanXuat(UpdateNuocSanXuatRequest updateNuocSanXuatRequest, Integer id);

    NuocSanXuatResponse getOneNuocSanXuat(Integer id);

    void removeOrRevertNuocSanXuat(String trangThaiNuocSanXuat,Integer id);
    
}
