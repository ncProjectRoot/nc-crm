package com.netcracker.crm;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NcCrmApplicationTests {


	@Autowired
	private ProductDao productDao;
	@Test
	public void contextLoads() throws SQLException {

		List<Product> products = productDao.findAllWithoutGroup();
		System.out.println(products);
		System.out.println(products.size());
	}

}
