package com.netcracker.crm;

import com.netcracker.crm.email.builder.EmailBuilder;
import com.netcracker.crm.email.entity.Complaint;
import com.netcracker.crm.email.sender.ComplaintMailSender;
import com.netcracker.crm.email.sender.TypeComplaint;
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
	private ComplaintMailSender complaintMailSender;
	@Test
	public void contextLoads() throws MessagingException {
		complaintMailSender.sendMail(new Complaint(1, "sssd","active" ,"oxeygenoxeygen@gmail.com"),TypeComplaint.ACCEPT);
	}
}
