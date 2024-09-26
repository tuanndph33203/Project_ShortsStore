package com.example.website_ban_ao_the_thao_ps.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponsePayment {
    private String status;
    private String message;
    private String URL;
}
