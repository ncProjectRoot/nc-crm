package com.netcracker.crm.excel.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by AN on 04.05.2017.
 */
@Component
public class OrderReportConfigurator {

    private final OrderDao orderDao;

    @Autowired
    public OrderReportConfigurator(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Workbook generateOrdersBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndCustomerIdBetweenDates(csr_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders_of_customer";

        OrderConverter orderConverter = new OrderConverter();
        AdditionalDataConfigurator adc = new AdditionalDataConfigurator();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertOrders(orders);
        AdditionalData additionalData = adc.numberOfOrdersInDates(orders, date_finish_first, date_finish_last);
        AdditionalData additionalData1 = adc.numberOfOrderStatusesInDates(orders, date_finish_first, date_finish_last);
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        additionalDataList.add(additionalData1);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName,additionalDataList);
    }

    public Workbook generateOrdersBetweenDatesOfCustomer_ReportGraphic(Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndCustomerIdBetweenDates(csr_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders_of_customer";

        OrderConverter orderConverter = new OrderConverter();
        AdditionalDataConfigurator adc = new AdditionalDataConfigurator();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertOrders(orders);
        AdditionalData additionalData = adc.numberOfOrdersInDates(orders, date_finish_first, date_finish_last);
        AdditionalData additionalData1 = adc.numberOfOrderStatusesInDates(orders, date_finish_first, date_finish_last);
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        additionalDataList.add(additionalData1);
        return new DefaultExcelBuilder().getWorkbookChart(data,reportName, additionalDataList);
    }

    public Workbook generateOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findOrdersByCsrIdAndArrayOfCustomerIdBetweenDates
                (csr_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders_of_several_customers";
        OrderConverter orderConverter = new OrderConverter();
        AdditionalDataConfigurator adc = new AdditionalDataConfigurator();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertOrders(orders);
        AdditionalData additionalData = adc.numberOfOrdersInDates(orders, date_finish_first, date_finish_last);
        AdditionalData additionalData1 = adc.numberOfOrderStatusesInDates(orders, date_finish_first, date_finish_last);
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        additionalDataList.add(additionalData1);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName, additionalDataList);
    }

    public Workbook generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findOrdersByCsrIdAndArrayOfCustomerIdBetweenDates
                (csr_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders_of_several_customers";
        OrderConverter orderConverter = new OrderConverter();
        AdditionalDataConfigurator adc = new AdditionalDataConfigurator();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertOrders(orders);
        AdditionalData additionalData = adc.numberOfOrdersInDates(orders, date_finish_first, date_finish_last);
        AdditionalData additionalData1 = adc.numberOfOrderStatusesInDates(orders, date_finish_first, date_finish_last);
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        additionalDataList.add(additionalData1);
        return new DefaultExcelBuilder().getWorkbookChart(data,reportName, additionalDataList);
    }

    public Workbook generateOrdersBetweenDatesAllCustomers_Report(ExcelFormat fileFormat, Long csr_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        List<Order> orders = orderDao.findOrdersByCsrIdBetweenDates(csr_id, date_finish_first, date_finish_last);
        String reportName = "Orders_of_all_customers";
        OrderConverter orderConverter = new OrderConverter();
        AdditionalDataConfigurator adc = new AdditionalDataConfigurator();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertOrders(orders);
        AdditionalData additionalData = adc.numberOfOrdersInDates(orders, date_finish_first, date_finish_last);
        AdditionalData additionalData1 = adc.numberOfOrderStatusesInDates(orders, date_finish_first, date_finish_last);
        List<AdditionalData> additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        additionalDataList.add(additionalData1);
        return new DefaultExcelBuilder().getWorkbook(fileFormat, data,reportName, additionalDataList);
    }
}
