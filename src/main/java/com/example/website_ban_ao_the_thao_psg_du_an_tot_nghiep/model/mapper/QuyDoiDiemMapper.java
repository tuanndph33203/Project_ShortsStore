package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.QuyDoiDiem;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDoiDiemResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuyDoiDiemMapper {

    QuyDoiDiem quyDoiDiemResponseToQuyDoiDiemEntity(QuyDoiDiemResponse quyDoiDiemResponse);

    QuyDoiDiemResponse quyDoiDiemEntityToQuyDoiDiemResponse(QuyDoiDiem quyDoiDiem);

    QuyDoiDiem createQuyDoiDiemRequestToQuyDoiDiemEntity(CreateQuyDoiDiemRequest createQuyDoiDiemRequest);

    QuyDoiDiem updateQuyDoiDiemRequestToQuyDoiDiemEntity(UpdateQuyDoiDiemRequest updateQuyDoiDiemRequest);

    List<QuyDoiDiemResponse> listQuyDoiDiemEntityToQuyDoiDiemResponse(List<QuyDoiDiem> quyDoiDiemList);
}
