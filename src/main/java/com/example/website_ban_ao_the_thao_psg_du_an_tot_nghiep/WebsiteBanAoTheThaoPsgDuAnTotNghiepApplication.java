package com.example.website_ban_ao_the_thao_ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
public class WebsiteBanAoTheThaoPsgDuAnTotNghiepApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsiteBanAoTheThaoPsgDuAnTotNghiepApplication.class, args);
    }

}
