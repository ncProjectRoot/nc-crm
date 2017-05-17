package com.netcracker.crm.excel;

import com.netcracker.crm.dao.ComplaintDao;
import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.impl.complaint.ComplaintReportServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by AN on 17.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintReportServiceImplTest {
    @Qualifier("complaintReportServiceImpl")
    @Autowired
    private ReportService reportService;

    @Autowired
    private ComplaintDao complaintDao;

    private final String FOLDER_PATH = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\";
    private Long pmg_id;
    private Long customer_id;
    private List<Long> customer_id_list;
    private LocalDateTime firstDate;
    private LocalDateTime lastDate;

    @Before
    public void init(){
        pmg_id = 10017L;
        customer_id = 503L;
        LocalDate date1 = LocalDate.of(1999, Month.FEBRUARY, 15);
        firstDate = LocalDateTime.of(date1, LocalTime.now());
        LocalDate date2 = LocalDate.of(2017, Month.MARCH, 15);
        lastDate = LocalDateTime.of(date2, LocalTime.now());
        customer_id_list = new ArrayList<>();
        customer_id_list.add(503L);
        customer_id_list.add(9698L);
        customer_id_list.add(7932L);
        customer_id_list.add(7853L);
    }

    @Test
    public void createComplaintsBetweenDatesOfCustomer_Report() throws IOException {
        reportService = new ComplaintReportServiceImpl(complaintDao);
        reportService.createReport(ExcelFormat.XLSX, pmg_id,customer_id,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        reportService.createReport(ExcelFormat.XLS, pmg_id,customer_id,firstDate,lastDate);
        workbook = reportService.getLastReportWorkbook();
        fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createComplaintsBetweenDatesOfCustomer_ReportGraphic() throws IOException {
        reportService = new ComplaintReportServiceImpl(complaintDao);
        reportService.createReportChart(pmg_id,customer_id,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        File file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createComplaintsBetweenDatesOfArrayCustomer_Report() throws IOException {
        reportService = new ComplaintReportServiceImpl(complaintDao);
        reportService.createReport(ExcelFormat.XLSX, pmg_id,customer_id_list,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        reportService.createReport(ExcelFormat.XLS, pmg_id,customer_id_list,firstDate,lastDate);
        workbook = reportService.getLastReportWorkbook();
        fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createComplaintsBetweenDatesOfArrayCustomer_ReportGraphic() throws IOException {
        reportService = new ComplaintReportServiceImpl(complaintDao);
        reportService.createReportChart(pmg_id,customer_id_list,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        File file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createComplaintsBetweenDatesOfAllCustomers_Report() throws IOException {
        reportService = new ComplaintReportServiceImpl(complaintDao);
        reportService.createReport(ExcelFormat.XLSX, pmg_id,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        reportService.createReport(ExcelFormat.XLS, pmg_id,firstDate,lastDate);
        workbook = reportService.getLastReportWorkbook();
        fileName = FOLDER_PATH+ reportService.getLastReportFileName();
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        file = new File(fileName);
        assertTrue(file.exists());
    }

    @After
    public void clearFolder(){
     /*   for (File myFile : new File(FOLDER_PATH).listFiles())
            if (myFile.isFile()) myFile.delete();*/
    }
}
