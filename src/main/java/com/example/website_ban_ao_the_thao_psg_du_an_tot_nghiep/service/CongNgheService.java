package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CongNgheResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CongNgheService {
    Page<CongNgheResponse> pageCongNghe(Integer pageNo, Integer size);

    Page<CongNgheResponse> pageSearchCongNghe(Integer pageNo,Integer size,String search);

    List<CongNgheResponse> listCongNgheResponse();

    CongNgheResponse createCongNghe(CreateCongNgheRequest createCongNgheRequest);

    CongNgheResponse updateCongNghe(UpdateCongNgheRequest updateCongNgheRequest, Integer id);

    CongNgheResponse getOneCongNghe(Integer id);

    void removeOrRevertCongNghe(String trangThaiCongNghe,Integer id);
}
