package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.dto.HoaDonDTO;
import com.example.website_ban_ao_the_thao_ps.dto.HoaDonDTO2;
import com.example.website_ban_ao_the_thao_ps.dto.HoaDonDTO3;
import com.example.website_ban_ao_the_thao_ps.dto.TopSellingProductDTO;
import com.example.website_ban_ao_the_thao_ps.entity.KhachHang;
import com.example.website_ban_ao_the_thao_ps.model.mapper.HoaDonMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.KhachHangMapper;
import com.example.website_ban_ao_the_thao_ps.model.mapper.impl.TopSellingProductMapper;
import com.example.website_ban_ao_the_thao_ps.model.response.*;
import com.example.website_ban_ao_the_thao_ps.repository.HoaDonChiTietRepository;
import com.example.website_ban_ao_the_thao_ps.repository.HoaDonRepository;
import com.example.website_ban_ao_the_thao_ps.repository.KhachHangRepository;
import com.example.website_ban_ao_the_thao_ps.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ThongKeServiceImpl implements ThongKeService {
    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    HoaDonMapper hoaDonMapper;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    KhachHangMapper khachHangMapper;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private TopSellingProductMapper topSellingProductMapper;

    @Override
    public HoaDonDTO findByNgayTaoTruoc(Integer ngayTruoc) {
        LocalDate ngayHienTai = LocalDate.now();
        LocalDate ngayTruocDo = ngayHienTai.minusDays(ngayTruoc);
        List<Object[]> results;
        List<HoaDonDTO> hoaDonDTOList;
        BigDecimal totalThanhTien = BigDecimal.ZERO;
        Long totalSoLuong;
        if (ngayTruoc >= 1) {
            results = hoaDonRepository.countAndSumByNgayTao(ngayTruocDo, ngayHienTai);
            hoaDonDTOList = mapToHoaDonDTOList(results);
            for (HoaDonDTO dto : hoaDonDTOList) {
                BigDecimal tongThanhTien = dto.getTongThanhTien();
                if (tongThanhTien != null) {
                    totalThanhTien = totalThanhTien.add(tongThanhTien);
                }
            }
            totalSoLuong = hoaDonDTOList.stream().map(HoaDonDTO::getSoLuong).mapToLong(Long::valueOf).sum();
        } else {
            results = hoaDonRepository.countAndSumByNgayTao(ngayTruocDo, ngayHienTai);
            hoaDonDTOList = mapToHoaDonDTOList(results);
            for (HoaDonDTO dto : hoaDonDTOList) {
                BigDecimal tongThanhTien = dto.getTongThanhTien();
                if (tongThanhTien != null) {
                    totalThanhTien = totalThanhTien.add(tongThanhTien);
                }
            }
            totalSoLuong = mapToHoaDonDTOList(results).stream().mapToLong(HoaDonDTO::getSoLuong).sum();
        }
        return new HoaDonDTO(totalSoLuong, totalThanhTien);
    }

    @Override
    public HoaDonDTO findByThangTaoTruoc(Integer thangTruoc) {
        LocalDate thangHienTai = LocalDate.now();
        LocalDate thangTruocDo = thangHienTai.minusMonths(thangTruoc);
        List<Object[]> results;
        List<HoaDonDTO> hoaDonDTOList;
        BigDecimal totalThanhTien = BigDecimal.ZERO;
        Long totalSoLuong;
        if (thangTruoc >= 1) {
            results = hoaDonRepository.countAndSumByNgayTao(thangTruocDo, thangHienTai);
            hoaDonDTOList = mapToHoaDonDTOList(results);
            for (HoaDonDTO dto : hoaDonDTOList) {
                BigDecimal tongThanhTien = dto.getTongThanhTien();
                if (tongThanhTien != null) {
                    totalThanhTien = totalThanhTien.add(tongThanhTien);
                }
            }
            totalSoLuong = hoaDonDTOList.stream().map(HoaDonDTO::getSoLuong).mapToLong(Long::valueOf).sum();
        } else {
            results = hoaDonRepository.countAndSumByNgayTao(thangTruocDo, thangHienTai);
            hoaDonDTOList = mapToHoaDonDTOList(results);
            for (HoaDonDTO dto : hoaDonDTOList) {
                BigDecimal tongThanhTien = dto.getTongThanhTien();
                if (tongThanhTien != null) {
                    totalThanhTien = totalThanhTien.add(tongThanhTien);
                }
            }
            totalSoLuong = mapToHoaDonDTOList(results).stream().mapToLong(HoaDonDTO::getSoLuong).sum();
        }
        return new HoaDonDTO(totalSoLuong, totalThanhTien);
    }

    @Override
    public List<KhachHangResponse> findTopByOrderBySoDiemDesc(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit); // Lấy trang đầu tiên, 10 kết quả
        List<KhachHang> khachHangList = khachHangRepository.findAllByOrderBySoDiemDesc(pageable);
        return khachHangMapper.listKhachHangEntityToKhachHangResponse(khachHangList);
    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit); // Lấy trang đầu tiên, 10 kết quả
        List<Object[]> topSellingProducts = hoaDonChiTietRepository.findTopSellingProduct(pageable);
        return topSellingProductMapper.mapToObjectArrayList(topSellingProducts);
    }

    @Override
    public List<HoaDonDTO3> countAndSumByTrangThai() {
        List<Object[]> results = hoaDonRepository.countAndSumByTrangThai();
        return mapToHoaDonDTO3List(results);
    }

    @Override
    public List<HoaDonDTO> countAndSumByNgayTao(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = hoaDonRepository.countAndSumByNgayTao(startDate, endDate);
        return mapToHoaDonDTOList(results);
    }

    @Override
    public List<HoaDonDTO2> countAndSumByNgayTaoMoney(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = hoaDonRepository.countAndSumByNgayTaoMoney(startDate, endDate);
        return mapToHoaDonDTO2List(results);
    }

    @Override
    public HoaDonDTO sumHoaDon() {
        List<Object[]> results = hoaDonRepository.sumHoaDon();
        BigDecimal totalThanhTien= BigDecimal.ZERO;
        for (HoaDonDTO dto : mapToHoaDonDTOList(results)) {
            BigDecimal tongThanhTien = dto.getTongThanhTien();
            if (tongThanhTien != null) {
                totalThanhTien = totalThanhTien.add(tongThanhTien);
            }
        }
        Long totalSoLuong = mapToHoaDonDTOList(results).stream().mapToLong(HoaDonDTO::getSoLuong).sum();
        return new HoaDonDTO(totalSoLuong,totalThanhTien);
    }

    private List<HoaDonDTO> mapToHoaDonDTOList(List<Object[]> results) {
        return results.stream().map(row -> new HoaDonDTO((Long) row[0], (BigDecimal) row[1])).collect(Collectors.toList());
    }

    private List<HoaDonDTO3> mapToHoaDonDTO3List(List<Object[]> results) {
        return results.stream().map(row -> new HoaDonDTO3((ApplicationConstant.TrangThaiHoaDon) row[0], (Long) row[1], (BigDecimal) row[2])).collect(Collectors.toList());
    }
    private List<HoaDonDTO2> mapToHoaDonDTO2List(List<Object[]> results) {
        return results.stream().map(row -> new HoaDonDTO2((LocalDate) row[0], (Long) row[1], (BigDecimal) row[2])).collect(Collectors.toList());
    }
}
