package com.example.hospital.patient.wx.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@EnableAsync
@EnableScheduling
@ServletComponentScan
@ComponentScan("com.example.*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class PatientWxApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientWxApiApplication.class, args);
    }

}
