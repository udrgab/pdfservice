package com.project.pdfservice.controller;


import com.project.pdfservice.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {
    @Autowired
    private PdfService pdfService;

    @RequestMapping(value = "/getPdf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getPdf(@PathVariable Long id){
        return pdfService.createPdf(id).getBody();
    }
}
