package com.netcracker.crm.excel.impl.order;

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
public class OrderReportServiceImpl implements ReportService {
    private final OrderDao orderDao;

    private Workbook lastReportWorkbook;

    private String lastReportFileName;

    @Autowired
    public OrderReportServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Workbook getLastReportWorkbook() {
        return lastReportWorkbook;
    }

    public String getLastReportFileName() {
        return lastReportFileName;
    }

    public void createReport(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createReportChart(Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReport(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createReportChart(Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReport(ExcelFormat fileFormat, Long csr_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        lastReportWorkbook = orc.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }





    public void createReportOfCurrentWeek(ExcelFormat fileFormat, Long csr_id, Long customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last week"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentWeek(Long csr_id, Long customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last week"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentWeek(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last week"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentWeek(Long csr_id, List<Long> customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last week"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentWeek(ExcelFormat fileFormat, Long csr_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                +" in last week"
                +"."+fileFormat;
    }





    public void createReportOfCurrentMonth(ExcelFormat fileFormat, Long csr_id, Long customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last month"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentMonth(Long csr_id, Long customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last month"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentMonth(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last month"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentMonth(Long csr_id, List<Long> customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last month"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentMonth(ExcelFormat fileFormat, Long csr_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of all customers"
                +" in last month"
                +"."+fileFormat;
    }




    public void createReportOfCurrentYear(ExcelFormat fileFormat, Long csr_id, Long customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last year"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentYear(Long csr_id, Long customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +" in last year"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentYear(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last year"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentYear(Long csr_id, List<Long> customer_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +" in last year"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentYear(ExcelFormat fileFormat, Long csr_id){
        OrderReportConfigurator orc = new OrderReportConfigurator(orderDao);
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = orc.generateOrdersBetweenDatesAllCustomers_Report(fileFormat, csr_id, date_finish_first, date_finish_last);
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
