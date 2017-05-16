package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.DateSelection;
import com.netcracker.crm.excel.additional.FirstColumnSelection;

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
        List<LocalDateTime> order_date_finish = new ArrayList<>();
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

    public Map<String, List<?>> convertAllOrdersOfManyCustomersBetweenDatesOfCSR(List<Order> orders){
        return convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
    }

    public AdditionalData numberOfOrdersInDates(List<Order> orders, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        AdditionalData additionalData = countByDate_addData(orders, dateSelection, FirstColumnSelection.Full_customer_name);
        additionalData.setDataName("Number of orders made in dates");
        return additionalData;
    }

    public AdditionalData numberOfOrderStatusesInDates(List<Order> orders, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        AdditionalData additionalData = countByDate_addData(orders, dateSelection, FirstColumnSelection.Order_status);
        additionalData.setDataName("Number of order statuses in dates");
        return additionalData;
    }

    private AdditionalData countByDate_addData(List<Order> orders, DateSelection dateSelection, FirstColumnSelection fcs){
        String first_column_title = fcs.toString();
        List<String> first_column_values = new ArrayList<>();
        Map<String, List<Integer>> monthYearValues = new LinkedHashMap();

        for (Order order: orders){
            String value = getStringFirstColValue(order, fcs);
            if(!first_column_values.contains(value)) first_column_values.add(value);
        }

        for (Order order: orders) {
            String firstColValue = getStringFirstColValue(order, fcs);
            int index = first_column_values.indexOf(firstColValue);
            LocalDate localDate = order.getDate().toLocalDate();
            String certainDate = getStringCutDate(localDate, dateSelection);
            if(!monthYearValues.containsKey(certainDate)){
                Integer[] integers = new Integer[first_column_values.size()];
                Arrays.fill(integers, 0);
                monthYearValues.put(certainDate, Arrays.asList(integers));
            }
            int value = monthYearValues.get(certainDate).get(index);
            value +=1;
            monthYearValues.get(certainDate).set(index, value);
        }

        LinkedHashMap<String, List<?>> data = new LinkedHashMap<>();
        data.put(first_column_title, first_column_values);
        data.putAll(monthYearValues);
        return new AdditionalData(data);
    }

    private String getStringCutDate(LocalDate localDate, DateSelection dateSelection){
        switch (dateSelection){
            case DAY: {
                return localDate.toString();
            }
            case MONTH: {
                return localDate.toString().substring(0, 7);
            }
            case YEAR: {
                return localDate.toString().substring(0, 4);
            }
            default: return localDate.toString();
        }
    }

    private String getStringFirstColValue(Order order, FirstColumnSelection fcs){
        switch (fcs){
            case Full_customer_name:{
                return order.getCustomer().getFirstName()+" "
                        + order.getCustomer().getMiddleName()+" "
                        + order.getCustomer().getLastName();
            }
            case Order_status:{
                return order.getStatus().getName();
            }
            default: return "First_column_value_not_found";
        }
    }

    private DateSelection getDateSelection(LocalDateTime date_start, LocalDateTime date_finish){
        Period difference = Period.between(date_start.toLocalDate(), date_finish.toLocalDate());
        DateSelection dateSelection;
        if (difference.getYears() > 0) dateSelection = DateSelection.YEAR;
        else if(difference.getMonths() > 0) dateSelection = DateSelection.MONTH;
        else dateSelection = DateSelection.DAY;
        return dateSelection;
    }
}
