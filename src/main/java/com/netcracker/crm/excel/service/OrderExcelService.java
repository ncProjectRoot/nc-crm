package com.netcracker.crm.excel.service;

import com.netcracker.crm.dto.OrderExcelDto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Pasha on 22.05.2017.
 */
public interface OrderExcelService {
    void generateCustomerOrders(OutputStream stream, OrderExcelDto orderExcelDto)  throws IOException;

}
