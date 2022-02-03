package com.project.pdfservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableFeignClients
@RestController
public class PdfserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdfserviceApplication.class, args);
    }


}
