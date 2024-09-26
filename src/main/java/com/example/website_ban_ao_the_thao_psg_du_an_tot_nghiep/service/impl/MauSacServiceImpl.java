package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.MauSac;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.MauSacMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateMauSacRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.MauSacResponse;
import com.example.website_ban_ao_the_thao_ps.repository.MauSacRepository;
import com.example.website_ban_ao_the_thao_ps.service.MauSacService;
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
public class MauSacServiceImpl implements MauSacService {
    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    MauSacMapper mauSacMapper;

    @Override
    public Page<MauSacResponse> pageMauSac(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<MauSac> mauSacPage = mauSacRepository.findAll(pageable);
        return mauSacPage.map(mauSacMapper::mauSacEntityToMauSacResponse);
    }

    @Override
    public Page<MauSacResponse> pageSearchMauSac(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<MauSac> mauSacPage = mauSacRepository.pageSearch(pageable, search);
        return mauSacPage.map(mauSacMapper::mauSacEntityToMauSacResponse);
    }

    @Override
    public List<MauSacResponse> listMauSacResponse() {
        return mauSacMapper.listMauSacEntityToMauSacResponse(mauSacRepository.getMauSacByTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE));
    }

    @Override
    public MauSacResponse createMauSac(CreateMauSacRequest createMauSacRequest) {
        try {
            MauSac mauSac = mauSacMapper.createMauSacRequestToMauSacEntity(createMauSacRequest);
            mauSac.setNgayTao(LocalDate.now());
            mauSac.setTrangThai(ApplicationConstant.TrangThaiSanPham.ACTIVE);
            return mauSacMapper.mauSacEntityToMauSacResponse(mauSacRepository.save(mauSac));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create nha san xuat. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create nha san xuat due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public MauSacResponse updateMauSac(UpdateMauSacRequest updateMauSacRequest, Integer id) {
        try {
            MauSac mauSac = mauSacRepository.findById(id).orElseThrow(() -> new NotFoundException("MauSac not found with id " + id));
            MauSac updateMauSac = mauSacMapper.updateMauSacRequestToMauSacEntity(updateMauSacRequest);
            mauSac.setMa(updateMauSac.getMa());
            mauSac.setNgayCapNhat(LocalDate.now());
            return mauSacMapper.mauSacEntityToMauSacResponse(mauSacRepository.save(mauSac));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update mau sac. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update mau sac due to an unexpected error." + ex);
        }
    }

    @Override
    public MauSacResponse getOneMauSac(Integer id) {
        MauSac mauSac = mauSacRepository.findById(id).orElseThrow(() -> new NotFoundException("MauSac not found with id " + id));
        return mauSacMapper.mauSacEntityToMauSacResponse(mauSac);
    }

    @Override
    public void removeOrRevertMauSac(String trangThaiMauSac, Integer id) {
        mauSacRepository.removeOrRevert(trangThaiMauSac, id);
    }
}
