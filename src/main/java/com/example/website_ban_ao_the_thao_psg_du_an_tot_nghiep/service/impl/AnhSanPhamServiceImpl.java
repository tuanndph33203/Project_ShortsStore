package com.example.website_ban_ao_the_thao_ps.service.impl;

import com.example.website_ban_ao_the_thao_ps.model.mapper.AnhSanPhamMapper;
import com.example.website_ban_ao_the_thao_ps.repository.AnhSanPhamRepository;
import com.example.website_ban_ao_the_thao_ps.service.AnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnhSanPhamServiceImpl implements AnhSanPhamService {

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepository;

    @Override
    public void delete(Integer id) {
        this.anhSanPhamRepository.deleteById(id);
    }
}
