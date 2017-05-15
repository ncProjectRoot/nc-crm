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
                +getOrderFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createOrdersBetweenDatesOfCustomer_ReportChart(Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of customer ( id=" +customer_id+" )"
                +getOrderFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +getOrderFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createOrdersBetweenDatesOfArrayCustomer_ReportChart(Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        lastReportWorkbook = org.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(csr_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Orders of group of customers"
                +getOrderFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    private String getOrderFileNameEnding(LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        return " between "
                +date_finish_first.getDayOfMonth()+"-"
                +date_finish_first.getMonthValue()+"-"
                +date_finish_first.getYear()+" and "
                +date_finish_last.getDayOfMonth()+"-"
                +date_finish_last.getMonthValue()+"-"
                +date_finish_last.getYear();
    }

}
