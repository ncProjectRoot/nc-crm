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
        user.setEmail("ANYADDRESS@gmail.com");
        user.setFirstName("John");
        user.setLastName("Snow");

        Order order = new Order();
        Product product = new Product();
        product.setTitle("Internet");
        order.setProduct(product);
        order.setCustomer(user);
        order.setStatus(OrderStatus.DISABLED);
        EmailMap emailMap = new EmailMap(EmailType.ORDER_STATUS);
        emailMap.put("order", order);
        orderStatusEmailSender.send(emailMap);

    }


    @Test
    public void complaintTest() throws Exception {
        Complaint complaint = new Complaint();
        User user = new User();
        user.setEmail("ANYADDRESS@gmail.com");
        user.setFirstName("John");
        user.setLastName("Snow");
        complaint.setCustomer(user);
        complaint.setStatus(ComplaintStatus.CLOSED);
        complaint.setTitle("AnyTitle");
        EmailMap emailMap = new EmailMap(EmailType.COMPLAINT);
        emailMap.put("complaint", complaint);

        complaintMailSender.send(emailMap);
    }


    @Test
    public void massiveTest() throws Exception {
        String[] addresses = {"ANYADDRESS1@gmail.com", "ANYADDRESS2@gmail.com"};
        String information = "Some information";
        String subject = "Interesting subject";
        EmailMap emailMap = new EmailMap(EmailType.MASSIVE);
        emailMap.put("receivers", addresses);
        emailMap.put("subject", subject);
        emailMap.put("body", information);
        massiveEmailSender.send(emailMap);
    }


    @Test
    public void regSuccessTest() throws Exception {
        User user = new User();
        user.setEmail("ANYADDRESS@gmail.com");
        user.setFirstName("John");
        user.setLastName("Snow");
        EmailMap emailMap = new EmailMap(EmailType.REGISTRATION);
        emailMap.put("user", user);
        regSuccessEmailSender.send(emailMap);
    }

}