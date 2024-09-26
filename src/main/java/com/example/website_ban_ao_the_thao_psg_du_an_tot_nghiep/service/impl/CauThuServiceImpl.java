package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.CauThu;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.CauThuMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCauThuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CauThuResponse;
import com.example.website_ban_ao_the_thao_ps.repository.CauThuRepository;
import com.example.website_ban_ao_the_thao_ps.service.CauThuService;
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
public class CauThuServiceImpl implements CauThuService {
    @Autowired
    CauThuRepository cauThuRepository;

    @Autowired
    CauThuMapper cauThuMapper;

    @Override
    public Page<CauThuResponse> pageCauThu(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CauThu> cauThuPage = cauThuRepository.findAll(pageable);
        return cauThuPage.map(cauThuMapper::cauThuEntityToCauThuResponse);
    }

    @Override
    public Page<CauThuResponse> pageSearchCauThu(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CauThu> cauThuPage = cauThuRepository.pageSearch(pageable, search);
        return cauThuPage.map(cauThuMapper::cauThuEntityToCauThuResponse);
    }

    @Override
    public List<CauThuResponse> listCauThuResponse() {
        return cauThuMapper.listCauThuEntityToCauThuResponse(cauThuRepository.getCauThuByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public CauThuResponse createCauThu(CreateCauThuRequest createCauThuRequest) {
        try {
            CauThu cauThu = cauThuMapper.createCauThuRequestToCauThuEntity(createCauThuRequest);
            cauThu.setMa(GenCode.generateCauThuCode());
            cauThu.setNgayTao(LocalDate.now());
            cauThu.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return cauThuMapper.cauThuEntityToCauThuResponse(cauThuRepository.save(cauThu));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create cau thu. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create cau thu due to an unexpected error." + ex);
        }
    }

    @Override
    public CauThuResponse updateCauThu(UpdateCauThuRequest updateCauThuRequest, Integer id) {
        try {
            CauThu cauThu = cauThuRepository.findById(id).orElseThrow(() -> new NotFoundException("CauThu not found with id " + id));
            CauThu updateCauThu = cauThuMapper.updateCauThuRequestToCauThuEntity(updateCauThuRequest);
            cauThu.setTen(updateCauThu.getTen());
            cauThu.setSoAo(updateCauThu.getSoAo());
            cauThu.setNgayCapNhat(LocalDate.now());
            return cauThuMapper.cauThuEntityToCauThuResponse(cauThuRepository.save(cauThu));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update cau thu. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update cau thu due to an unexpected error." + ex);
        }
    }

    @Override
    public CauThuResponse getOneCauThu(Integer id) {
        CauThu cauThu = cauThuRepository.findById(id).orElseThrow(() -> new NotFoundException("CauThu not found with id " + id));
        return cauThuMapper.cauThuEntityToCauThuResponse(cauThu);
    }

    @Override
    public void removeOrRevertCauThu(String trangThaiCauThu, Integer id) {
        cauThuRepository.removeOrRevert(trangThaiCauThu, id);
    }
}
