package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDoiDiemResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuyDoiDiemService {

    List<QuyDoiDiemResponse> listQuyDoiDiemResponse();

    QuyDoiDiemResponse createQuyDoiDiem(CreateQuyDoiDiemRequest createQuyDoiDiemRequest);

    QuyDoiDiemResponse updateQuyDoiDiem(UpdateQuyDoiDiemRequest updateQuyDoiDiemRequest, Integer id);

    QuyDoiDiemResponse getOneQuyDoiDiem(Integer id);

    void removeOrRevertQuyDoiDiem(String trangThaiQuyDoiDiem,Integer id);
}
