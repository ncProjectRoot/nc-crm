package com.netcracker.crm.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.netcracker.crm.domain.model.Order;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

public class PDFGenerator {

    final static Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    final static Font SIMPLE_FONT = new Font(Font.FontFamily.HELVETICA, 12);

    public void generate(Order order) throws DocumentException, IOException, MessagingException {

        new PDFGenerator().createPdf(generateFileName(), order.getProduct().getTitle(), order.getProduct().getDescription(), order.getProduct().getDefaultPrice(), order.getProduct().getDiscount().getPercentage(), order.getId(), order.getDate(), order.getCustomer().getFirstName(), order.getCustomer().getLastName(), order.getCustomer().getPhone(), order.getCustomer().getEmail());
    }

    private void createPdf(String filename, String name, String description, double price, double discount,Long orderNum, LocalDate date, String fName, String lName, String phoneNumber, String email)
            throws DocumentException, IOException {

        Document document = new Document();

        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        //insert order details
        document.add(createHeader(orderNum));
        document.add(createOrderDate(date));

        //insert table
        document.add(createTable(name, description, String.valueOf(price)));

        //insert customer's order details
        document.add(createOrderInfoTable(fName, lName, price, discount, phoneNumber, email));

        document.close();
    }

    private Paragraph createHeader(Long orderNum) {

        Paragraph header = new Paragraph(null, BOLD_FONT);
        header.add("Details for Order #" + orderNum);
        header.setFont(BOLD_FONT);
        header.setAlignment(Element.ALIGN_CENTER);
        return header;

    }

    private Paragraph createOrderDate(LocalDate date) {

        Paragraph orderDate = new Paragraph(null, SIMPLE_FONT);
        orderDate.add("Order placed: " + date);
        orderDate.setSpacingAfter(15);
        return orderDate;

    }


    private PdfPTable createOrderInfoTable(String fName, String lName, double price, double discount, String phone, String email) {
        PdfPTable infoTable = new PdfPTable(3);

        infoTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        infoTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(new Paragraph("Customer information:", BOLD_FONT));
        infoTable.addCell("");
        infoTable.addCell(new Paragraph("Subtotal: " + price + " UAH", SIMPLE_FONT));
        infoTable.addCell(new Paragraph(fName + " " + lName, SIMPLE_FONT));
        infoTable.addCell("");
        infoTable.addCell(new Paragraph("Discount: " + discount + " UAH", SIMPLE_FONT));
        infoTable.addCell(new Paragraph(phone));
        infoTable.addCell("");
        infoTable.addCell(new Paragraph("Total order: " + (price - discount) + " UAH", BOLD_FONT));
        infoTable.addCell(email);
        infoTable.addCell("");
        infoTable.addCell("");

        return  infoTable;

    }
    private PdfPTable createTable(String name, String description, String price) {

        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell = new PdfPCell();

        cell.setRowspan(3);

        cell.setMinimumHeight(50);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("Name");
        table.addCell("Description");
        table.addCell("Price, UAH");

        cell.setRowspan(3);
        table.addCell(name);
        table.addCell(description);
        table.addCell(price);
        table.setSpacingAfter(15);

        return table;

    }

    private String generateFileName() {
        UUID fileName = UUID.randomUUID();

        return fileName.toString() + ".pdf";
    }
}
