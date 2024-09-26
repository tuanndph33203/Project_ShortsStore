package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateChiTietSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.ChiTietSanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ChiTietSanPhamService {
    Page<ChiTietSanPhamResponse> pageChiTietSanPham(Integer pageNo, Integer size);
    Page<ChiTietSanPhamResponse> pageSearchChiTietSanPhamAdmin(Integer pageNo, Integer size, String search, Boolean gioiTinh,   Integer kichThuocId, Integer cauThuId,   Integer mauSacId,   Integer thuongHieuId,   Integer congNgheId, ApplicationConstant.TrangThaiSanPham trangThai);
    ChiTietSanPhamResponse getOneChiTietSanPham(Integer id);
    ChiTietSanPhamResponse getOneChiTietSanPhamBySku(String sku);

    List<ChiTietSanPhamResponse> findByTrangThai();


    List<ChiTietSanPhamResponse> updateSoLuongSanPhamPending(UpdateChiTietSanPhamRequest chiTietSanPhamRequest);

    void removeOrRevertChiTietSanPham(String trangThaiChiTietSanPham, Integer id);

    List<String> findAllSku(List<Integer> id);
}
