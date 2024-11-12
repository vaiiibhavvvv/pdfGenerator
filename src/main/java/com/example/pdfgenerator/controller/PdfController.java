package com.example.pdfgenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.service.PdfGeneratorService;
import com.itextpdf.io.source.ByteArrayOutputStream;


@RestController
public class PdfController{

   @Autowired
    private PdfGeneratorService pdfGeneratorService;

   @PostMapping("/pdf-generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Invoice invoice){
       if (invoice==null || invoice.getItems()==null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
       ByteArrayOutputStream outputStream = this.pdfGeneratorService.generateByteArray(invoice);
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_PDF);
       headers.setContentDisposition(ContentDisposition.builder("attachment").filename("invoice.pdf").build());
       return new ResponseEntity<>(outputStream.toByteArray(),headers,HttpStatus.OK);
   }


}