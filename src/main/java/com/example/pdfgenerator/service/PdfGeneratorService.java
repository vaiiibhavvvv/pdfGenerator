package com.example.pdfgenerator.service;

import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.entity.Item;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class PdfGeneratorService {

    private static final float[] HEADER_COLUMN_WIDTHS = {280F,280F};
    private static final float[]   PRODUCT_INFO_COLUMN_WIDTHS = {140F,140F,140F,140F};

    //Generate byte array of pdf content based on the provided invoice data
    public ByteArrayOutputStream generateByteArray(Invoice invoice){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try(PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc)){
            PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
            document.setFont(font);
            addHeader(document,invoice);
            addPRoductInfoTable(document,invoice.getItems());

        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("PDF generated successfully");
        return outputStream;


}

private void addHeader(Document document, Invoice invoice){
    Table headerTable = new Table(PdfGeneratorService.HEADER_COLUMN_WIDTHS);
    String sellerInfo = formatPartyInfo("Seller",invoice.getSeller(),invoice.getBuyerAddress(),invoice.getSellerGstin());
    String buyerInfo = formatPartyInfo("Buyer",invoice.getBuyer(),invoice.getBuyerAddress(),invoice.getBuyerGstin());

    headerTable.addCell(createCell(sellerInfo,30));
    headerTable.addCell(createCell(buyerInfo,30));

    document.add(headerTable);
}

private String formatPartyInfo(String role,String name,String address, String gstin){
    return String.format("%s:\n%s\n%s\nGSTIN: %s", role, name, address, gstin);
    }
    private void addPRoductInfoTable(Document document, List<Item> items){
        Table productTable = new Table(PRODUCT_INFO_COLUMN_WIDTHS);
        productTable.setTextAlignment(TextAlignment.CENTER);

        productTable.addCell(createCell("Item"));
        productTable.addCell(createCell("Quantity"));
        productTable.addCell(createCell("Rate"));
        productTable.addCell(createCell("Amount"));

        for(Item item : items){
            productTable.addCell(createCell(item.getName()));
            productTable.addCell(createCell(item.getQuantity()));
            productTable.addCell(createCell(String.valueOf(item.getRate())));
            productTable.addCell(createCell(String.valueOf(item.getAmount())));
        }
        document.add(productTable);
    }

    private Cell createCell(String content) {
        return new Cell().add(new Paragraph(content));
    }

    // Helper method to create a cell with specific padding
    private Cell createCell(String content, float padding) {
        return new Cell().add(new Paragraph(content)).setPadding(padding);
    }
}