package com.netcracker.crm;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.pdf.PDFGenerator;
import org.junit.Test;

import java.time.LocalDate;

public class PDFGeneratorTest {

    @Test
    public void orderPDFGeneratorTest() throws Exception {
        User user = new User();
        user.setEmail("elon@spacex.com");
        user.setFirstName("Elon");
        user.setLastName("Musk");
        user.setPhone("0935168465");

        Order order = new Order();
        Product product = new Product();
        product.setTitle("Internet");
        product.setDescription("25 mb/s");
        product.setDefaultPrice(55.5);
        order.setProduct(product);
        order.setId(214748l);
        order.setDate(LocalDate.MAX);
        order.setCustomer(user);
        order.setStatus(OrderStatus.DISABLED);

        PDFGenerator pdfTest = new PDFGenerator();
        pdfTest.generate(order);

    }
}
