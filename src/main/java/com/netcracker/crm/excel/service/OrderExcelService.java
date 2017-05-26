package com.netcracker.crm.excel.service;

import com.netcracker.crm.dto.OrderExcelDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Pasha on 22.05.2017.
 */
public interface OrderExcelService {
    void generateCustomerOrders(HttpServletResponse response, OrderExcelDto orderExcelDto) throws IOException;

}
