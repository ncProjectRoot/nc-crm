package com.netcracker.crm.excel.service.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.excel.OrderExcelDto;
import com.netcracker.crm.excel.OrderMapKey;
import com.netcracker.crm.excel.builder.ExcelBuilder;
import com.netcracker.crm.excel.converter.OrderExcelConverter;
import com.netcracker.crm.excel.service.OrderExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 22.05.2017.
 */
@Service
public class OrderExcelServiceImpl implements OrderExcelService {


    @Autowired
    private OrderDao orderDao;
    private OrderExcelConverter converter = new OrderExcelConverter();
    private String[] orderTitle = {
            "№",
            "Customer full name",
            "Product title",
            "Order date",
            "Prefered date",
            "Order status",
            "CSR id"
        };



    public void generateCustomerOrders(HttpServletResponse response, OrderExcelDto orderExcelDto) throws IOException {
        String fileName = getFilename(orderExcelDto.getIdCustomer());
        LocalDate from = convertString(orderExcelDto.getDateFrom());
        LocalDate to = convertString(orderExcelDto.getDateTo());
        List<Order> orders = orderDao.findAllByCustomerIds(Arrays.asList(orderExcelDto.getIdCustomer()), from, to, orderExcelDto.getOrderByIndex());
        Workbook workbook = parseData(orders);
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    private Workbook parseData(List<Order> orders) {
        Map<OrderMapKey, List<?>> map = converter.convertToMap(orders);
        ExcelBuilder excelBuilder = new ExcelBuilder();
        excelBuilder.createSheet("Orders");
        setTitleRow(excelBuilder, 0);
        excelBuilder.autosizeColumn(map.size());
        for (int i = 1; i <= orders.size(); i++) {
            rowSetter(excelBuilder, i, map);
            excelBuilder.autosizeColumn(map.size());
        }
        return excelBuilder.getWorkbook();
    }


    private String getFilename(Long[] id){
        if (id.length == 1){
            return  "Orders-" + id[0] + "-";
        }else {
            return  "Orders-by_customers-";
        }
    }

    private void setTitleRow(ExcelBuilder excelBuilder, int rowNumber) {
        excelBuilder.createRow(rowNumber);
        int cellNumber = 0;
        for (String title : orderTitle) {
            excelBuilder.createCell(cellNumber++).setCellValue(title).setCellTitleStyle();
        }
    }

    private void rowSetter(ExcelBuilder excelBuilder, int rowNumber, Map<OrderMapKey, List<?>> map) {
        excelBuilder.createRow(rowNumber);
        cellSetter(excelBuilder, map, rowNumber - 1);
    }

    private void cellSetter(ExcelBuilder excelBuilder, Map<OrderMapKey, List<?>> map, int index) {
        int cellNumber = 0;
        excelBuilder.createCell(cellNumber++).setCellValue((long) index + 1).setCellValueStyle();
        for (Map.Entry<OrderMapKey, List<?>> m : map.entrySet()) {
            excelBuilder.createCell(cellNumber++).setCellValue(getListValue(m.getValue(), index)).setCellValueStyle();
        }
    }

    private LocalDate convertString(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dtf);
    }


    private Object getListValue(List<?> list, int index) {
        return list.get(index);
    }
}
