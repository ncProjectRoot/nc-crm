package com.netcracker.crm.excel.impl.complaint;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.excel.ReportService;
import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by AN on 17.05.2017.
 */
@Service
public class ComplaintReportServiceImpl implements ReportService{
    private final ComplaintDao complaintDao;
    private ComplaintReportConfigurator crc;

    private Workbook lastReportWorkbook;

    private String lastReportFileName;

    @Autowired
    public ComplaintReportServiceImpl(ComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
        crc = new ComplaintReportConfigurator(this.complaintDao);
    }

    public Workbook getLastReportWorkbook() {
        return lastReportWorkbook;
    }

    public String getLastReportFileName() {
        return lastReportFileName;
    }

    public void createReport(ExcelFormat fileFormat, Long pmg_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createReportChart(Long pmg_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReport(ExcelFormat fileFormat, Long pmg_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }

    public void createReportChart(Long pmg_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReport(ExcelFormat fileFormat, Long pmg_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        
        lastReportWorkbook = crc.generateComplaintsBetweenDatesAllCustomers_Report(fileFormat, pmg_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of all customers"
                + getSelectedDatesFileNameEnding(date_finish_first, date_finish_last)
                +"."+fileFormat;
    }





    public void createReportOfCurrentWeek(ExcelFormat fileFormat, Long pmg_id, Long customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                +" in last week"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentWeek(Long pmg_id, Long customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                +" in last week"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentWeek(ExcelFormat fileFormat, Long pmg_id, List<Long> customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                +" in last week"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentWeek(Long pmg_id, List<Long> customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                +" in last week"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentWeek(ExcelFormat fileFormat, Long pmg_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusWeeks(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesAllCustomers_Report(fileFormat, pmg_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of all customers"
                +" in last week"
                +"."+fileFormat;
    }





    public void createReportOfCurrentMonth(ExcelFormat fileFormat, Long pmg_id, Long customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                +" in last month"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentMonth(Long pmg_id, Long customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                +" in last month"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentMonth(ExcelFormat fileFormat, Long pmg_id, List<Long> customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                +" in last month"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentMonth(Long pmg_id, List<Long> customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                +" in last month"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentMonth(ExcelFormat fileFormat, Long pmg_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusMonths(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesAllCustomers_Report(fileFormat, pmg_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of all customers"
                +" in last month"
                +"."+fileFormat;
    }



    

    public void createReportOfCurrentYear(ExcelFormat fileFormat, Long pmg_id, Long customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                +" in last year"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentYear(Long pmg_id, Long customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of customer ( id=" +customer_id+" )"
                +" in last year"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentYear(ExcelFormat fileFormat, Long pmg_id, List<Long> customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_Report(fileFormat, pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                +" in last year"
                +"."+fileFormat;
    }

    public void createReportChartOfCurrentYear(Long pmg_id, List<Long> customer_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesOfArrayCustomer_ReportGraphic(pmg_id, customer_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of group of customers"
                +" in last year"
                +" with charts"
                +"."+ExcelFormat.XLSX;
    }

    public void createReportOfCurrentYear(ExcelFormat fileFormat, Long pmg_id){
        
        LocalDateTime date_finish_first = LocalDateTime.now();
        LocalDateTime date_finish_last = date_finish_first.minusYears(1L);
        lastReportWorkbook = crc.generateComplaintsBetweenDatesAllCustomers_Report(fileFormat, pmg_id, date_finish_first, date_finish_last);
        lastReportFileName = "Complaints of all customers"
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
