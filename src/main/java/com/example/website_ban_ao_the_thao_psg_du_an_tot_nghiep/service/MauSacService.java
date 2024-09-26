package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MauSacService {
    Page<MauSacResponse> pageMauSac(Integer pageNo, Integer size);

    Page<MauSacResponse> pageSearchMauSac(Integer pageNo,Integer size,String search);

    List<MauSacResponse> listMauSacResponse();

    MauSacResponse createMauSac(CreateMauSacRequest createMauSacRequest);

    MauSacResponse updateMauSac(UpdateMauSacRequest updateMauSacRequest, Integer id);

    MauSacResponse getOneMauSac(Integer id);

    void removeOrRevertMauSac(String trangThaiMauSac,Integer id);
}
