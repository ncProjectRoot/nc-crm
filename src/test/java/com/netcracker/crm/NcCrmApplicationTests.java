package com.netcracker.crm;

import com.netcracker.crm.email.senders.ComplaintMailSender;
import com.netcracker.crm.email.senders.MassiveEmailSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NcCrmApplicationTests {

	@Autowired
	private MassiveEmailSender massiveEmailSender;
	@Test
	public void contextLoads() throws MessagingException {

	}
}
