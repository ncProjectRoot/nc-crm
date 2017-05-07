package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.dto.row.OrderRowDto;
import com.netcracker.crm.service.entity.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.05.2017
 */

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public Order persist(OrderDto orderDto) {
        Order order = convertFromDtoToEntity(orderDto);
        orderDao.create(order);
        return order;
    }

    public List<Order> findByCustomerId(Long id) {
        return orderDao.findAllByCustomerId(id);
    }

    @Override
    public Map<String, Object> getOrderRow(OrderRowRequest orderRowRequest) throws IOException {
        Map<String, Object> response = new HashMap<>();
        Long length = orderDao.getOrderRowsCount(orderRowRequest);
        response.put("length", length);
        List<Order> orders = orderDao.findOrderRows(orderRowRequest);

        List<OrderRowDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            ordersDto.add(convertToEntity(order));
        }
        response.put("orders", ordersDto);
        return response;
    }


    private Order convertFromDtoToEntity(OrderDto orderDto){
        Order order = new Order();
        Product product = productDao.findById(orderDto.getProductId());
        User customer = userDao.findById(orderDto.getCustomerId());

        order.setProduct(product);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        order.setDate(LocalDateTime.now());
        return order;
    }

    private OrderRowDto convertToEntity(Order order) {
        OrderRowDto orderRowDto = new OrderRowDto();
        orderRowDto.setId(order.getId());
        orderRowDto.setStatus(order.getStatus().getName());
        orderRowDto.setProductId(order.getProduct().getId());
        orderRowDto.setProductTitle(order.getProduct().getTitle());
        orderRowDto.setProductStatus(order.getProduct().getStatus().getName());
        orderRowDto.setCustomer(order.getCustomer().getId());
        if (order.getCsr() != null) {
            orderRowDto.setCsr(order.getCsr().getId());
        }
        orderRowDto.setDateFinish(order.getDate().toString());
        orderRowDto.setPreferredDate(order.getPreferedDate().toString());
        return orderRowDto;
    }

}
