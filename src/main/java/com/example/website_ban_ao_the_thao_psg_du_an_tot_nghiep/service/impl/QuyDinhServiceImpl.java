package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.QuyDinh;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.QuyDinhMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateQuyDinhRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.QuyDinhResponse;
import com.example.website_ban_ao_the_thao_ps.repository.QuyDinhRepository;
import com.example.website_ban_ao_the_thao_ps.service.QuyDinhService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class QuyDinhServiceImpl implements QuyDinhService {

    @Autowired
    QuyDinhRepository quyDinhRepository;

    @Autowired
    QuyDinhMapper quyDinhMapper;

    @Override
    public Page<QuyDinhResponse> pageQuyDinh(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<QuyDinh> quyDinhPage = quyDinhRepository.findAll(pageable);
        return quyDinhPage.map(quyDinhMapper::quyDinhEntityToQuyDinhResponse);
    }

    @Override
    public List<QuyDinhResponse> listQuyDinhResponse() {
        return quyDinhMapper.listQuyDinhEntityToQuyDinhResponse(quyDinhRepository.getQuyDinhByTrangThai(ApplicationConstant.TrangThaiQuyDinh.ACTIVE));
    }

    @Override
    public QuyDinhResponse createQuyDinh(CreateQuyDinhRequest createQuyDinhRequest) {
        try {
            QuyDinh quyDinh = quyDinhMapper.createQuyDinhRequestToQuyDinhEntity(createQuyDinhRequest);
            quyDinh.setNgayTao(LocalDate.now());
            quyDinh.setTrangThai(ApplicationConstant.TrangThaiQuyDinh.ACTIVE);
            return quyDinhMapper.quyDinhEntityToQuyDinhResponse(quyDinhRepository.save(quyDinh));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create quy dinh. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create quy dinh due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public QuyDinhResponse updateQuyDinh(UpdateQuyDinhRequest updateQuyDinhRequest, Integer id) {
        try {
            QuyDinh quyDinh = quyDinhRepository.findById(id).orElseThrow(() -> new NotFoundException("QuyDinh not found with id " + id));
            QuyDinh updateQuyDinh = quyDinhMapper.updateQuyDinhRequestToQuyDinhEntity(updateQuyDinhRequest);
            quyDinh.setNgayCapNhat(LocalDate.now());
            return quyDinhMapper.quyDinhEntityToQuyDinhResponse(quyDinhRepository.save(quyDinh));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update quy dinh. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update quy dinh due to an unexpected error." + ex);
        }
    }

    @Override
    public QuyDinhResponse getOneQuyDinh(Integer id) {
        QuyDinh quyDinh = quyDinhRepository.findById(id).orElseThrow(() -> new NotFoundException("QuyDinh not found with id " + id));
        return quyDinhMapper.quyDinhEntityToQuyDinhResponse(quyDinh);
    }

    @Override
    public void removeOrRevertQuyDinh(String trangThaiQuyDinh, Integer id) {
        quyDinhRepository.removeOrRevert(trangThaiQuyDinh, id);
    }

    @Override
    public void delete(Integer id) {
        this.quyDinhRepository.deleteById(id);
    }
}
