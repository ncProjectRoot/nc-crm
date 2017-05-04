package com.netcracker.crm.excel.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dao.impl.OrderDaoImpl;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 04.05.2017.
 */
public class OrderReportGenerator {

    public Workbook generateOrdersBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderDao orderDao = new OrderDaoImpl();
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
        OrderDao orderDao = new OrderDaoImpl();
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
        return new DefaultExcelBuilder().getWorkbookChart(fileFormat,data,reportName, xAxis, yAxis);
    }


}
