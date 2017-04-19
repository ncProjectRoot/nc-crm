package com.netcracker.crm.email.senders;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;


/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 20.04.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailSendersTest {

    @Autowired
    private ChangeStatusServiceEmailSender changeStatusServiceEmailSender;
    @Autowired
    private ComplaintMailSender complaintMailSender;
    @Autowired
    private MassiveEmailSender massiveEmailSender;
    @Autowired
    private RegSuccessEmailSender regSuccessEmailSender;

    @Test
    public void changeStatusServiceTest() throws Exception {
        User user = new User();
        user.setEmail("ANYEMAILTOTEST@gmail.com");
        user.setName("John");
        user.setSurname("Snow");

        ServiceEntity product = new ServiceEntity();
        product.setTitle("Internet");
        product.setStatus("Deactivated");
        product.setType("service");

        changeStatusServiceEmailSender.send(user, product);

    }


    @Test
    public void complaintTest() throws Exception {
        Complaint complaint = new Complaint();
        User user = new User();
        user.setEmail("ANYEMAILTOTEST@gmail.com");
        user.setName("John");
        user.setSurname("Snow");
        complaint.setSender(user);
        complaint.setStatus("accept");
        complaint.setName("AnyName");

        complaintMailSender.sendMail(complaint);
    }


    @Test
    public void massiveTest() throws Exception {
        String[] addresses = {"ANYEMAILTOTEST@gmail.com", "ANYEMAILTOTEST2@gmail.com"};
        String information = "Some information";
        String subject = "Interesting subject";

        massiveEmailSender.sendMails(addresses, subject, information);
    }


    @Test
    public void regSuccessTest() throws Exception {
        User user = new User();
        user.setEmail("ANYEMAILTOTEST@gmail.com");
        user.setName("John");
        user.setSurname("Snow");

        regSuccessEmailSender.send(user);
    }

}