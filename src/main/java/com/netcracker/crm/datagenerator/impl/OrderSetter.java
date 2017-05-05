package com.netcracker.crm.datagenerator.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.datagenerator.AbstractSetter;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasha on 05.05.2017.
 */
@Service
public class OrderSetter extends AbstractSetter<Order> {
    private List<User> customers;
    private List<User> csrs;
    private List<Product> products;

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> generate(int numbers) {
        List<Order> orders = new ArrayList<>();

        for (int i = 0; i < numbers; i++) {
            Order order = generateObject();
            orderDao.create(order);
            orders.add(order);
        }
        return orders;
    }

    @Override
    public Order generateObject() {
        Order order = new Order();
        order.setDate(getOrderDate());
        order.setPreferedDate(getPreferDate());
        order.setProduct(getProduct());
        order.setCustomer(getCustomer());
        OrderStatus orderStatus = getStatus();
        order.setStatus(orderStatus);
        if (orderStatus != OrderStatus.NEW) {
            order.setCsr(getCsr());
        }
        return order;
    }

    private OrderStatus getStatus(){
        return OrderStatus.values()[random.nextInt(6)];
    }

    private User getCsr(){
        return csrs.get(random.nextInt(csrs.size()));
    }

    private User getCustomer(){
        return customers.get(random.nextInt(customers.size()));
    }

    private Product getProduct(){
        return products.get(random.nextInt(products.size()));
    }


    private LocalDateTime getOrderDate(){
        return LocalDateTime.now().minusDays(random.nextInt(150) + 10);
    }

    private LocalDateTime getPreferDate(){
        return LocalDateTime.now().plusDays(random.nextInt(30));
    }


    public void setCustomers(List<User> customers) {
        this.customers = customers;
    }

    public void setCsrs(List<User> csrs) {
        this.csrs = csrs;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
