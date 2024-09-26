package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.VaiTro;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.VaiTroMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateVaiTroRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateVaiTroRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.VaiTroResponse;
import com.example.website_ban_ao_the_thao_ps.repository.VaiTroRepository;
import com.example.website_ban_ao_the_thao_ps.service.VaiTroService;
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
public class VaiTroServiceImpl implements VaiTroService {
    @Autowired
    VaiTroRepository vaiTroRepository;

    @Autowired
    VaiTroMapper vaiTroMapper;

    @Override
    public Page<VaiTroResponse> pageVaiTro(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<VaiTro> vaiTroPage = vaiTroRepository.findAll(pageable);
        return vaiTroPage.map(vaiTroMapper::vaiTroEntityToVaiTroResponse);
    }

    @Override
    public Page<VaiTroResponse> pageSearchVaiTro(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<VaiTro> vaiTroPage = vaiTroRepository.pageSearch(pageable, search);
        return vaiTroPage.map(vaiTroMapper::vaiTroEntityToVaiTroResponse);
    }

    @Override
    public List<VaiTroResponse> listVaiTroResponse() {
        return vaiTroMapper.listVaiTroEntityToVaiTroResponse(vaiTroRepository.getVaiTroByTrangThai(ApplicationConstant.TrangThaiVaiTro.ACTIVE));
    }

    @Override
    public VaiTroResponse createVaiTro(CreateVaiTroRequest createVaiTroRequest) {
        try {
            VaiTro vaiTro = vaiTroMapper.createVaiTroRequestToVaiTroEntity(createVaiTroRequest);
            vaiTro.setMa(GenCode.generateVaiTroCode());
            vaiTro.setNgayTao(LocalDate.now());
            vaiTro.setTrangThai(ApplicationConstant.TrangThaiVaiTro.ACTIVE);
            return vaiTroMapper.vaiTroEntityToVaiTroResponse(vaiTroRepository.save(vaiTro));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create vai tro. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create vai tro due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public VaiTroResponse updateVaiTro(UpdateVaiTroRequest updateVaiTroRequest, Integer id) {
        try {
            VaiTro vaiTro = vaiTroRepository.findById(id).orElseThrow(() -> new NotFoundException("VaiTro not found with id " + id));
            VaiTro updateVaiTro = vaiTroMapper.updateVaiTroRequestToVaiTroEntity(updateVaiTroRequest);
            vaiTro.setTen(updateVaiTro.getTen());
            vaiTro.setNgayCapNhap(LocalDate.now());
            return vaiTroMapper.vaiTroEntityToVaiTroResponse(vaiTroRepository.save(vaiTro));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update vai tro. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update vai tro due to an unexpected error." + ex);
        }
    }

    @Override
    public VaiTroResponse getOneVaiTro(Integer id) {
        VaiTro vaiTro = vaiTroRepository.findById(id).orElseThrow(() -> new NotFoundException("VaiTro not found with id " + id));
        return vaiTroMapper.vaiTroEntityToVaiTroResponse(vaiTro);
    }

    @Override
    public void removeOrRevertVaiTro(String trangThaiVaiTro, Integer id) {
        vaiTroRepository.removeOrRevert(trangThaiVaiTro, id);
    }
}
