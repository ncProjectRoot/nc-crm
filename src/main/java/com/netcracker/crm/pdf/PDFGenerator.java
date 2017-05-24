package com.netcracker.crm.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class PDFGenerator {

    final static Font BOLD_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    final static Font SIMPLE_FONT = new Font(Font.FontFamily.HELVETICA, 12);

    public InputStream generate(Order order, User user, Product product, Discount discount) throws DocumentException, IOException, MessagingException {

        String test = generateFileName();
        return createPdf(test, product.getTitle(), product.getDescription(), product.getDefaultPrice(), discount.getPercentage(), order.getId(), order.getDate(), user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail());

    }

    public InputStream createPdf(String filename, String name, String description, double price, double discount, Long orderNum, LocalDateTime date, String fName, String lName, String phoneNumber, String email)
            throws DocumentException, IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {

            Document document = new Document();

            try {
                PdfWriter.getInstance(document, stream);
            } catch (DocumentException e) {
            }

            document.open();

            //insert order details
            document.add(createHeader(orderNum));
            document.add(createOrderDate(date));

            //insert table
            document.add(createTable(name, description, String.valueOf(price)));

            //insert customer's order details
            document.add(createOrderInfoTable(fName, lName, price, discount, phoneNumber, email));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(stream.toByteArray());
    }

    private Paragraph createHeader(Long orderNum) {

        Paragraph header = new Paragraph(null, BOLD_FONT);
        header.add("Details for Order #" + orderNum);
        header.setFont(BOLD_FONT);
        header.setAlignment(Element.ALIGN_CENTER);
        return header;

    }

    private Paragraph createOrderDate(LocalDateTime date) {

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
        infoTable.addCell(new Paragraph("Discount: " + (price - (price * discount / 100)) + " UAH", SIMPLE_FONT));
        infoTable.addCell(new Paragraph(phone));
        infoTable.addCell("");
        infoTable.addCell(new Paragraph("Total order: " + (price * discount / 100) + " UAH", BOLD_FONT));
        infoTable.addCell(email);
        infoTable.addCell("");
        infoTable.addCell("");

        return infoTable;

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

    public String generateFileName() {
        UUID fileName = UUID.randomUUID();

        return fileName.toString() + ".pdf";
    }
}