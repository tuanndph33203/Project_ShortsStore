package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChiTietSanPham;
import com.example.website_ban_ao_the_thao_ps.entity.SanPham;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.model.mapper.ChiTietSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.SanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChiTietSanPhamResponse;
import com.example.website_ban_ao_the_thao_ps.repository.ChiTietSanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.repository.SanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    ChiTietSanPhamMapper chiTietSanPhamMapper;

    @Override
    public Page<ChiTietSanPhamResponse> pageChiTietSanPham(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ChiTietSanPham> chiTietSanPhamPage = chiTietSanPhamRepository.findAll(pageable);
        return chiTietSanPhamPage.map(chiTietSanPhamMapper::chiTietSanPhamEntityTochiTietSanPhamResponse);
    }

    @Override
    public Page<ChiTietSanPhamResponse> pageSearchChiTietSanPhamAdmin(Integer pageNo, Integer size, String search, Boolean gioiTinh,   Integer kichThuocId, Integer cauThuId,   Integer mauSacId,   Integer thuongHieuId,   Integer congNgheId, ApplicationConstant.TrangThaiSanPham trangThai) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<ChiTietSanPham> chiTietSanPhamPage = chiTietSanPhamRepository.pageSearchChiTietSanPhamAdmin(pageable, search, gioiTinh, kichThuocId, cauThuId, mauSacId, thuongHieuId, congNgheId, trangThai);
        return chiTietSanPhamPage.map(chiTietSanPhamMapper::chiTietSanPhamEntityTochiTietSanPhamResponse);
    }

    @Override
    public ChiTietSanPhamResponse getOneChiTietSanPham(Integer id) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(id).orElseThrow(() -> new NotFoundException("ChiTietSanPham not found with id" + id));
        return chiTietSanPhamMapper.chiTietSanPhamEntityTochiTietSanPhamResponse(chiTietSanPham);
    }

    @Override
    public ChiTietSanPhamResponse getOneChiTietSanPhamBySku(String sku) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getChiTietSanPhamBySku(sku).orElseThrow(() -> new NotFoundException("ChiTietSanPham not found with sku" + sku));
        return chiTietSanPhamMapper.chiTietSanPhamEntityTochiTietSanPhamResponse(chiTietSanPham);
    }

    @Override
    public List<ChiTietSanPhamResponse> findByTrangThai() {
        List<ChiTietSanPham> chiTietSanPhamList = this.chiTietSanPhamRepository.findChiTietSanPhamsByTrangThai(ApplicationConstant.TrangThaiChiTietSanPham.PENDING);
        return this.chiTietSanPhamMapper.listchiTietSanPhamEntityTochiTietSanPhamResponse(chiTietSanPhamList);
    }



    @Override
    public List<ChiTietSanPhamResponse> updateSoLuongSanPhamPending(UpdateChiTietSanPhamRequest updateChiTietSanPhamRequest) {
        List<ChiTietSanPham> chiTietSanPhamList = this.chiTietSanPhamRepository.findChiTietSanPhamsByTrangThai(ApplicationConstant.TrangThaiChiTietSanPham.PENDING);
        List<ChiTietSanPham> newlistChiTietSanPham = new ArrayList<>();
            for (int i = 0; i < chiTietSanPhamList.size(); i++) {
                ChiTietSanPham chiTietSanPham = chiTietSanPhamList.get(i);
                chiTietSanPham.setSoLuong(updateChiTietSanPhamRequest.getSoLuong());
                chiTietSanPham.setSku(updateChiTietSanPhamRequest.getSku());
                chiTietSanPham.setKichThuoc(updateChiTietSanPhamRequest.getKichThuoc());
                chiTietSanPham.setNgayCapNhat(LocalDate.now());
                newlistChiTietSanPham.add(chiTietSanPham);
            }
        return this.chiTietSanPhamMapper.listchiTietSanPhamEntityTochiTietSanPhamResponse(this.chiTietSanPhamRepository.saveAll(newlistChiTietSanPham));
    }

    @Override
    public void removeOrRevertChiTietSanPham(String trangThaiChiTietSanPham, Integer id) {
        this.chiTietSanPhamRepository.removeOrRevert(trangThaiChiTietSanPham, id);
    }

    @Override
    public List<String> findAllSku(List<Integer> id) {
        return this.chiTietSanPhamRepository.findAllSkuExcluding(id);
    }
}
