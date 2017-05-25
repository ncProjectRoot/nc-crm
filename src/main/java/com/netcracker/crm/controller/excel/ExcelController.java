package com.netcracker.crm.controller.excel;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.dto.OrderExcelDto;
import com.netcracker.crm.excel.service.OrderExcelService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.OrderExcelValidator;
import com.netcracker.crm.validation.impl.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_ORDER_CREATED;

/**
 * Created by Pasha on 22.05.2017.
 */
@RestController
public class ExcelController {
    private final OrderExcelService orderExcelService;
    private final OrderExcelValidator orderExcelValidator;
    private final BindingResultHandler bindingResultHandler;



    @Autowired
    public ExcelController(OrderExcelService orderExcelService,OrderExcelValidator orderExcelValidator,
                           BindingResultHandler bindingResultHandler) {
        this.orderExcelService = orderExcelService;
        this.orderExcelValidator = orderExcelValidator;
        this.bindingResultHandler = bindingResultHandler;
    }

    @GetMapping(path = "/*/report/users")
    public ResponseEntity<?> download(HttpServletResponse response, @Valid OrderExcelDto orderExcelDto, BindingResult bindingResult) throws IOException {
        orderExcelValidator.validate(orderExcelDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }
        orderExcelService.generateCustomerOrders(response, orderExcelDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
