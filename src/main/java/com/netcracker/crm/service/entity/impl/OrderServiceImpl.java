package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.dto.OrderHistoryDto;
import com.netcracker.crm.dto.row.OrderRowDto;
import com.netcracker.crm.service.entity.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final HistoryDao historyDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, ProductDao productDao,HistoryDao historyDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.productDao = productDao;
        this.historyDao = historyDao;
    }

    @Override
    @Transactional
    public Order create(OrderDto orderDto) {
        Order order = convertFromDtoToEntity(orderDto);
        orderDao.create(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.findById(id);
    }

    @Transactional
    @Override
    public List<AutocompleteDto> getAutocompleteOrder(String pattern, User user) {
        List<Order> orders;
        if (user.isContactPerson()) {
            orders = orderDao.findOrgOrdersByIdOrTitle(pattern, user.getId());
        } else {
            orders = orderDao.findByIdOrTitleByCustomer(pattern, user.getId());
        }
        List<AutocompleteDto> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(convertToAutocompleteDto(order));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getOrdersRow(OrderRowRequest orderRowRequest) {
        Map<String, Object> response = new HashMap<>();
        Long length = orderDao.getOrderRowsCount(orderRowRequest);
        response.put("length", length);
        List<Order> orders = orderDao.findOrderRows(orderRowRequest);

        List<OrderRowDto> ordersDto = new ArrayList<>();
        for (Order order : orders) {
            ordersDto.add(convertToRowDto(order));
        }
        response.put("rows", ordersDto);
        return response;
    }

    @Override
    public List<Order> findByCustomer(User customer) {
        return orderDao.findAllByCustomerId(customer.getId());
    }

    @Override
    public boolean hasCustomerProduct(Long productId, Long customerId) {
        return orderDao.hasCustomerProduct(productId, customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<OrderHistoryDto> getOrderHistory(Long id) {
        return convertToOrderHistory(historyDao.findAllByOrderId(id));
    }



    private Set<OrderHistoryDto> convertToOrderHistory(List<History> list){
        Set<OrderHistoryDto> orders = new TreeSet<>(orderHistoryDtoComparator);
        for (History history : list){
            OrderHistoryDto historyDto = new OrderHistoryDto();
            historyDto.setId(history.getId());
            historyDto.setDateChangeStatus(history.getDateChangeStatus().toString());
            historyDto.setDescChangeStatus(history.getDescChangeStatus());
            historyDto.setOldStatus(history.getOldStatus().getName());
            orders.add(historyDto);
        }
        return orders;
    }


    private Comparator<OrderHistoryDto> orderHistoryDtoComparator = (o1, o2) -> o1.getId() > o2.getId()? -1 : 1;

    private Order convertFromDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        Product product = productDao.findById(orderDto.getProductId());
        User customer = userDao.findById(orderDto.getCustomerId());

        order.setProduct(product);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.NEW);
        order.setDate(LocalDateTime.now());

        StringBuilder preferredDataTime = new StringBuilder();
        if (!orderDto.getPreferredDate().isEmpty()) {
            preferredDataTime.append(orderDto.getPreferredDate());
            if (!orderDto.getPreferredTime().isEmpty()) {
                preferredDataTime.append('T');
                preferredDataTime.append(orderDto.getPreferredTime());
            }
        }
        if (preferredDataTime.length() != 0) {
            order.setPreferedDate(LocalDateTime.parse(preferredDataTime));
        }
        return order;
    }

    private OrderRowDto convertToRowDto(Order order) {
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
        if (order.getDate() != null) {
            orderRowDto.setDateFinish(order.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (order.getPreferedDate() != null) {
            orderRowDto.setPreferredDate(order.getPreferedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return orderRowDto;
    }

    private AutocompleteDto convertToAutocompleteDto(Order order) {
        AutocompleteDto autocompleteDto = new AutocompleteDto();
        autocompleteDto.setId(order.getId());
        autocompleteDto.setValue(order.getProduct().getTitle() + " " + order.getDate().toLocalDate());
        return autocompleteDto;
    }

}
