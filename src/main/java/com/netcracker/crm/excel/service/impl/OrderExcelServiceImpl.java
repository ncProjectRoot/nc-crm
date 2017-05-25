package com.netcracker.crm.excel.service.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.dto.OrderExcelDto;
import com.netcracker.crm.excel.OrderMapKey;
import com.netcracker.crm.excel.builder.ExcelBuilder;
import com.netcracker.crm.excel.converter.OrderExcelConverter;
import com.netcracker.crm.excel.drawer.ExcelDrawer;
import com.netcracker.crm.excel.service.OrderExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.temporal.ChronoUnit.*;

/**
 * Created by Pasha on 22.05.2017.
 */
@Service
public class OrderExcelServiceImpl implements OrderExcelService {
    private final OrderDao orderDao;
    private OrderExcelConverter converter = new OrderExcelConverter();
    private ExcelDrawer excelDrawer = new ExcelDrawer();
    private String[] orderTitle = {
            "№",
            "Customer full name",
            "Product title",
            "Order date",
            "Prefered date",
            "Order status",
            "CSR id"
    };

    @Autowired
    public OrderExcelServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }


    public void generateCustomerOrders(HttpServletResponse response, OrderExcelDto orderExcelDto) throws IOException {
        String fileName = getFilename(orderExcelDto.getIdCustomer());
        LocalDate from = convertString(orderExcelDto.getDateFrom());
        LocalDate to = convertString(orderExcelDto.getDateTo());
        List<Order> orders = orderDao.findAllByCustomerIds(Arrays.asList(orderExcelDto.getIdCustomer()), from, to, orderExcelDto.getOrderByIndex());
        LocalDate[] range = calculateRange(from, to);
        Workbook workbook = parseData(orders, range);
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + LocalDate.now() + ".xlsx");
        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    private Workbook parseData(List<Order> orders, LocalDate[] range) {
        Map<OrderMapKey, List<?>> map = converter.convertToMap(orders);
        ExcelBuilder excelBuilder = new ExcelBuilder();
        excelBuilder.createSheet("Orders");
        setTitleRow(excelBuilder, 0);
        excelBuilder.autosizeColumn(map.size());
        for (int i = 1; i <= orders.size(); i++) {
            rowSetter(excelBuilder, i, map);
            excelBuilder.autosizeColumn(map.size());
        }
        drawChart(excelBuilder, orders, range);
        return excelBuilder.getWorkbook();
    }


    private void drawChart(ExcelBuilder excelBuilder, List<Order> orders, LocalDate[] range) {
        Map<LocalDate, Map<String, Integer>> map = prepareDataChart(range, orders);
        excelBuilder.createSheet("Chart");
        setDateTitle(excelBuilder, map.keySet(), 30);
        setProductValue(map.values(), excelBuilder, 31);
        excelDrawer.drawChart(excelBuilder.getSheet(), 1, 1, range.length * 2, 25, map);
    }

    private void setDateTitle(ExcelBuilder excelBuilder, Set<LocalDate> dates, int rowNumber) {
        excelBuilder.createRow(rowNumber);
        int cellNumber = 1;
        excelBuilder.createCell(cellNumber++).setCellValue("").setCellTitleStyle();
        for (LocalDate date : dates) {
            excelBuilder.createCell(cellNumber++).setCellValue(date.toString()).setCellTitleStyle();
        }
        excelBuilder.autosizeColumn(dates.size() + 2);
    }

    private void setProductValue(Collection<Map<String, Integer>> productValue, ExcelBuilder excelBuilder, int rowNumber) {
        boolean first = false;
        int rowCounter = rowNumber;
        int cellCounter = 1;
        for (Map<String, Integer> aProductValue : productValue) {
            for (Map.Entry<String, Integer> m : aProductValue.entrySet()) {
                if (!first) {
                    excelBuilder.createRow(rowCounter++);
                    excelBuilder.createCell(cellCounter++).setCellValue(m.getKey()).setCellTitleStyle();
                    excelBuilder.createCell(cellCounter).setCellValue(m.getValue()).setCellValueStyle();
                    cellCounter = 1;
                } else {
                    excelBuilder.getRow(rowCounter++).createCell(cellCounter).setCellValue(m.getValue()).setCellValueStyle();
                }
                excelBuilder.autosizeColumn(cellCounter);
            }
            if (!first) {
                cellCounter = 3;
            } else {
                cellCounter++;
            }
            first = true;
            rowCounter = rowNumber;
        }
    }

    private Map<LocalDate, Map<String, Integer>> prepareDataChart(LocalDate[] range, List<Order> orders) {
        Map<LocalDate, Map<String, Integer>> result = new LinkedHashMap<>();
        LocalDate temp = null;
        for (LocalDate date : range) {
            Map<String, Integer> products = new LinkedHashMap<>();
            for (Order order : orders) {
                LocalDate orderDate = order.getDate().toLocalDate();
                int value = 0;
                if ((temp == null && date.isAfter(orderDate))
                        || (temp != null && date.isAfter(orderDate) && temp.isBefore(orderDate))) {
                    value = 1;
                }
                products.merge(order.getProduct().getTitle(), value, (a, b) -> a + b);
            }
            temp = LocalDate.from(date);
            result.put(date, products);
        }
        return result;
    }


    private String getFilename(Long[] id) {
        if (id.length == 1) {
            return "Orders-" + id[0] + "-";
        } else {
            return "Orders-by_customers-";
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

    private LocalDate convertString(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, dtf);
    }

    private LocalDate[] calculateRange(LocalDate from, LocalDate to) {
        List<LocalDate> result = new ArrayList<>();
        ChronoUnit unit = getChronoUnit(from, to);
        result.add(LocalDate.from(from));
        do {
            if (unit == YEARS) {
                from = from.plusYears(1);
                result.add(LocalDate.from(from));
            } else if (unit == MONTHS) {
                from = from.plusMonths(1);
                result.add(LocalDate.from(from));
            } else {
                from = from.plusDays(1);
                result.add(LocalDate.from(from));
            }
        } while (from.isBefore(to));
        result.add(to);
        return result.toArray(new LocalDate[result.size()]);
    }

    private ChronoUnit getChronoUnit(LocalDate from, LocalDate to) {
        if (from.plusYears(1).isBefore(to)) {
            return YEARS;
        } else if (from.plusMonths(1).isBefore(to)) {
            return MONTHS;
        } else {
            return DAYS;
        }
    }

    private Object getListValue(List<?> list, int index) {
        return list.get(index);
    }
}
