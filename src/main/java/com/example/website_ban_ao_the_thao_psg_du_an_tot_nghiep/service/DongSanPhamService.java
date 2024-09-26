package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.DongSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DongSanPhamService {
    Page<DongSanPhamResponse> pageDongSanPham(Integer pageNo, Integer size);

    Page<DongSanPhamResponse> pageSearchDongSanPham(Integer pageNo,Integer size,String search);

    List<DongSanPhamResponse> listDongSanPhamResponse();

    DongSanPhamResponse createDongSanPham(CreateDongSanPhamRequest createDongSanPhamRequest);

    DongSanPhamResponse updateDongSanPham(UpdateDongSanPhamRequest updateDongSanPhamRequest, Integer id);

    DongSanPhamResponse getOneDongSanPham(Integer id);

    void removeOrRevertDongSanPham(String trangThaiDongSanPham,Integer id);
}
