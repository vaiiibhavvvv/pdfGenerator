package com.example.pdfgenerator.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.entity.Item;
import com.example.pdfgenerator.service.PdfGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class ControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PdfGeneratorService pdfGeneratorService;

    @InjectMocks
    private PdfController pdfController;

    private Invoice invoice;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(pdfController).build();

        invoice = new Invoice();
        invoice.setSeller("ABC Pvt. Ltd.");
        invoice.setSellerGstin("ABCDE12345");
        invoice.setSellerAddress("Delhi, India");
        invoice.setBuyer("Vaibhav");
        invoice.setBuyerGstin("qwert54321");
        invoice.setBuyerAddress("Bangalore India");

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("Prod 1");
        item.setQuantity("15");
        item.setRate(99);
        item.setQuantity("50");
        items.add(item);
        invoice.setItems(items);

    }

    @Test
    public void testGeneratePdf() throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.assignBytes(new ObjectMapper().writeValueAsBytes(invoice));
        Mockito.when(pdfGeneratorService.generateByteArray(any(Invoice.class))).thenReturn(outputStream);

        mockMvc.perform(MockMvcRequestBuilders.post("/pdf-generate").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(invoice))).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PDF)).andExpect(MockMvcResultMatchers.content().bytes(outputStream.toByteArray()));
    }

}
