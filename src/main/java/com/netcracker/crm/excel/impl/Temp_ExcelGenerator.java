package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 03.05.2017.
 */
public class Temp_ExcelGenerator {
    public Workbook generateOrderExcel(ExcelFormat fileFormat, List<Order> orders, String reportName) {
        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName);
    }

    public Workbook generateOrderExcel(ExcelFormat fileFormat, List<Order> orders, String reportName, String xAxisColumn, List<String> yAxisColumn) {
        OrderConverter orderConverter = new OrderConverter();
        LinkedHashMap<String, List<?>> data = (LinkedHashMap<String, List<?>>) orderConverter.convertAllOrdersOfCustomerBetweenDatesOfCSR(orders);
        return new DefaultExcelBuilder().getWorkbookChart(fileFormat,data,reportName,xAxisColumn,yAxisColumn);
    }
}
