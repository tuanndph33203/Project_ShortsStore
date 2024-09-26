package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateVaiTroRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateVaiTroRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.VaiTroResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VaiTroService {
    Page<VaiTroResponse> pageVaiTro(Integer pageNo, Integer size);

    Page<VaiTroResponse> pageSearchVaiTro(Integer pageNo,Integer size,String search);

    List<VaiTroResponse> listVaiTroResponse();

    VaiTroResponse createVaiTro(CreateVaiTroRequest createVaiTroRequest);

    VaiTroResponse updateVaiTro(UpdateVaiTroRequest updateVaiTroRequest, Integer id);

    VaiTroResponse getOneVaiTro(Integer id);

    void removeOrRevertVaiTro(String trangThaiVaiTro,Integer id);
}
