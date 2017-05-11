package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.DateSelection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * Created by AN on 03.05.2017.
 */
public class OrderConverter {
    private LinkedHashMap<String, List<?>> lastAdditionalData;
    private String  lastAdditionalDataName;
    private List<String> lastAdditionalData_firstColumns;

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

    public LinkedHashMap<String, List<?>> numberOfOrdersInDates(List<Order> orders, LocalDateTime date_start, LocalDateTime date_finish) {
        Period difference = Period.between(date_start.toLocalDate(), date_finish.toLocalDate());
        DateSelection dateSelection;
        if (difference.getYears() > 0) dateSelection = DateSelection.YEAR;
        else if(difference.getMonths() > 0) dateSelection = DateSelection.MONTH;
        else dateSelection = DateSelection.DAY;
        countByDate_addData(orders, dateSelection);
        setLastAdditionalDataName("Number of orders made in dates");
        return getLastAdditionalData();
    }

    private void countByDate_addData(List<Order> orders, DateSelection dateSelection){
        Map<String, List<?>> additinonalData = new LinkedHashMap<>();
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
                case DAY: certainDate = localDateTime.toString();
                case MONTH: {
                    certainDate = localDateTime.toString().substring(3,
                            localDateTime.toString().length());
                }
                case YEAR: {
                    certainDate = localDateTime.toString().substring(6,
                            localDateTime.toString().length());
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
        additinonalData.put(cutomer_name_title, customer_names);
        additinonalData.putAll(monthYearValues);
        setLastAdditionalData((LinkedHashMap<String, List<?>>) additinonalData);
        setLastAdditionalData_firstColumns(customer_names);
    }

    public LinkedHashMap<String, List<?>> getLastAdditionalData() {
        return lastAdditionalData;
    }

    public void setLastAdditionalData(LinkedHashMap<String, List<?>> lastAdditionalData) {
        this.lastAdditionalData = lastAdditionalData;
    }

    public String getLastAdditionalDataName() {
        return lastAdditionalDataName;
    }


    public void setLastAdditionalDataName(String lastAdditionalDataName) {
        this.lastAdditionalDataName = lastAdditionalDataName;
    }

    public List<String> getLastAdditionalData_firstColumns() {
        return lastAdditionalData_firstColumns;
    }

    public void setLastAdditionalData_firstColumns(List<String> lastAdditionalData_firstColumns) {
        this.lastAdditionalData_firstColumns = lastAdditionalData_firstColumns;
    }
}
