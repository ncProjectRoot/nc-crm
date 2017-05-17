package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.DateSelection;
import com.netcracker.crm.excel.additional.FirstColumnName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

/**
 * Created by AN on 16.05.2017.
 */
public class AdditionalDataConfigurator {

    public AdditionalData numberOfOrdersInDates(List<Order> orders, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        List<Object> objects = new ArrayList<>(orders);
        AdditionalData additionalData = countByDate_addData(objects, dateSelection, FirstColumnName.Full_customer_name);
        additionalData.setDataName("Number of order made in dates");
        return additionalData;
    }

    public AdditionalData numberOfOrderStatusesInDates(List<Order> orders, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        List<Object> objects = new ArrayList<>(orders);
        AdditionalData additionalData = countByDate_addData(objects, dateSelection, FirstColumnName.Order_status);
        additionalData.setDataName("Number of order statuses in dates");
        return additionalData;
    }

    public AdditionalData numberOfComplaintsInDates(List<Complaint> complaints, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        List<Object> objects = new ArrayList<>(complaints);
        AdditionalData additionalData = countByDate_addData(objects, dateSelection, FirstColumnName.Full_customer_name);
        additionalData.setDataName("Number of complaints made in dates");
        return additionalData;
    }

    public AdditionalData numberOfComplaintStatusesInDates(List<Complaint> complaints, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        List<Object> objects = new ArrayList<>(complaints);
        AdditionalData additionalData = countByDate_addData(objects, dateSelection, FirstColumnName.Complaint_status);
        additionalData.setDataName("Number of complaint statuses in dates");
        return additionalData;
    }

    public AdditionalData numberOfComplaintsOrderStatusesInDates(List<Complaint> complaints, LocalDateTime date_start, LocalDateTime date_finish) {
        DateSelection dateSelection = getDateSelection(date_start, date_finish);
        List<Object> objects = new ArrayList<>(complaints);
        AdditionalData additionalData = countByDate_addData(objects, dateSelection, FirstColumnName.Order_status);
        additionalData.setDataName("Number of order statuses in dates");
        return additionalData;
    }

    private AdditionalData countByDate_addData(List<Object> objects, DateSelection dateSelection, FirstColumnName fcs){
        String first_column_title = fcs.toString();
        List<String> first_column_values = new ArrayList<>();
        Map<String, List<Integer>> monthYearValues = new LinkedHashMap();

        for (Object object: objects){
            String value = getStringFirstColValue(object, fcs);
            if(!first_column_values.contains(value)) first_column_values.add(value);
        }

        for (Object object: objects) {
            String firstColValue = getStringFirstColValue(object, fcs);
            int index = first_column_values.indexOf(firstColValue);
            LocalDate localDate = getObjectDate(object);
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

    private String getStringFirstColValue(Object o, FirstColumnName fcs){
        if(o instanceof Order){
            Order order = (Order) o;
            switch (fcs) {
                case Full_customer_name: {
                    return order.getCustomer().getFirstName() + " "
                            + order.getCustomer().getMiddleName() + " "
                            + order.getCustomer().getLastName();
                }
                case Order_status: {
                    return order.getStatus().getName();
                }
            }
        }
        else if(o instanceof Complaint){
            Complaint complaint = (Complaint) o;
            switch (fcs) {
                case Full_customer_name: {
                    return complaint.getCustomer().getFirstName() + " "
                            + complaint.getCustomer().getMiddleName() + " "
                            + complaint.getCustomer().getLastName();
                }
                case Order_status: {
                    return complaint.getOrder().getStatus().getName();
                }
                case Complaint_status: {
                    return complaint.getStatus().getName();
                }
            }
        }
        return "First_col_value_not_found";
    }

    private DateSelection getDateSelection(LocalDateTime date_start, LocalDateTime date_finish){
        Period difference = Period.between(date_start.toLocalDate(), date_finish.toLocalDate());
        DateSelection dateSelection;
        if (difference.getYears() > 0) dateSelection = DateSelection.YEAR;
        else if(difference.getMonths() > 0) dateSelection = DateSelection.MONTH;
        else dateSelection = DateSelection.DAY;
        return dateSelection;
    }

    private  LocalDate getObjectDate(Object o){
        if(o instanceof Order){
            Order order = (Order) o;
            return order.getDate().toLocalDate();
        }
        return LocalDate.now();
    }
}
