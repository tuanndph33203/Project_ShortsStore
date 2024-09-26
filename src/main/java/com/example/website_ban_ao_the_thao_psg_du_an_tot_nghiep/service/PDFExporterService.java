package com.example.website_ban_ao_the_thao_ps.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface PDFExporterService {

    void exportPDF(HttpServletResponse httpServletResponse, Integer id);
}
