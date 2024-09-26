package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.QuyDoiDiem;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.QuyDoiDiemMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDoiDiemRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDoiDiemResponse;
import com.example.website_ban_ao_the_thao_ps.repository.QuyDoiDiemRepository;
import com.example.website_ban_ao_the_thao_ps.service.QuyDoiDiemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class QuyDoiDiemServiceImpl implements QuyDoiDiemService {
    @Autowired
    QuyDoiDiemRepository quyDoiDiemRepository;

    @Autowired
    QuyDoiDiemMapper quyDoiDiemMapper;

    @Override
    public List<QuyDoiDiemResponse> listQuyDoiDiemResponse() {
        return quyDoiDiemMapper.listQuyDoiDiemEntityToQuyDoiDiemResponse(this.quyDoiDiemRepository.findAll());
    }

    @Override
    public QuyDoiDiemResponse createQuyDoiDiem(CreateQuyDoiDiemRequest createQuyDoiDiemRequest) {
        try {
            QuyDoiDiem quyDoiDiem = quyDoiDiemMapper.createQuyDoiDiemRequestToQuyDoiDiemEntity(createQuyDoiDiemRequest);
            quyDoiDiem.setNgayTao(LocalDate.now());
            quyDoiDiem.setTrangThai(ApplicationConstant.TrangThaiQuyDoiDiem.ACTIVE);
            return quyDoiDiemMapper.quyDoiDiemEntityToQuyDoiDiemResponse(quyDoiDiemRepository.save(quyDoiDiem));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create nuoc san xuat. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create nuoc san xuat due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public QuyDoiDiemResponse updateQuyDoiDiem(UpdateQuyDoiDiemRequest updateQuyDoiDiemRequest, Integer id) {
        try {
            QuyDoiDiem quyDoiDiem = quyDoiDiemRepository.findById(id).orElseThrow(() -> new NotFoundException("QuyDinh not found with id " + id));
            quyDoiDiem.setSoTienQuyDoi(updateQuyDoiDiemRequest.getSoTienQuyDoi());
            quyDoiDiem.setNgayCapNhat(LocalDate.now());
            return quyDoiDiemMapper.quyDoiDiemEntityToQuyDoiDiemResponse(this.quyDoiDiemRepository.save(quyDoiDiem));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update quy dinh. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update quy dinh due to an unexpected error." + ex);
        }
    }

    @Override
    public QuyDoiDiemResponse getOneQuyDoiDiem(Integer id) {
        QuyDoiDiem quyDoiDiem = quyDoiDiemRepository.findById(id).orElseThrow(() -> new NotFoundException("QuyDinh not found with id " + id));;
        return quyDoiDiemMapper.quyDoiDiemEntityToQuyDoiDiemResponse(quyDoiDiem);
    }

    @Override
    public void removeOrRevertQuyDoiDiem(String trangThaiQuyDoiDiem, Integer id) {
        quyDoiDiemRepository.removeOrRevert(trangThaiQuyDoiDiem, id);
    }
}
