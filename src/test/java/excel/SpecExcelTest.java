package excel;

import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.excel.additional.ExcelFormat;
import com.netcracker.crm.excel.impl.Temp_ExcelGenerator;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by AN on 04.05.2017.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpecExcelTest {

    private User userCreated;
    private Order orderCreated;
    private Product productCreated;

    private User userCreated1;
    private Order orderCreated1;
    private Product productCreated1;

    private List<Order> orders;
    private String xAxis;
    private List<String> yAxis;


    @Before
    public void createData() throws Exception {

        userCreated = new User();
        userCreated.setPassword("test password");
        userCreated.setFirstName("test first name");
        userCreated.setMiddleName("test middle name");
        userCreated.setEmail("test email");
        userCreated.setEnable(false);
        userCreated.setAccountNonLocked(false);
        userCreated.setContactPerson(false);
        userCreated.setUserRole(UserRole.ROLE_CUSTOMER);

        orderCreated = new Order();
        orderCreated.setId((long) 0);
        orderCreated.setStatus(OrderStatus.NEW);
        orderCreated.setDate(LocalDateTime.now());
        orderCreated.setPreferedDate(LocalDateTime.now());
        orderCreated.setCustomer(userCreated);
        orderCreated.setCsr(userCreated);

        productCreated = new Product();
        productCreated.setTitle("test product title");
        productCreated.setStatus(ProductStatus.OUTDATED);
        productCreated.setDefaultPrice(500.0);
        orderCreated.setProduct(productCreated);

        userCreated1 = new User();
        userCreated1.setPassword("test password_haha");
        userCreated1.setFirstName("test first name_haha");
        userCreated1.setMiddleName("test middle name_haha");
        userCreated1.setEmail("test email_haha");
        userCreated1.setEnable(false);
        userCreated1.setAccountNonLocked(false);
        userCreated1.setContactPerson(false);
        userCreated1.setUserRole(UserRole.ROLE_CUSTOMER);

        orderCreated1 = new Order();
        orderCreated1.setId((long) 1);
        orderCreated1.setStatus(OrderStatus.NEW);
        orderCreated1.setDate(LocalDateTime.now());
        orderCreated1.setPreferedDate(LocalDateTime.now());
        orderCreated1.setCustomer(userCreated);
        orderCreated1.setCsr(userCreated);

        productCreated1 = new Product();
        productCreated1.setTitle("test product title_haha");
        productCreated1.setStatus(ProductStatus.OUTDATED);
        productCreated1.setDefaultPrice(300.0);
        orderCreated1.setProduct(productCreated1);

        orders = new ArrayList<>();
        orders.add(orderCreated);
        orders.add(orderCreated1);

        xAxis = "Order_id";
        yAxis = new ArrayList<>();
        yAxis.add("Product_default_price");
    }

    @Test
    public void createFileXLSX_Chart() throws IOException {
        Temp_ExcelGenerator tempExcelGenerator = new Temp_ExcelGenerator();
        Workbook workbook =  tempExcelGenerator.generateOrderExcel(ExcelFormat.XLSX, orders, "this Report", xAxis, yAxis);
        String filename = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\Test Chart Report.xlsx";
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
        File file = new File(filename);
        assertTrue("File was created", file.exists());
    }

    @Test
    public void createFileXLS_Chart() throws IOException {
        Temp_ExcelGenerator tempExcelGenerator = new Temp_ExcelGenerator();
        Workbook workbook =  tempExcelGenerator.generateOrderExcel(ExcelFormat.XLS, orders, "this Report", xAxis, yAxis);
        String filename = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\Test Chart Report.xls";
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
        File file = new File(filename);
        assertTrue("File was created", file.exists());
    }

    @Test
    public void createFileXLSX() throws IOException {
        Temp_ExcelGenerator tempExcelGenerator = new Temp_ExcelGenerator();
        Workbook workbook =  tempExcelGenerator.generateOrderExcel(ExcelFormat.XLSX, orders, "this Report");
        String filename = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\Test Report.xlsx";
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
        File file = new File(filename);
        assertTrue("File was created", file.exists());
    }

    @Test
    public void createFileXLS() throws IOException {
        Temp_ExcelGenerator tempExcelGenerator = new Temp_ExcelGenerator();
        Workbook workbook =  tempExcelGenerator.generateOrderExcel(ExcelFormat.XLS, orders, "this Report");
        String filename = "src\\main\\java\\com\\netcracker\\crm\\excel\\reports\\Test Report.xls";
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
        File file = new File(filename);
        assertTrue("File was created", file.exists());
    }






}
