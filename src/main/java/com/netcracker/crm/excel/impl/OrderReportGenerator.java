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

        String reportName = "Orders_of_" +
                orders.get(0).getCustomer().getFirstName() + "_" +
                orders.get(0).getCustomer().getMiddleName() +  "_" +
                orders.get(0).getCustomer().getLastName();

        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName);
    }

    public Workbook generateOrdersBetweenDatesOfCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndCustomerId(csr_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders_of_" +
                orders.get(0).getCustomer().getFirstName() + "_" +
                orders.get(0).getCustomer().getMiddleName() +  "_" +
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

        String reportName = "Orders_of_several_accounts";
        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfManyCustomersBetweenDatesOfCSR(orders);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName);
    }

    public Workbook generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Order> orders = orderDao.findAllByCsrIdAndArrayOfCustomerId
                (csr_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Orders_of_several_accounts";
        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfManyCustomersBetweenDatesOfCSR(orders);

        List<LinkedHashMap<String, List<?>>> additionalData = new ArrayList<>();
        LinkedHashMap<String,List<?>> firstAddData = new LinkedHashMap<>();

        List<String> names = new ArrayList<>();
        names.add("name1");
        names.add("name2");
        names.add("name3");
        names.add("name4");
        firstAddData.put("names", names);

        List<Long> firstDate = new ArrayList<>();
        firstDate.add(1L);
        firstDate.add(2L);
        firstDate.add(3L);
        firstDate.add(4L);
        firstAddData.put("firstDate", firstDate);

        List<Long> secondDate = new ArrayList<>();
        secondDate.add(2L);
        secondDate.add(3L);
        secondDate.add(4L);
        secondDate.add(1L);
        firstAddData.put("secondDate", secondDate);

        additionalData.add(firstAddData);

        String xAxis = "TestData";
        String addDataOuterTitle = xAxis;

        List<String> addDataOuterTitles = new ArrayList<>();
        addDataOuterTitles.add(addDataOuterTitle);

        List<String> yAxis = names;


        Map<String, List<String>> graphic = new HashMap<>();
        graphic.put(xAxis, yAxis);


        graphic.put(xAxis, yAxis);
        List<Map<String, List<String>>> graphics = new ArrayList<>();
        graphics.add(graphic);

        return new DefaultExcelBuilder().getWorkbookChart
                (fileFormat,data,reportName, graphics, additionalData, addDataOuterTitles);
    }


}
