####PDF Generation API using Java Template Engine

This is a Spring Boot application that provides a REST API to generate a PDF document using the provided data. The PDF is stored locally and can be re-downloaded without regeneration if the same data is provided.

####Features
Generate PDF: Accepts data via a POST request and generates a PDF using Thymeleaf or iText.
Download PDF: Ability to download the generated PDF.
Store PDF: The PDF is stored on local storage and can be re-downloaded for repeated requests with the same data.
API Documentation
1. Generate PDF
Endpoint: POST /generate-pdf

####Request Body:

json
Copy code
{
  "seller": "XYZ Pvt. Ltd.",
  "sellerGstin": "29AABBCCDD121ZD",
  "sellerAddress": "New Delhi, India",
  "buyer": "Vedant Computers",
  "buyerGstin": "29AABBCCDD131ZD",
  "buyerAddress": "New Delhi, India",
  "items": [
    {
     "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 123.00,
      "amount": 1476.00
    }
  ]
}
Response:

Returns a status indicating the PDF generation was successful and provides a download link.
2. Download PDF
Endpoint: GET /download-pdf/{filename}

Parameters: filename (Name of the file to download)
Response:

The generated PDF file is sent as a download.
####Technology Stack
Java 
Spring Boot (Web,iText)
Local File Storage for storing PDFs
