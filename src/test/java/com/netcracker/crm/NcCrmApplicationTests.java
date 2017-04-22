package com.netcracker.crm;

import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NcCrmApplicationTests {


	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	public void contextLoads() throws SQLException {
		User user = new User();
		user.setFirstName("John");
		user.setMiddleName("Doe");
		user.setLastName("Doevich");
		user.setEmail("johndoe@gmail.com");
		user.setPassword(passwordEncoder.encode("123123"));
		user.setUserRole(UserRole.ROLE_ADMIN);
		user.setEnable(true);

		long id = userDao.create(user);
		System.out.println(id);
	}

}
