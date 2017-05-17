package com.netcracker.crm.excel.impl.complaint;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.domain.model.Complaint;
import com.netcracker.crm.excel.additional.AdditionalData;
import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.impl.AdditionalDataConfigurator;
import com.netcracker.crm.excel.impl.DefaultExcelBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 17.05.2017.
 */
public class ComplaintReportConfigurator {
    private final ComplaintDao complaintDao;

    private LinkedHashMap<String, List<?>> data;
    private List<AdditionalData> additionalDataList;

    @Autowired
    public ComplaintReportConfigurator(ComplaintDao complaintDao) {
        this.complaintDao = complaintDao;
    }

    public Workbook generateComplaintsBetweenDatesOfCustomer_Report(ExcelFormat fileFormat, Long pmg_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Complaint> complaints = complaintDao.findByPmgIdAndCustomerIdBetweenDates(pmg_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Complaints_of_customer";
        configurateAllTypes(complaints, date_finish_first, date_finish_last);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName,additionalDataList);
    }

    public Workbook generateComplaintsBetweenDatesOfCustomer_ReportGraphic(Long pmg_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Complaint> complaints = complaintDao.findByPmgIdAndCustomerIdBetweenDates(pmg_id,customer_id,date_finish_first,date_finish_last);

        String reportName = "Complaints_of_customer";
        configurateAllTypes(complaints, date_finish_first, date_finish_last);
        return new DefaultExcelBuilder().getWorkbookChart(data,reportName, additionalDataList);
    }

    public Workbook generateComplaintsBetweenDatesOfArrayCustomer_Report(ExcelFormat fileFormat, Long pmg_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Complaint> complaints = complaintDao.findByPmgIdAndListOfCustomerIdBetweenDates(pmg_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Complaints_of_several_customers";
        configurateAllTypes(complaints, date_finish_first, date_finish_last);
        return new DefaultExcelBuilder().getWorkbook(fileFormat,data,reportName, additionalDataList);
    }

    public Workbook generateComplaintsBetweenDatesOfArrayCustomer_ReportGraphic(Long pmg_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last) {
        List<Complaint> complaints = complaintDao.findByPmgIdAndListOfCustomerIdBetweenDates(pmg_id, customer_id,date_finish_first,date_finish_last);

        String reportName = "Complaints_of_several_customers";
        configurateAllTypes(complaints, date_finish_first, date_finish_last);
        return new DefaultExcelBuilder().getWorkbookChart(data,reportName, additionalDataList);
    }

    public Workbook generateComplaintsBetweenDatesAllCustomers_Report(ExcelFormat fileFormat, Long pmg_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        List<Complaint> complaints = complaintDao.findByPmgIdBetweenDates(pmg_id, date_finish_first, date_finish_last);
        String reportName = "Complaints_of_all_customers";
        configurateAllTypes(complaints, date_finish_first, date_finish_last);
        return new DefaultExcelBuilder().getWorkbook(fileFormat, data,reportName, additionalDataList);
    }

    private void configurateAllTypes(List<Complaint> complaints, LocalDateTime date_finish_first, LocalDateTime date_finish_last){
        ComplaintConverter complaintConverter = new ComplaintConverter();
        AdditionalDataConfigurator adc = new AdditionalDataConfigurator();
        data = complaintConverter.convertComplaints(complaints);
        AdditionalData additionalData = adc.numberOfComplaintsInDates(complaints, date_finish_first, date_finish_last);
        AdditionalData additionalData1 = adc.numberOfComplaintStatusesInDates(complaints, date_finish_first, date_finish_last);
        AdditionalData additionalData2 = adc.numberOfComplaintsOrderStatusesInDates(complaints, date_finish_first, date_finish_last);
        additionalDataList = new ArrayList<>();
        additionalDataList.add(additionalData);
        additionalDataList.add(additionalData1);
        additionalDataList.add(additionalData2);
    }
}
