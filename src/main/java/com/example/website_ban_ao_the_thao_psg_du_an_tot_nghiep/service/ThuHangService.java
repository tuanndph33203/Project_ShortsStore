package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ThuHang;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ThuHangService {
    Page<ThuHangResponse> pageThuHang(Integer pageNo, Integer size);

    Page<ThuHangResponse> pageSearchThuHang(Integer pageNo, Integer size, String search, String trangThaiThuHang);

    List<ThuHangResponse> listThuHangResponse();

    byte[] exportExcelThuHang() throws IOException;

    ThuHangResponse createThuHang(CreateThuHangRequest createThuHangRequest);

    ThuHangResponse updateThuHang(UpdateThuHangRequest updateThuHangRequest, Integer id);

    ThuHangResponse getOneThuHang(Integer id);

    void removeOrRevertThuHang(String trangThaiThuHang,Integer id);

    List<Object[]> getAllList();
}
