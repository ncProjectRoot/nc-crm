package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by AN on 03.05.2017.
 */
public class OrderConverter {
    private Map<String, List<?>> lastAdditionalData;
    private String  lastAdditionalDataName;

    Map<String, List<?>> convertAllOrdersOfCustomerBetweenDatesOfCSR(List<Order> orders){
        List<Long> order_id = new ArrayList<>();
        List<LocalDateTime> order_date = new ArrayList<>();
        List<LocalDateTime> order_preffered_date = new ArrayList<>();
        List<String> order_status = new ArrayList<>();
        List<String> product_title = new ArrayList<>();
        List<Double> product_default_price = new ArrayList<>();
        List<Double> product_discount = new ArrayList<>();

        for (Order order: orders) {
            order_id.add(order.getId());
            order_date.add(order.getDate());
            order_preffered_date.add(order.getPreferedDate());
            order_status.add(order.getStatus().getName());
            product_title.add(order.getProduct().getTitle());
            product_default_price.add(order.getProduct().getDefaultPrice());
            product_discount.add(order.getProduct().getDiscount().getPercentage());
        }

        LinkedHashMap<String, List<?>> data = new LinkedHashMap<>();

        data.put("Order_id", order_id);
        data.put("Order_date", order_date);
        data.put("Order_preffered", order_preffered_date);
        data.put("Order_status", order_status);
        data.put("Product_title", product_title);
        data.put("Product_default_price", product_default_price);
        data.put("Product_discount_percentage", product_discount);
        return data;
    }

    public Map<String, List<?>> convertAllOrdersOfManyCustomersBetweenDatesOfCSR(List<Order> orders){
        LinkedHashMap<String, List<?>> data = new LinkedHashMap<>();

        List<String> customer_fullName = new ArrayList<>();

        for (Order order: orders) {
            String fullName = order.getCustomer().getFirstName();
            fullName +=" " + order.getCustomer().getMiddleName();
            fullName +=" " + order.getCustomer().getLastName();
            customer_fullName.add(fullName);
        }
        data.put("Full_name", customer_fullName);

        data.putAll(convertAllOrdersOfCustomerBetweenDatesOfCSR(orders));
        return data;
    }

   /* public Map<String, List<?>> convertAllOrdersOfCustomerBetweenDatesOfCSR_addData(List<Order> orders){

    }

    private Map<String, List<?>> countByMonthAndYear_addData(List<Order> orders, Method method) throws InvocationTargetException, IllegalAccessException {
        Map<String, List<?>> additinonalData = new LinkedHashMap<>();

        List<String> customer_name = new ArrayList<>();
        Map<String, List<String>> monthYearValues = new LinkedHashMap();

        for (Order order: orders){
            String fullName = order.getCustomer().getFirstName()+" "
                    + order.getCustomer().getMiddleName()+" "
                    + order.getCustomer().getLastName();
            if(!customer_name.contains(fullName)) customer_name.add(fullName);
        }

        for (Order order: orders) {
            String fullName = order.getCustomer().getFirstName()+" "
                    + order.getCustomer().getMiddleName()+" "
                    + order.getCustomer().getLastName();
            customer_name.add(fullName);
            LocalDateTime localDateTime = order.getDate();
            String certainMonthYear = localDateTime.getMonthValue()+"-"
                    +localDateTime.getYear();
            monthYearValues.put(certainMonthYear)


        }
    }*/

}
