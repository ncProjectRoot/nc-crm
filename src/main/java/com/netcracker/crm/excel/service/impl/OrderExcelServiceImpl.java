package com.netcracker.crm.excel.service.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.dto.OrderExcelDto;
import com.netcracker.crm.excel.ExcelMapKey;
import com.netcracker.crm.excel.converter.ExcelConverter;
import com.netcracker.crm.excel.drawer.ExcelDrawer;
import com.netcracker.crm.excel.service.AbstractExcelService;
import com.netcracker.crm.excel.service.OrderExcelService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 22.05.2017.
 */
@Service
public class OrderExcelServiceImpl extends AbstractExcelService<Order> implements OrderExcelService {
    private final OrderDao orderDao;
    private final ExcelConverter converter;
    private String[] titles = {
            "№",
            "Customer full name",
            "Product title",
            "Order date",
            "Prefered date",
            "Order status",
    };

    @Autowired
    public OrderExcelServiceImpl(OrderDao orderDao, ExcelConverter converter, ExcelDrawer excelDrawer) {
        super(excelDrawer);
        this.orderDao = orderDao;
        this.converter = converter;
    }


    public void generateCustomerOrders(OutputStream stream, OrderExcelDto orderExcelDto) throws IOException {
        LocalDate from = convertString(orderExcelDto.getDateFrom());
        LocalDate to = convertString(orderExcelDto.getDateTo());
        orderExcelDto.setIdCustomer(checkId(orderExcelDto.getIdCustomer()));
        List<Order> orders = orderDao.findAllByCustomerIds(Arrays.asList(orderExcelDto.getIdCustomer()), from, to, orderExcelDto.getOrderByIndex());
        LocalDate[] range = calculateRange(from, to);
        Workbook workbook = parseData(orders, range);
        workbook.write(stream);
        stream.close();
    }

    public Map<LocalDate, Map<String, Integer>> prepareDataChart(LocalDate[] range, List<Order> orders) {
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

    @Override
    public Map<ExcelMapKey, List<?>> convertToMap(List<Order> objects) {
        return converter.convertOrdersToMap(objects);
    }

    @Override
    public String[] getTitles() {
        return titles;
    }
}
