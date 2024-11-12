package com.example.pdfgenerator;

import java.util.ArrayList;
import java.util.List;


import com.example.pdfgenerator.controller.PdfController;
import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class PdfgeneratorApplication implements CommandLineRunner {


	@Autowired
	private PdfController pdfController;
	public static void main(String[] args) {
		SpringApplication.run(PdfgeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Invoice invoice = new Invoice();
		invoice.setSeller("ABC Pvt. Ltd.");
		invoice.setSellerGstin("ABCDE12345");
		invoice.setSellerAddress("Delhi India");
		invoice.setBuyer("Vaibhav");
		invoice.setBuyerGstin("QWERT54321");
		invoice.setBuyerAddress("Bangalore India");

		List<Item> items = new ArrayList<>();
		Item item = new Item();
		item.setName("Product 1");
		item.setQuantity("5 Nos");
		item.setRate(999);
		item.setAmount(66);
		items.add(item);
		invoice.setItems(items);

		ResponseEntity<byte[]> output = pdfController.generatePdf(invoice);
		System.out.println(output.getStatusCode());

	}
}
