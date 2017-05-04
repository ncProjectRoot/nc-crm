package com.netcracker.crm.excel.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.ReportService;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public class ReportServiceImpl implements ReportService {
    public Workbook createOrdersBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator();
        return org.generateOrdersBetweenDatesOfCustomer_Report(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
    }

    public Workbook createOrdersBetweenDatesOfCustomer_ReportGraphic(ExcelFormat fileFormat, Long csr_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        OrderReportGenerator org = new OrderReportGenerator();
        return org.generateOrdersBetweenDatesOfCustomer_ReportGraphic(fileFormat, csr_id, customer_id, date_finish_first, date_finish_last);
    }
}
