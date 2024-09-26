package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateKichThuocRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateKichThuocRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.KichThuocResponse;
import com.example.website_ban_ao_the_thao_ps.model.response.LoaiSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KichThuocService {
    Page<KichThuocResponse> pageKichThuoc(Integer pageNo, Integer size);

    List<KichThuocResponse> listKichThuocResponse();

    Page<KichThuocResponse> pageSearchKichThuoc(Integer pageNo,Integer size,String search);

    KichThuocResponse createKichThuoc(CreateKichThuocRequest createKichThuocRequest);

    KichThuocResponse updateKichThuoc(UpdateKichThuocRequest updateKichThuocRequest, Integer id);

    KichThuocResponse getOneKichThuoc(Integer id);

    void removeOrRevertKichThuoc(String trangThaiKichThuoc,Integer id);

//    Boolean checkNameKichThuoc(String name);
}
