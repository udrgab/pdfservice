package com.project.pdfservice.service;



import org.springframework.http.HttpEntity;


public interface PdfService {
    HttpEntity<byte[]> createPdf(Long id);



}
