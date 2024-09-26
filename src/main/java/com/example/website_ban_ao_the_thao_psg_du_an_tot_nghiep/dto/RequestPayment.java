package com.example.website_ban_ao_the_thao_ps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  RequestPayment {
    private String maHoaDon;
    private Long totalPrice;
}