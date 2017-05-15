package com.netcracker.crm.excel;

import static org.junit.Assert.*;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.impl.ReportServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Created by AN on 07.05.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class OrderReportServiceTest {
    @Autowired
    private ReportService reportService;

    @Autowired
    private OrderDao orderDao;

    private final String FOLDER_PATH = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\";
    private Long csr_id;
    private Long customer_id;
    private List<Long> customer_id_list;
    private LocalDateTime firstDate;
    private LocalDateTime lastDate;

    @Before
    public void init(){
        csr_id = 10065L;
        customer_id = 9471L;
        LocalDate date1 = LocalDate.of(1999, Month.FEBRUARY, 15);
        firstDate = LocalDateTime.of(date1, LocalTime.now());
        LocalDate date2 = LocalDate.of(2017, Month.MARCH, 15);
        lastDate = LocalDateTime.of(date2, LocalTime.now());
        customer_id_list = new ArrayList<>();
        customer_id_list.add(9471L);
        customer_id_list.add(298L);
        customer_id_list.add(4902L);
        customer_id_list.add(9551L);
    }

    @Test
    public void createOrdersBetweenDatesOfCustomer_Report() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        reportService.createOrdersBetweenDatesOfCustomer_Report(ExcelFormat.XLSX,csr_id,customer_id,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        reportService.createOrdersBetweenDatesOfCustomer_Report(ExcelFormat.XLS,csr_id,customer_id,firstDate,lastDate);
        workbook = reportService.getLastReportWorkbook();
        fileName = FOLDER_PATH+reportService.getLastReportFileName();
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createOrdersBetweenDatesOfCustomer_ReportGraphic() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        reportService.createOrdersBetweenDatesOfCustomer_ReportChart(csr_id,customer_id,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        File file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createOrdersBetweenDatesOfArrayCustomer_Report() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        reportService.createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat.XLSX,csr_id,customer_id_list,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        reportService.createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat.XLS,csr_id,customer_id_list,firstDate,lastDate);
        workbook = reportService.getLastReportWorkbook();
        fileName = FOLDER_PATH+reportService.getLastReportFileName();
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        file = new File(fileName);
        assertTrue(file.exists());
    }

    @Test
    public void createOrdersBetweenDatesOfArrayCustomer_ReportGraphic() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        reportService.createOrdersBetweenDatesOfArrayCustomer_ReportChart(csr_id,customer_id_list,firstDate,lastDate);
        Workbook workbook = reportService.getLastReportWorkbook();
        String fileName = FOLDER_PATH+reportService.getLastReportFileName();
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        File file = new File(fileName);
        assertTrue(file.exists());
    }

    @After
    public void clearFolder(){
     /*   for (File myFile : new File(FOLDER_PATH).listFiles())
            if (myFile.isFile()) myFile.delete();*/
    }
}
