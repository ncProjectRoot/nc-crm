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

    @Autowired
    public ReportServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Workbook createOrdersBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        return org.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
    }

    public Workbook createOrdersBetweenDatesOfCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        return org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
    }

    public Workbook createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        return org.generateOrdersBetweenDatesOfArrayCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
    }

    public Workbook createOrdersBetweenDatesOfArrayCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator(orderDao);
        return org.generateOrdersBetweenDatesOfArrayCustomer_ReportGraphic(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
    }

}
