package com.netcracker.crm.excel.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.excel.ReportService;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by AN on 25.04.2017.
 */
@Service
public class ReportServiceImpl implements ReportService {
    private final OrderDao orderDao;

    private Workbook lastReportWorkbook;

    private String lastReportFileName;

    @Autowired
    public ReportServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Workbook getLastReportWorkbook() {
        return lastReportWorkbook;
    }

    public String getLastReportFileName() {
        return lastReportFileName;
    }

    public void createOrdersBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createOrdersBetweenDatesOfCustomer_ReportChart(Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createOrdersBetweenDatesOfArrayCustomer_ReportChart(Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersBetweenDatesAllCustomers_Report(ExcelFormat fileFormat, Long csr_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }





    public void createOrdersOfCurrentWeekOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last week"
                +"."+fileFormat;
    }

    public void createOrdersOfCurrentWeekOfCustomer_ReportChart(Long csr_id, Long customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last week"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersOfCurrentWeekOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last week"
                +"."+fileFormat;
    }

    public void createOrdersOfCurrentWeekOfArrayCustomer_ReportChart(Long csr_id, List<Long> customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last week"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersOfCurrentWeekAllCustomers_Report(ExcelFormat fileFormat, Long csr_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                +" in last week"
                +"."+fileFormat;
    }





    public void createOrdersOfCurrentMonthOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last month"
                +"."+fileFormat;
    }

    public void createOrdersOfCurrentMonthOfCustomer_ReportChart(Long csr_id, Long customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last month"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersOfCurrentMonthOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last month"
                +"."+fileFormat;
    }

    public void createOrdersOfCurrentMonthOfArrayCustomer_ReportChart(Long csr_id, List<Long> customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last month"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersOfCurrentMonthAllCustomers_Report(ExcelFormat fileFormat, Long csr_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                +" in last month"
                +"."+fileFormat;
    }




    public void createOrdersOfCurrentYearOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last year"
                +"."+fileFormat;
    }

    public void createOrdersOfCurrentYearOfCustomer_ReportChart(Long csr_id, Long customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last year"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersOfCurrentYearOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last year"
                +"."+fileFormat;
    }

    public void createOrdersOfCurrentYearOfArrayCustomer_ReportChart(Long csr_id, List<Long> customer_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last year"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersOfCurrentYearAllCustomers_Report(ExcelFormat fileFormat, Long csr_id){
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = org.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                +" in last year"
                +"."+fileFormat;
    }

    private String getSelectedDatesFileNameEnding(LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        return " between "
                +date_finish_first.getDayOfMonth()+"-"
                +date_finish_first.getMonthValue()+"-"
                +date_finish_first.getYear()+" and "
                +date_finish_last.getDayOfMonth()+"-"
                +date_finish_last.getMonthValue()+"-"
                +date_finish_last.getYear();
    }
}
