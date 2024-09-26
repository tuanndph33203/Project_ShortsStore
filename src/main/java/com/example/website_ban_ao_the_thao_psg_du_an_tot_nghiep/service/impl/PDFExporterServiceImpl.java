package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.config.PDFExporterConfig;
import com.example.website_ban_ao_the_thao_ps.entity.HoaDon;
import com.example.website_ban_ao_the_thao_ps.exception.NotFoundException;
import com.example.website_ban_ao_the_thao_ps.repository.HoaDonRepository;
import com.example.website_ban_ao_the_thao_ps.service.PDFExporterService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PDFExporterServiceImpl implements PDFExporterService {

    @Autowired
    private PDFExporterConfig pdfExporterConfig;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public void exportPDF(HttpServletResponse httpServletResponse, Integer id) {
        HoaDon hoaDon = this.hoaDonRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy id"));
        try {
            this.pdfExporterConfig.export(httpServletResponse, hoaDon);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
