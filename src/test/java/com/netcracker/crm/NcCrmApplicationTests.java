package com.netcracker.crm;


import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.datagenerator.GeneratorDbData;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NcCrmApplicationTests {

	@Autowired
	private GeneratorDbData generator;

	@Autowired
	private OrderDao orderDao;
	@Test
	public void contextLoads() throws SQLException {

		for (Order order : orderDao.findAllByCsrId(OrderStatus.REQUEST_TO_PAUSE, 5023L)){
			System.out.println(order);
		}
	}

	@Test
	public void generateTestDataForDB() throws SQLException {
		generator.generateDataForDB(1);
	}
}
