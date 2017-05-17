package com.netcracker.crm.excel;

import com.netcracker.crm.excel.additional.ExcelFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by AN on 25.04.2017.
 */
public interface ReportService {
    Workbook getLastReportWorkbook();

    String getLastReportFileName();

    void createReport(ExcelFormat fileFormat, Long user_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    void createReportChart(Long user_id, Long customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    void createReport(ExcelFormat fileFormat, Long user_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    void createReportChart(Long user_id, List<Long> customer_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);

    void createReport(ExcelFormat fileFormat, Long user_id, LocalDateTime date_finish_first, LocalDateTime date_finish_last);




    void createReportOfCurrentWeek(ExcelFormat fileFormat, Long user_id, Long customer_id);

    void createReportChartOfCurrentWeek(Long user_id, Long customer_id);

    void createReportOfCurrentWeek(ExcelFormat fileFormat, Long user_id, List<Long> customer_id);

    void createReportChartOfCurrentWeek(Long user_id, List<Long> customer_id);

    void createReportOfCurrentWeek(ExcelFormat fileFormat, Long user_id);




    void createReportOfCurrentMonth(ExcelFormat fileFormat, Long user_id, Long customer_id);

    void createReportChartOfCurrentMonth(Long user_id, Long customer_id);

    void createReportOfCurrentMonth(ExcelFormat fileFormat, Long user_id, List<Long> customer_id);

    void createReportChartOfCurrentMonth(Long user_id, List<Long> customer_id);

    void createReportOfCurrentMonth(ExcelFormat fileFormat, Long user_id);



    void createReportOfCurrentYear(ExcelFormat fileFormat, Long user_id, Long customer_id);

    void createReportChartOfCurrentYear(Long user_id, Long customer_id);

    void createReportOfCurrentYear(ExcelFormat fileFormat, Long user_id, List<Long> customer_id);

    void createReportChartOfCurrentYear(Long user_id, List<Long> customer_id);

    void createReportOfCurrentYear(ExcelFormat fileFormat, Long user_id);


}
