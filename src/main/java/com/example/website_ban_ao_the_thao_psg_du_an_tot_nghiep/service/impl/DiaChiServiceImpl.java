package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.DiaChi;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.exception.DuplicateRecordException;
import com.example.website_ban_ao_the_thao_ps.exception.InternalServerException;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.DiaChiMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.KhachHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateDiaChiRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateDiaChiRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.DiaChiResponse;
import com.example.website_ban_ao_the_thao_ps.repository.DiaChiRepository;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    DiaChiMapper diaChiMapper;

    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    KhachHangMapper khachHangMapper;

    @Override
    public Page<DiaChiResponse> pageDiaChi(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<DiaChi> diaChiPage = diaChiRepository.pageACTIVE(pageable);
        return diaChiPage.map(diaChiMapper::diaChiEntityToDiaChiResponse);
    }

    @Override
    public DiaChiResponse getOneDiaChi(Integer id) {
        DiaChi diaChi = diaChiRepository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay id dia chi" +id));
        return diaChiMapper.diaChiEntityToDiaChiResponse(diaChi);
    }

    @Override
    public Page<DiaChiResponse> pageSearchDiaChi(Integer pageNo, Integer size, String search) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<DiaChi> diaChiPage = diaChiRepository.pageSearchActive(search, pageable);
        return diaChiPage.map(diaChiMapper::diaChiEntityToDiaChiResponse);
    }

    @Override
    public DiaChiResponse add(CreateDiaChiRequest createDiaChiRequest, Integer khachHangID) {
        if (khachHangID == null) {
            throw new IllegalArgumentException("Không tìm thấy ID khách hàng");
        }
        Optional<KhachHang> khachHangOptional = khachHangRepository.findById(khachHangID);
        if (khachHangOptional.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với ID đã cho");
        }
        KhachHang khachHang = khachHangOptional.get();

        DiaChi diaChi = diaChiMapper.createDiaChiRequestToDiaChiEntity(createDiaChiRequest);
        diaChi.setId(null);
        diaChi.setNgayTao(LocalDate.now());
        diaChi.setTrangThai(ApplicationConstant.TrangThaiDiaChi.EXTRA);
        diaChi.setKhachHang(khachHang);
        return diaChiMapper.diaChiEntityToDiaChiResponse(diaChiRepository.save(diaChi));
    }
    
    @Override
    public DiaChiResponse update(UpdateDiaChiRequest updateDiaChiRequest, Integer id) {
        try {
            DiaChi diaChi = diaChiRepository.findById(id).orElseThrow(() -> new NotFoundException("Dia chi not found id" + id));
            DiaChi updateDiaChi = diaChiMapper.updateDiaChiRequestToDiaChiEntity(updateDiaChiRequest);
            diaChi.setHoTen(updateDiaChi.getHoTen());
            diaChi.setDiaChiChiTiet(updateDiaChi.getDiaChiChiTiet());
            diaChi.setMaTinh(updateDiaChi.getMaTinh());
            diaChi.setMaPhuongXa(updateDiaChi.getMaPhuongXa());
            diaChi.setMaQuanHuyen(updateDiaChi.getMaQuanHuyen());
            diaChi.setSdt(updateDiaChi.getSdt());
            diaChi.setTenTinh(updateDiaChi.getTenTinh());
            diaChi.setTenPhuongXa(updateDiaChi.getTenPhuongXa());
            diaChi.setTenQuanHuyen(updateDiaChi.getTenQuanHuyen());
            diaChi.setNgayCapNhap(LocalDate.now());
            return diaChiMapper.diaChiEntityToDiaChiResponse(diaChiRepository.save(diaChi));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to update dia chi. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to update dia chi due to an unexpected error." + ex);
        }
    }


    @Override
    public DiaChiResponse getOne(Integer id) {
        Optional<DiaChi> diaChiOptional = diaChiRepository.findById(id);
        return diaChiMapper.diaChiEntityToDiaChiResponse(diaChiOptional.get());
    }

    @Override
    public Page<DiaChiResponse> searchNameOrMa(String searchName, Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<DiaChi> diaChiPage = diaChiRepository.pageSearchActive(searchName, pageable);
        return diaChiPage.map(diaChiMapper::diaChiEntityToDiaChiResponse);
    }

    @Override
    public void delete(Integer id) {
        diaChiRepository.deleteById(id);
    }

    @Override
    public void diaChiTrangThai(String trangThaiDiaChi, Integer id) {
        diaChiRepository.update(trangThaiDiaChi, id);
    }


}
