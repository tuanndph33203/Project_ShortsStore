package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.CoAo;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.CoAoMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateCoAoRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateCoAoRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.CoAoResponse;
import com.example.website_ban_ao_the_thao_ps.repository.CoAoRepository;
import com.example.website_ban_ao_the_thao_ps.service.CoAoService;
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
public class CoAoServiceImpl implements CoAoService {
    @Autowired
    CoAoRepository coAoRepository;

    @Autowired
    CoAoMapper coAoMapper;

    @Override
    public Page<CoAoResponse> pageCoAo(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CoAo> coAoPage = coAoRepository.findAll(pageable);
        return coAoPage.map(coAoMapper::coAoEntityToCoAoResponse);
    }

    @Override
    public Page<CoAoResponse> pageSearchCoAo(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<CoAo> coAoPage = coAoRepository.pageSearch(pageable, search);
        return coAoPage.map(coAoMapper::coAoEntityToCoAoResponse);
    }

    @Override
    public List<CoAoResponse> listCoAoResponse() {
        return coAoMapper.listCoAoEntityToCoAoResponse(coAoRepository.getCoAoByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public CoAoResponse createCoAo(CreateCoAoRequest createCoAoRequest) {
        try {
            CoAo coAo = coAoMapper.createCoAoRequestToCoAoEntity(createCoAoRequest);
            coAo.setMa(GenCode.generateCoAoCode());
            coAo.setNgayTao(LocalDate.now());
            coAo.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return coAoMapper.coAoEntityToCoAoResponse(coAoRepository.save(coAo));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create co ao. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create co ao due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public CoAoResponse updateCoAo(UpdateCoAoRequest updateCoAoRequest, Integer id) {
        try {
            CoAo coAo = coAoRepository.findById(id).orElseThrow(() -> new NotFoundException("CoAo not found with id " + id));
            CoAo updateCoAo = coAoMapper.updateCoAoRequestToCoAoEntity(updateCoAoRequest);
            coAo.setTen(updateCoAo.getTen());
            coAo.setNgayCapNhat(LocalDate.now());
            return coAoMapper.coAoEntityToCoAoResponse(coAoRepository.save(coAo));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update co ao. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update co ao due to an unexpected error." + ex);
        }
    }

    @Override
    public CoAoResponse getOneCoAo(Integer id) {
        CoAo coAo = coAoRepository.findById(id).orElseThrow(() -> new NotFoundException("CoAo not found with id " + id));
        return coAoMapper.coAoEntityToCoAoResponse(coAo);
    }

    @Override
    public void removeOrRevertCoAo(String trangThaiCoAo, Integer id) {
        coAoRepository.removeOrRevert(trangThaiCoAo, id);
    }
}

