package com.example.website_ban_ao_the_thao_ps.model.mapper;

import com.example.website_ban_ao_the_thao_ps.entity.QuyDinh;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDinhResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuyDinhMapper {

    QuyDinh quyDinhResponseToQuyDinhEntity(QuyDinhResponse quyDinhResponse);

    QuyDinhResponse quyDinhEntityToQuyDinhResponse(QuyDinh quyDinh);

    QuyDinh createQuyDinhRequestToQuyDinhEntity(CreateQuyDinhRequest createQuyDinhRequest);

    QuyDinh updateQuyDinhRequestToQuyDinhEntity(UpdateQuyDinhRequest updateQuyDinhRequestQuyDinhRequest);

    List<QuyDinhResponse> listQuyDinhEntityToQuyDinhResponse(List<QuyDinh> quyDinhList);
}
