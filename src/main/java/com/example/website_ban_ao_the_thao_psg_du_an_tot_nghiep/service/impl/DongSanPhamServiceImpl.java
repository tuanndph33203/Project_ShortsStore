package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.DongSanPham;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.DongSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDongSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.DongSanPhamResponse;
import com.example.website_ban_ao_the_thao_ps.repository.DongSanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.service.DongSanPhamService;
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
public class DongSanPhamServiceImpl implements DongSanPhamService {
    @Autowired
    DongSanPhamRepository dongSanPhamRepository;

    @Autowired
    DongSanPhamMapper dongSanPhamMapper;

    @Override
    public Page<DongSanPhamResponse> pageDongSanPham(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<DongSanPham> dongSanPhamPage = dongSanPhamRepository.findAll(pageable);
        return dongSanPhamPage.map(dongSanPhamMapper::dongSanPhamEntityToDongSanPhamResponse);
    }

    @Override
    public Page<DongSanPhamResponse> pageSearchDongSanPham(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<DongSanPham> dongSanPhamPage = dongSanPhamRepository.pageSearch(pageable, search);
        return dongSanPhamPage.map(dongSanPhamMapper::dongSanPhamEntityToDongSanPhamResponse);
    }

    @Override
    public List<DongSanPhamResponse> listDongSanPhamResponse() {
        return dongSanPhamMapper.listDongSanPhamEntityToDongSanPhamResponse(dongSanPhamRepository.getDongSanPhamByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public DongSanPhamResponse createDongSanPham(CreateDongSanPhamRequest createDongSanPhamRequest) {
        try {
            DongSanPham dongSanPham = dongSanPhamMapper.createDongSanPhamRequestToDongSanPhamEntity(createDongSanPhamRequest);
            dongSanPham.setMa(GenCode.generateDongSanPhamCode());
            dongSanPham.setNgayTao(LocalDate.now());
            dongSanPham.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return dongSanPhamMapper.dongSanPhamEntityToDongSanPhamResponse(dongSanPhamRepository.save(dongSanPham));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create dong san pham. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create dong san pham due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public DongSanPhamResponse updateDongSanPham(UpdateDongSanPhamRequest updateDongSanPhamRequest, Integer id) {
        try {
            DongSanPham dongSanPham = dongSanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("DongSanPham not found with id " + id));
            DongSanPham updateDongSanPham = dongSanPhamMapper.updateDongSanPhamRequestToDongSanPhamEntity(updateDongSanPhamRequest);
            dongSanPham.setTen(updateDongSanPham.getTen());
            dongSanPham.setNgayCapNhat(LocalDate.now());
            return dongSanPhamMapper.dongSanPhamEntityToDongSanPhamResponse(dongSanPhamRepository.save(dongSanPham));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update dong san pham. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update dong san pham due to an unexpected error." + ex);
        }
    }

    @Override
    public DongSanPhamResponse getOneDongSanPham(Integer id) {
        DongSanPham dongSanPham = dongSanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("DongSanPham not found with id " + id));
        return dongSanPhamMapper.dongSanPhamEntityToDongSanPhamResponse(dongSanPham);
    }

    @Override
    public void removeOrRevertDongSanPham(String trangThaiDongSanPham, Integer id) {
        dongSanPhamRepository.removeOrRevert(trangThaiDongSanPham, id);
    }
}
