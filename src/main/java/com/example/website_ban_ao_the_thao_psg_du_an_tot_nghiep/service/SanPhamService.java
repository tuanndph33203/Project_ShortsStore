package com.example.website_ban_ao_the_thao_ps.service;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.model.response.KhachHangResponse;
import com.example.website_ban_ao_the_thao_ps.entity.KichThuoc;
import com.example.website_ban_ao_the_thao_ps.model.request.create_request.CreateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.request.update_request.UpdateSanPhamRequest;
import com.example.website_ban_ao_the_thao_ps.model.response.SanPhamResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface SanPhamService {

    Page<SanPhamResponse> pageSanPham(Integer pageNo, Integer size);

    Page<SanPhamResponse> pageSearchSanPhamAdmin(Integer pageNo, Integer size,String search, Boolean gioiTinh, Integer cauThuId, Integer coAoId,
                                            Integer mauSacId, Integer loaiSanPhamId,
                                            Integer chatLieuId, Integer dongSanPhamId,
                                            Integer nhaSanXuatId, Integer thuongHieuId,
                                            Integer nuocSanXuatId, Integer congNgheId,
                                            ApplicationConstant.TrangThaiSanPham trangThai,BigDecimal giaMin, BigDecimal giaMax);

    List<SanPhamResponse> listSanPhamResponse();

    byte[] exportExcelSanPham() throws IOException;

    SanPhamResponse createSanPham(CreateSanPhamRequest createSanPhamRequest);

    SanPhamResponse updateSanPham(UpdateSanPhamRequest updateSanPhamRequest, Integer id);
    SanPhamResponse getOneSanPham(Integer id);

    void removeOrRevertSanPham(String trangThaiSanPham, Integer id);
}
