
package com.example.website_ban_ao_the_thao_ps.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HoaDonDTO2 {

    private LocalDate ngayTao;
    private long soLuong;
    private BigDecimal tongThanhTien;
    // Getters and setters
}
