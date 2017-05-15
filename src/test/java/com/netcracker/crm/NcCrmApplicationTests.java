package com.netcracker.crm;


import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.datagenerator.GeneratorDbData;
import com.netcracker.crm.service.entity.OrderService;
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
	private HistoryDao historyDao;
	@Autowired
	private OrderService orderService;
	@Test
	public void contextLoads() throws SQLException {
		System.out.println(orderService.getOrderHistory(19605L));
	}

	@Test
	public void generateTestDataForDB() throws SQLException {
		generator.generateDataForDB(1);
	}
}
