package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.OrderService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_ORDER_CREATED;

/**
 * Created by Pasha on 07.05.2017.
 */
@RestController
public class OrderRestController {


    @Autowired
    private ResponseGenerator<Order> generator;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderValidator orderValidator;
    @Autowired
    private BindingResultHandler bindingResultHandler;


    @PostMapping("/customer/put/order")
    public ResponseEntity<?> createOrder(@Valid OrderDto orderDto, BindingResult bindingResult
            , Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = (UserDetailsImpl) principal;
        orderDto.setCustomerId(user.getId());
        orderValidator.validate(orderDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }
        Order order = orderService.persist(orderDto);
        if (order.getId() > 0) {
            return generator.getHttpResponse(order.getId(), SUCCESS_MESSAGE, SUCCESS_ORDER_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/csr/load/orders")
    public Map<String, Object> ordersCsr(OrderRowRequest orderRowRequest) throws IOException {
        return orderService.getOrdersRow(orderRowRequest);
    }

    @GetMapping("/customer/load/orders")
    public Map<String, Object> ordersCustomer(OrderRowRequest orderRowRequest, Authentication authentication) throws IOException {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            orderRowRequest.setCustomerId(customer.getId());
        }
        return orderService.getOrdersRow(orderRowRequest);
    }

    @GetMapping("/orders/users/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER')")
    public List<AutocompleteDto> getAutocompleteDto(String pattern, Authentication authentication,
                                                     @PathVariable(value = "userId") Long userId) throws IOException {
        Object principal = authentication.getPrincipal();
        User customer = null;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
        }
        return orderService.getAutocompleteOrder(pattern, customer);
    }


}
