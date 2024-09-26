package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.LoaiSanPham;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.LoaiSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateLoaiSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.LoaiSanPhamResponse;
import com.example.website_ban_ao_the_thao_ps.repository.LoaiSanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.service.LoaiSanPhamService;
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
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {
    @Autowired
    LoaiSanPhamRepository loaiSanPhamRepository;

    @Autowired
    LoaiSanPhamMapper loaiSanPhamMapper;

    @Override
    public Page<LoaiSanPhamResponse> pageLoaiSanPham(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<LoaiSanPham> loaiSanPhamPage = loaiSanPhamRepository.findAll(pageable);
        return loaiSanPhamPage.map(loaiSanPhamMapper::loaiSanPhamEntityToLoaiSanPhamResponse);
    }

    @Override
    public Page<LoaiSanPhamResponse> pageSearchLoaiSanPham(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<LoaiSanPham> loaiSanPhamPage = loaiSanPhamRepository.pageSearch(pageable, search);
        return loaiSanPhamPage.map(loaiSanPhamMapper::loaiSanPhamEntityToLoaiSanPhamResponse);
    }

    @Override
    public List<LoaiSanPhamResponse> listLoaiSanPhamResponse() {
        return loaiSanPhamMapper.listLoaiSanPhamEntityToLoaiSanPhamResponses(loaiSanPhamRepository.getLoaiSanPhamByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public LoaiSanPhamResponse createLoaiSanPham(CreateLoaiSanPhamRequest createLoaiSanPhamRequest) {
        try {
            LoaiSanPham loaiSanPham = loaiSanPhamMapper.createLoaiSanPhamRequestToLoaiSanPhamEntity(createLoaiSanPhamRequest);
            loaiSanPham.setMa(GenCode.generateLoaiSanPhamCode());
            loaiSanPham.setNgayTao(LocalDate.now());
            loaiSanPham.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return loaiSanPhamMapper.loaiSanPhamEntityToLoaiSanPhamResponse(loaiSanPhamRepository.save(loaiSanPham));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create loai san pham. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create loai san pham due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public LoaiSanPhamResponse updateLoaiSanPham(UpdateLoaiSanPhamRequest updateLoaiSanPhamRequest, Integer id) {
        try {
            LoaiSanPham loaiSanPham = loaiSanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("LoaiSanPham not found with id " + id));
            LoaiSanPham updateLoaiSanPham = loaiSanPhamMapper.updateLoaiSanPhamRequestToLoaiSanPhamEntity(updateLoaiSanPhamRequest);
            loaiSanPham.setTen(updateLoaiSanPham.getTen());
            loaiSanPham.setNgayCapNhat(LocalDate.now());
            return loaiSanPhamMapper.loaiSanPhamEntityToLoaiSanPhamResponse(loaiSanPhamRepository.save(loaiSanPham));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update loai san pham. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update loai san pham due to an unexpected error." + ex);
        }
    }

    @Override
    public LoaiSanPhamResponse getOneLoaiSanPham(Integer id) {
        LoaiSanPham loaiSanPham = loaiSanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("LoaiSanPham not found with id " + id));
        return loaiSanPhamMapper.loaiSanPhamEntityToLoaiSanPhamResponse(loaiSanPham);
    }

    @Override
    public void removeOrRevertLoaiSanPham(String trangThaiLoaiSanPham, Integer id) {
        loaiSanPhamRepository.removeOrRevert(trangThaiLoaiSanPham, id);
    }
}
