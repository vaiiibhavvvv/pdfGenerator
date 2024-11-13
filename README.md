# PDF Generator API

A Spring Boot application that provides a REST API to generate PDF documents using Java template engines like Thymeleaf or iText. The generated PDF is stored locally and can be redownloaded for repeated requests with the same data.

## Features
- **Generate PDF**: Accepts data via a POST request and generates a PDF using iText.
- **Download PDF**: Allows downloading the generated PDF.
- **Store PDF**: PDFs are stored on local storage for future access without regeneration.

## API Documentation

### 1. Generate PDF
**Endpoint**: `POST /generate-pdf`

#### Request Body:
```json
{
  "seller": "ABC Pvt. Ltd.",
  "sellerGstin": "ABCDE12345",
  "sellerAddress": "New Delhi, India",
  "buyer": "Vaibhav",
  "buyerGstin": "QWERTY54321",
  "buyerAddress": "Bangalore, India",
  "items": [
    {
      "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 123.00,
      "amount": 1476.00
    }
  ]
}
```

#### Response:
Returns a status indicating whether the PDF was generated successfully and provides a download link.

### 2. Download PDF
**Endpoint**: `GET /download-pdf/{filename}`

#### Parameters:
- `filename`: The name of the file to download.

#### Response:
The generated PDF file will be sent as a download.

## Technology Stack:
- **Java**
- **Spring Boot** (Web)
- **iText (Template Engine for PDF generation)
- **Local File Storage** for storing PDFs
