package com.netcracker.crm.excel.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
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
public class OrderReportGenerator {

    private final OrderDao orderDao;

    @Autowired
    public OrderReportGenerator(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Workbook generateOrdersBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndCustomerId(csr_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders of " +
                orders.get(0).getCustomer().getFirstName() + " " +
                orders.get(0).getCustomer().getMiddleName() +  " " +
                orders.get(0).getCustomer().getLastName();

        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName);
    }

    public Workbook generateOrdersBetweenDatesOfCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndCustomerId(csr_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders of " +
                orders.get(0).getCustomer().getFirstName() + " " +
                orders.get(0).getCustomer().getMiddleName() +  " " +
                orders.get(0).getCustomer().getLastName();

        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
        String xAxis = "Order_id";
        List<String> yAxis = new ArrayList<>();
        yAxis.add("Product_default_price");
        Map<String, List<String>> xColumn_yColumns = new HashMap<>();
        xColumn_yColumns.put(xAxis, yAxis);
        return new DefaultExcelBuilder().getWorkbookChart(fileFormat,data,reportName, xColumn_yColumns);
    }

    public Workbook generateOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndArrayOfCustomerId
                (csr_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders of several accounts";
        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfManyCustomersBetweenDatesOfCSR(orders);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName);
    }

    public Workbook generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndArrayOfCustomerId
                (csr_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders of several accounts";
        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfManyCustomersBetweenDatesOfCSR(orders);
        String xAxis = "Full_name";
        List<String> yAxis = new ArrayList<>();
        yAxis.add("Product_default_price");
        yAxis.add("Order_date");

        Map<String, List<String>> graphic = new HashMap<>();
        graphic.put(xAxis, yAxis);

        String xAxis1 = "Full_name";
        List<String> yAxis1 = new ArrayList<>();
        yAxis1.add("Order_preffered");
        yAxis1.add("Order_date");


        Map<String, List<String>> graphic1 = new HashMap<>();
        graphic.put(xAxis, yAxis);
        graphic1.put(xAxis1, yAxis1);
        List<Map<String, List<String>>> graphics = new ArrayList<>();
        graphics.add(graphic);
        graphics.add(graphic1);
        List<LinkedHashMap<String, List<?>>> additionalData = new ArrayList<>();
        additionalData.add(data);
        additionalData.add(data);
        additionalData.add(data);

        return new DefaultExcelBuilder().getWorkbookChart
                (fileFormat,data,reportName, graphics, additionalData);
    }


}
