package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.ThuongHieu;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ThuongHieuMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateThuongHieuRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ThuongHieuResponse;
import com.example.website_ban_ao_the_thao_ps.repository.ThuongHieuRepository;
import com.example.website_ban_ao_the_thao_ps.service.ThuongHieuService;
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
public class ThuongHieuServiceImpl implements ThuongHieuService {
    @Autowired
    ThuongHieuRepository thuongHieuRepository;

    @Autowired
    ThuongHieuMapper thuongHieuMapper;

    @Override
    public Page<ThuongHieuResponse> pageThuongHieu(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ThuongHieu> thuongHieuPage = thuongHieuRepository.findAll(pageable);
        return thuongHieuPage.map(thuongHieuMapper::thuongHieuEntityToThuongHieuResponse);
    }

    @Override
    public Page<ThuongHieuResponse> pageSearchThuongHieu(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ThuongHieu> thuongHieuPage = thuongHieuRepository.pageSearch(pageable, search);
        return thuongHieuPage.map(thuongHieuMapper::thuongHieuEntityToThuongHieuResponse);
    }

    @Override
    public List<ThuongHieuResponse> listThuongHieuResponse() {
        return thuongHieuMapper.listThuongHieuEntityToThuongHieuResponse(thuongHieuRepository.getThuongHieuByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public ThuongHieuResponse createThuongHieu(CreateThuongHieuRequest createThuongHieuRequest) {
        try {
            ThuongHieu thuongHieu = thuongHieuMapper.createThuongHieuRequestToThuongHieuEntity(createThuongHieuRequest);
            thuongHieu.setMa(GenCode.generateThuongHieuCode());
            thuongHieu.setNgayTao(LocalDate.now());
            thuongHieu.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return thuongHieuMapper.thuongHieuEntityToThuongHieuResponse(thuongHieuRepository.save(thuongHieu));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create thuong hieu. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create thuong hieu due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public ThuongHieuResponse updateThuongHieu(UpdateThuongHieuRequest updateThuongHieuRequest, Integer id) {
        try {
            ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElseThrow(() -> new NotFoundException("ThuongHieu not found with id " + id));
            ThuongHieu updateThuongHieu = thuongHieuMapper.updateThuongHieuRequestToThuongHieuEntity(updateThuongHieuRequest);
            thuongHieu.setTen(updateThuongHieu.getTen());
            thuongHieu.setNgayCapNhat(LocalDate.now());
            return thuongHieuMapper.thuongHieuEntityToThuongHieuResponse(thuongHieuRepository.save(thuongHieu));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update thuong hieu. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update thuong hieu due to an unexpected error." + ex);
        }
    }

    @Override
    public ThuongHieuResponse getOneThuongHieu(Integer id) {
        ThuongHieu thuongHieu = thuongHieuRepository.findById(id).orElseThrow(() -> new NotFoundException("ThuongHieu not found with id " + id));
        return thuongHieuMapper.thuongHieuEntityToThuongHieuResponse(thuongHieu);
    }

    @Override
    public void removeOrRevertThuongHieu(String trangThaiThuongHieu, Integer id) {
        thuongHieuRepository.removeOrRevert(trangThaiThuongHieu, id);
    }
}
