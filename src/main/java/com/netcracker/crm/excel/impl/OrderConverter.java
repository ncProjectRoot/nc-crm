package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by AN on 03.05.2017.
 */
public class OrderConverter {

    LinkedHashMap<String, List<?>> convertOrders(List<Order> orders){
        List<String> customer_fullName = new ArrayList<>();
        List<Long> order_id = new ArrayList<>();
        List<LocalDateTime> order_date_finish = new ArrayList<>();
        List<LocalDateTime> order_preffered_date = new ArrayList<>();
        List<String> order_status = new ArrayList<>();
        List<String> product_title = new ArrayList<>();
        List<Double> product_default_price = new ArrayList<>();
        List<Double> product_discount = new ArrayList<>();

        for (Order order: orders) {
            String fullName = getFullName(order);
            customer_fullName.add(fullName);
            order_id.add(order.getId());
            order_date_finish.add(order.getDate());
            order_preffered_date.add(order.getPreferedDate());
            order_status.add(order.getStatus().getName());
            product_title.add(order.getProduct().getTitle());
            product_default_price.add(order.getProduct().getDefaultPrice());
            if(order.getProduct().getDiscount() != null)
            product_discount.add(order.getProduct().getDiscount().getPercentage());
            else product_discount.add(null);
        }

        LinkedHashMap<String, List<?>> data = new LinkedHashMap<>();

        data.put("Full_name", customer_fullName);
        data.put("Order_id", order_id);
        data.put("Order_date_finish", order_date_finish);
        data.put("Order_preffered_date", order_preffered_date);
        data.put("Order_status", order_status);
        data.put("Product_title", product_title);
        data.put("Product_default_price", product_default_price);
        data.put("Product_discount_percentage", product_discount);
        return data;
    }

    private String getFullName(Order order){
        return order.getCustomer().getFirstName()
                +" " + order.getCustomer().getMiddleName()
                +" " + order.getCustomer().getLastName();
    }
}
