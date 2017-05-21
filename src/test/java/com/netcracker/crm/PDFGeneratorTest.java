package com.netcracker.crm;

import com.netcracker.crm.domain.model.*;
import org.junit.Test;

import java.time.LocalDateTime;

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
        Discount discount = new Discount();

        discount.setId(1488l);
        discount.setPercentage(5.5);
        product.setTitle("Internet");
        product.setDescription("25 mb/s");
        product.setDefaultPrice(55.5);
        product.setDiscount(discount);

        order.setProduct(product);
        order.setId(214748l);
        order.setDate(LocalDateTime.MAX);
        order.setCustomer(user);
        order.setStatus(OrderStatus.DISABLED);

//        PDFGenerator pdfGenerator = new PDFGenerator();
//        pdfGenerator.generate(order);

    }
}
