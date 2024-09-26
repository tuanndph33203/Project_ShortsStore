package com.example.website_ban_ao_the_thao_ps.scheduled;

import com.example.website_ban_ao_the_thao_ps.common.ApplicationConstant;
import com.example.website_ban_ao_the_thao_ps.entity.ChuongTrinhTichDiem;
import com.example.website_ban_ao_the_thao_ps.repository.ChuongTrinhTichDiemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ChuongTrinhTichDiemScheduled {

    @Autowired
    ChuongTrinhTichDiemRepository chuongTrinhTichDiemRepository;

    @Scheduled(fixedRate = 10000)
    public void updateChuongTrinhTichDiemActive(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        List<ChuongTrinhTichDiem> chuongTrinhTichDiemList = chuongTrinhTichDiemRepository.findByThoiGianKetThucBeforeAndTrangThaiNot(currentDateTime, ApplicationConstant.TrangThaiChuongTrinhTichDiem.INACTIVE);
        for (ChuongTrinhTichDiem chuongTrinhTichDiem : chuongTrinhTichDiemList) {
            if (LocalDateTime.now().isAfter(chuongTrinhTichDiem.getThoiGianKetThuc())){
                chuongTrinhTichDiem.setTrangThai(ApplicationConstant.TrangThaiChuongTrinhTichDiem.INACTIVE);
            }
        }
        chuongTrinhTichDiemRepository.saveAll(chuongTrinhTichDiemList);
    }
}
