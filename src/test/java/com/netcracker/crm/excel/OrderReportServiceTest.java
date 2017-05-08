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
        csr_id = 8L;
        customer_id = 55L;
        LocalDate date1 = LocalDate.of(1999, Month.FEBRUARY, 15);
        firstDate = LocalDateTime.of(date1, LocalTime.now());
        LocalDate date2 = LocalDate.of(2011, Month.FEBRUARY, 15);
        lastDate = LocalDateTime.of(date2, LocalTime.now());
        customer_id_list = new ArrayList<>();
        customer_id_list.add(55L);
        customer_id_list.add(122L);
        customer_id_list.add(26L);
        customer_id_list.add(95L);
    }

    @Test
    public void createOrdersBetweenDatesOfCustomer_Report() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        Workbook workbook = reportService.createOrdersBetweenDatesOfCustomer_Report(ExcelFormat.XLSX,csr_id,customer_id,firstDate,lastDate);
        String fileName = FOLDER_PATH+"createOrdersBetweenDatesOfCustomer_Report.xlsx";
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        workbook = reportService.createOrdersBetweenDatesOfCustomer_Report(ExcelFormat.XLS,csr_id,customer_id,firstDate,lastDate);
        fileName = FOLDER_PATH+"createOrdersBetweenDatesOfCustomer_Report.xls";
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        file = new File(fileName);
        assertTrue(file.exists());
        fos.close();
    }

    @Test
    public void createOrdersBetweenDatesOfCustomer_ReportGraphic() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        Workbook workbook = reportService.createOrdersBetweenDatesOfCustomer_ReportGraphic(ExcelFormat.XLSX,csr_id,customer_id,firstDate,lastDate);
        String fileName = FOLDER_PATH+"createOrdersBetweenDatesOfCustomer_ReportGraphic.xlsx";
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        workbook = reportService.createOrdersBetweenDatesOfCustomer_ReportGraphic(ExcelFormat.XLS,csr_id,customer_id,firstDate,lastDate);
        fileName = FOLDER_PATH+"createOrdersBetweenDatesOfCustomer_ReportGraphic.xls";
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        file = new File(fileName);
        assertTrue(file.exists());
        fos.close();
    }

    @Test
    public void createOrdersBetweenDatesOfArrayCustomer_Report() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        Workbook workbook = reportService.createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat.XLSX,csr_id,customer_id_list,firstDate,lastDate);
        String fileName = FOLDER_PATH+"createOrdersBetweenDatesOfArrayCustomer_Report.xlsx";
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        workbook = reportService.createOrdersBetweenDatesOfArrayCustomer_Report(ExcelFormat.XLS,csr_id,customer_id_list,firstDate,lastDate);
        fileName = FOLDER_PATH+"createOrdersBetweenDatesOfArrayCustomer_Report.xls";
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        file = new File(fileName);
        assertTrue(file.exists());
        fos.close();
    }

    @Test
    public void createOrdersBetweenDatesOfArrayCustomer_ReportGraphic() throws IOException {
        reportService = new ReportServiceImpl(orderDao);
        Workbook workbook = reportService.createOrdersBetweenDatesOfArrayCustomer_ReportGraphic(ExcelFormat.XLSX,csr_id,customer_id_list,firstDate,lastDate);
        String fileName = FOLDER_PATH+"createOrdersBetweenDatesOfArrayCustomer_ReportGraphic.xlsx";
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        File file = new File(fileName);
        assertTrue(file.exists());

        workbook = reportService.createOrdersBetweenDatesOfArrayCustomer_ReportGraphic(ExcelFormat.XLS,csr_id,customer_id_list,firstDate,lastDate);
        fileName = FOLDER_PATH+"createOrdersBetweenDatesOfArrayCustomer_ReportGraphic.xls";
        fos = new FileOutputStream(fileName);
        workbook.write(fos);
        file = new File(fileName);
        assertTrue(file.exists());
        fos.close();
    }

    @After
    public void clearFolder(){
     /*   for (File myFile : new File(FOLDER_PATH).listFiles())
            if (myFile.isFile()) myFile.delete();*/
    }
}
