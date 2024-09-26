package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.CongNghe;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.CongNgheMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCongNgheRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CongNgheResponse;
import com.example.website_ban_ao_the_thao_ps.repository.CongNgheRepository;
import com.example.website_ban_ao_the_thao_ps.service.CongNgheService;
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
public class CongNgheServiceImpl implements CongNgheService {

    @Autowired
    CongNgheRepository congNgheRepository;

    @Autowired
    CongNgheMapper congNgheMapper;

    @Override
    public Page<CongNgheResponse> pageCongNghe(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CongNghe> congNghePage = congNgheRepository.findAll(pageable);
        return congNghePage.map(congNgheMapper::congNgheEntityToCongNgheResponse);
    }

    @Override
    public Page<CongNgheResponse> pageSearchCongNghe(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CongNghe> congNghePage = congNgheRepository.pageSearch(pageable, search);
        return congNghePage.map(congNgheMapper::congNgheEntityToCongNgheResponse);
    }

    @Override
    public List<CongNgheResponse> listCongNgheResponse() {
        return congNgheMapper.listCongNgheEntityToCongNgheResponses(congNgheRepository.getCongNgheByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public CongNgheResponse createCongNghe(CreateCongNgheRequest createCongNgheRequest) {
        try {
            CongNghe congNghe = congNgheMapper.createCongNgheRequestToCongNgheEntity(createCongNgheRequest);
            congNghe.setMa(GenCode.generateCongNgheCode());
            congNghe.setNgayTao(LocalDate.now());
            congNghe.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return congNgheMapper.congNgheEntityToCongNgheResponse(congNgheRepository.save(congNghe));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create cong nghe. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create cong nghe due to an unexpected error." + ex);
        }
    }

    @Override
    public CongNgheResponse updateCongNghe(UpdateCongNgheRequest updateCongNgheRequest, Integer id) {
        try {
            CongNghe congNghe = congNgheRepository.findById(id).orElseThrow(() -> new NotFoundException("CongNghe not found with id " + id));
            CongNghe updateCongNghe = congNgheMapper.updateCongNgheRequestToCongNgheEntity(updateCongNgheRequest);
            congNghe.setTen(updateCongNghe.getTen());
            congNghe.setMoTa(updateCongNghe.getMoTa());
            congNghe.setNgayCapNhat(LocalDate.now());
            return congNgheMapper.congNgheEntityToCongNgheResponse(congNgheRepository.save(congNghe));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update cong nghe. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update cong nghe due to an unexpected error." + ex);
        }
    }

    @Override
    public CongNgheResponse getOneCongNghe(Integer id) {
        CongNghe congNghe = congNgheRepository.findById(id).orElseThrow(() -> new NotFoundException("CongNghe not found with id " + id));
        return congNgheMapper.congNgheEntityToCongNgheResponse(congNghe);
    }

    @Override
    public void removeOrRevertCongNghe(String trangThaiCongNghe, Integer id) {
        congNgheRepository.removeOrRevert(trangThaiCongNghe, id);
    }
}
