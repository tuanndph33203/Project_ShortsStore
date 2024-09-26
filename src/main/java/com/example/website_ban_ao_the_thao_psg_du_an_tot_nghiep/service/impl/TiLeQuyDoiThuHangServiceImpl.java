package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.common.GenCode;
import com.example.website_ban_ao_the_thao_ps.entity.TiLeQuyDoiThuHang;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.TiLeQuyDoiThuHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateTiLeQuyDoiThuHangRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.TiLeQuyDoiThuHangResponse;
import com.example.website_ban_ao_the_thao_ps.repository.TiLeQuyDoiThuHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.TiLeQuyDoiThuHangService;
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

import static com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant.TrangThaiSanPham.ACTIVE;

@Component
public class TiLeQuyDoiThuHangServiceImpl implements TiLeQuyDoiThuHangService {
    @Autowired
    TiLeQuyDoiThuHangRepository tiLeQuyDoiThuHangRepository;

    @Autowired
    TiLeQuyDoiThuHangMapper tiLeQuyDoiThuHangMapper;

    @Override
    public Page<TiLeQuyDoiThuHangResponse> pageTiLeQuyDoiThuHang(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<TiLeQuyDoiThuHang> tiLeQuyDoiThuHangPage = tiLeQuyDoiThuHangRepository.findAll(pageable);
        return tiLeQuyDoiThuHangPage.map(tiLeQuyDoiThuHangMapper::tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse);
    }

    @Override
    public List<TiLeQuyDoiThuHangResponse> listTiLeQuyDoiThuHangResponse() {
        return tiLeQuyDoiThuHangMapper.listTiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(tiLeQuyDoiThuHangRepository.getTiLeQuyDoiThuHangByTrangThai(ACTIVE));
    }

    @Override
    public TiLeQuyDoiThuHangResponse createTiLeQuyDoiThuHang(CreateTiLeQuyDoiThuHangRequest createTiLeQuyDoiThuHangRequest) {
        try {
            TiLeQuyDoiThuHang tiLeQuyDoiThuHang = tiLeQuyDoiThuHangMapper.createTiLeQuyDoiThuHangRequestToTiLeQuyDoiThuHangEntity(createTiLeQuyDoiThuHangRequest);
            tiLeQuyDoiThuHang.setNgayTao(LocalDate.now());
            tiLeQuyDoiThuHang.setTrangThai(ApplicationConstant.TrangThaiTiLeQuyDoiThuHang.ACTIVE);
            return tiLeQuyDoiThuHangMapper.tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(tiLeQuyDoiThuHangRepository.save(tiLeQuyDoiThuHang));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create thu hang. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create thu hang due to an unexpected error." + ex);
        }
    }

    @Override
    @Transactional
    public TiLeQuyDoiThuHangResponse updateTiLeQuyDoiThuHang(UpdateTiLeQuyDoiThuHangRequest updateTiLeQuyDoiThuHangRequest, Integer id) {
        try {
            TiLeQuyDoiThuHang tiLeQuyDoiThuHang = tiLeQuyDoiThuHangRepository.findById(id).orElseThrow(() -> new NotFoundException("TiLeQuyDoiThuHang not found with id " + id));
            TiLeQuyDoiThuHang updateTiLeQuyDoiThuHang = tiLeQuyDoiThuHangMapper.updateTiLeQuyDoiThuHangRequestToTiLeQuyDoiThuHangEntity(updateTiLeQuyDoiThuHangRequest);
            tiLeQuyDoiThuHang.setNgayCapNhat(LocalDate.now());
            return tiLeQuyDoiThuHangMapper.tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(tiLeQuyDoiThuHangRepository.save(tiLeQuyDoiThuHang));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update thu hang. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update thu hang due to an unexpected error." + ex);
        }
    }

    @Override
    public TiLeQuyDoiThuHangResponse getOneTiLeQuyDoiThuHang(Integer id) {
        TiLeQuyDoiThuHang tiLeQuyDoiThuHang = tiLeQuyDoiThuHangRepository.findById(id).orElseThrow(() -> new NotFoundException("TiLeQuyDoiThuHang not found with id " + id));
        return tiLeQuyDoiThuHangMapper.tiLeQuyDoiThuHangEntityToTiLeQuyDoiThuHangResponse(tiLeQuyDoiThuHang);
    }

    @Override
    public void removeOrRevertTiLeQuyDoiThuHang(String trangThaiTiLeQuyDoiThuHang, Integer id) {
        tiLeQuyDoiThuHangRepository.removeOrRevert(trangThaiTiLeQuyDoiThuHang, id);
    }
}
