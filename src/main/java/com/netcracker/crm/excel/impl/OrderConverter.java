package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.DateSelection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * Created by AN on 03.05.2017.
 */
public class OrderConverter {

    Map<String, List<?>> convertAllOrdersOfCustomerBetweenDatesOfCSR(List<Order> orders){
        List<String> customer_fullName = new ArrayList<>();
        List<Long> order_id = new ArrayList<>();
        List<LocalDateTime> order_date = new ArrayList<>();
        List<LocalDateTime> order_preffered_date = new ArrayList<>();
        List<String> order_status = new ArrayList<>();
        List<String> product_title = new ArrayList<>();
        List<Double> product_default_price = new ArrayList<>();
        List<Double> product_discount = new ArrayList<>();

        for (Order order: orders) {
            String fullName = order.getCustomer().getFirstName();
            fullName +=" " + order.getCustomer().getMiddleName();
            fullName +=" " + order.getCustomer().getLastName();
            customer_fullName.add(fullName);
            order_id.add(order.getId());
            order_date.add(order.getDate());
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
        data.put("Order_date", order_date);
        data.put("Order_preffered", order_preffered_date);
        data.put("Order_status", order_status);
        data.put("Product_title", product_title);
        data.put("Product_default_price", product_default_price);
        data.put("Product_discount_percentage", product_discount);
        return data;
    }

    public Map<String, List<?>> convertAllOrdersOfManyCustomersBetweenDatesOfCSR(List<Order> orders){
        return convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
    }

    public AdditionalData numberOfOrdersInDates(List<Order> orders, LocalDateTime date_start, LocalDateTime date_finish) {
        Period difference = Period.between(date_start.toLocalDate(), date_finish.toLocalDate());
        DateSelection dateSelection;
        if (difference.getYears() > 0) dateSelection = DateSelection.YEAR;
        else if(difference.getMonths() > 0) dateSelection = DateSelection.MONTH;
        else dateSelection = DateSelection.DAY;
        AdditionalData additionalData = countByDate_addData(orders, dateSelection);
        additionalData.setDataName("Number of orders made in dates");
        return additionalData;
    }

    private AdditionalData countByDate_addData(List<Order> orders, DateSelection dateSelection){
        LinkedHashMap<String, List<?>> data = new LinkedHashMap<>();
        String cutomer_name_title = "Full name";
        List<String> customer_names = new ArrayList<>();
        Map<String, List<Integer>> monthYearValues = new LinkedHashMap();

        for (Order order: orders){
            String fullName = order.getCustomer().getFirstName()+" "
                    + order.getCustomer().getMiddleName()+" "
                    + order.getCustomer().getLastName();
            if(!customer_names.contains(fullName)) customer_names.add(fullName);
        }

        for (Order order: orders) {
            String fullName = order.getCustomer().getFirstName()+" "
                    + order.getCustomer().getMiddleName()+" "
                    + order.getCustomer().getLastName();
            int index = customer_names.indexOf(fullName);
            LocalDate localDateTime = order.getDate().toLocalDate();
            String certainDate;
            switch (dateSelection){
                case DAY: {
                    certainDate = localDateTime.toString();
                    break;
                }
                case MONTH: {
                    certainDate = localDateTime.toString().substring(0, 7);
                    break;
                }
                case YEAR: {
                    certainDate = localDateTime.toString().substring(0, 4);
                    break;
                }
                default: certainDate = localDateTime.toString();
            }
            if(!monthYearValues.containsKey(certainDate)){
                Integer[] integers = new Integer[customer_names.size()];
                Arrays.fill(integers, 0);
                monthYearValues.put(certainDate, Arrays.asList(integers));
            }
            int value = monthYearValues.get(certainDate).get(index);
            value +=1;
            monthYearValues.get(certainDate).set(index, value);
        }
        data.put(cutomer_name_title, customer_names);
        data.putAll(monthYearValues);
        return new AdditionalData(data);
    }
}
