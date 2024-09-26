package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CauThuResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CauThuService {
    Page<CauThuResponse> pageCauThu(Integer pageNo, Integer size);

    Page<CauThuResponse> pageSearchCauThu(Integer pageNo,Integer size,String search);

    List<CauThuResponse> listCauThuResponse();

    CauThuResponse createCauThu(CreateCauThuRequest createCauThuRequest);

    CauThuResponse updateCauThu(UpdateCauThuRequest updateCauThuRequest, Integer id);

    CauThuResponse getOneCauThu(Integer id);

    void removeOrRevertCauThu(String trangThaiCauThu,Integer id);
}
