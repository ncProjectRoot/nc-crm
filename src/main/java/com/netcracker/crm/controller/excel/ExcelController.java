package com.netcracker.crm.controller.excel;

import com.netcracker.crm.dto.OrderExcelDto;
import com.netcracker.crm.excel.service.OrderExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasha on 22.05.2017.
 */
@RestController
public class ExcelController {
    private final OrderExcelService orderExcelService;

    @Autowired
    public ExcelController(OrderExcelService orderExcelService) {
        this.orderExcelService = orderExcelService;
    }

    @GetMapping(path = "/*/report/users")
    public void download(HttpServletResponse response, OrderExcelDto orderExcelDto) throws IOException {
        orderExcelService.generateCustomerOrders(response, orderExcelDto);
    }
}
