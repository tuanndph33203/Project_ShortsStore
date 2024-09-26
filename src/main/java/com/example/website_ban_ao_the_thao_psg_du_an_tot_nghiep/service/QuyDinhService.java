package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDinhResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuyDinhService {

    Page<QuyDinhResponse> pageQuyDinh(Integer pageNo, Integer size);

    List<QuyDinhResponse> listQuyDinhResponse();

    QuyDinhResponse createQuyDinh(CreateQuyDinhRequest createQuyDinhRequest);

    QuyDinhResponse updateQuyDinh(UpdateQuyDinhRequest updateQuyDinhRequest, Integer id);

    QuyDinhResponse getOneQuyDinh(Integer id);

    void removeOrRevertQuyDinh(String trangThaiQuyDinh,Integer id);

    void delete(Integer id);
}
