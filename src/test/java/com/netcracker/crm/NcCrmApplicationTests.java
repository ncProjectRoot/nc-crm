package com.netcracker.crm;

import com.netcracker.crm.email.entity.Complaint;
import com.netcracker.crm.email.entity.User;
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
	private ComplaintMailSender complaintMailSender;

	@Autowired
	private MassiveEmailSender massiveEmailSender;
	@Test
	public void contextLoads() throws MessagingException {
		User user = new User("Pasha", "Kurachenko" , "oxeygenoxeygen@gmail.com");
		complaintMailSender.sendMail(new Complaint(1, "sssd","accept" , user));
		String[] arr = {"oxeygenoxeygen@gmail.com", "csiponworkgroup@gmail.com"};
//
//		String information = "Information is that which informs. In other words,\n" +
//				" it is the answer to a question of some kind.\n" +
//				" It is thus related to data and knowledge, as data\n" +
//				" represents values attributed to parameters, \n" +
//				"and knowledge signifies understanding of real things\n" +
//				" or abstract concepts. As it regards data, \n" +
//				"the information's existence is not necessarily \n" +
//				"coupled to an observer (it exists beyond an event horizon,\n" +
//				" for example), while in the case of knowledge,\n" +
//				" the information requires a cognitive observer.";
//		massiveEmailSender.sendMails(arr, "Look what we can offer for you", information);
	}
}
