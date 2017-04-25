package com.netcracker.crm.email.senders;

import com.netcracker.crm.domain.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 20.04.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSendersTest {

    @Autowired
    private OrderStatusEmailSender orderStatusEmailSender;
    @Autowired
    private ComplaintMailSender complaintMailSender;
    @Autowired
    private MassiveEmailSender massiveEmailSender;
    @Autowired
    private RegSuccessEmailSender regSuccessEmailSender;

    @Test
    public void orderStatusTest() throws Exception {
        User user = new User();
        user.setEmail("ANYEMAILTOTEST@gmail.com");
        user.setFirstName("John");
        user.setLastName("Snow");

        Order order = new Order();
        Product product = new Product();
        product.setTitle("Internet");
        order.setProduct(product);
        order.setCustomer(user);
        order.setStatus(OrderStatus.DISABLED);

        orderStatusEmailSender.send(order);

    }


    @Test
    public void complaintTest() throws Exception {
        Complaint complaint = new Complaint();
        User user = new User();
        user.setEmail("ANYEMAILTOTEST@gmail.com");
        user.setFirstName("John");
        user.setLastName("Snow");
        complaint.setCustomer(user);
        complaint.setStatus(ComplaintStatus.CLOSED);
        complaint.setTitle("AnyTitle");

        complaintMailSender.send(complaint);
    }


    @Test
    public void massiveTest() throws Exception {
        String[] addresses = {"ANYEMAILTOTEST@gmail.com", "ANYEMAILTOTEST2@gmail.com"};
        String information = "Some information";
        String subject = "Interesting subject";

        massiveEmailSender.send(addresses, subject, information);
    }


    @Test
    public void regSuccessTest() throws Exception {
        User user = new User();
        user.setEmail("ANYEMAILTOTEST@gmail.com");
        user.setFirstName("John");
        user.setLastName("Snow");

        regSuccessEmailSender.send(user);
    }

}