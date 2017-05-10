package com.netcracker.crm.validation.impl;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.validation.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_WRONG_FORMAT;
import static com.netcracker.crm.validation.field.OrderDtoField.*;

/**
 * Created by Pasha on 07.05.2017.
 */
@Component
@PropertySource(value = "classpath:message.properties")
public class OrderValidator extends AbstractValidator {

    private static final String REGEX_PATTERN_DATE = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

    private static final String REGEX_PATTERN_TIME = "^[0-9]{2}:[0-9]{2}$";

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDto orderDto = (OrderDto) target;
        checkCustomer(errors, orderDto);
        checkProduct(errors, orderDto);
        checkDate(errors, orderDto);
        checkTime(errors, orderDto);
    }


    private void checkCustomer(Errors errors, OrderDto orderDto) {
        Long customerId = orderDto.getCustomerId();
        if (customerId != null) {
            if (userDao.findById(customerId) == null) {
                errors.rejectValue(CUSTOMER_ID.getName(), ERROR_CODE_WRONG_FORMAT,
                        getErrorMessage(CUSTOMER_ID, ERROR_CODE_WRONG_FORMAT));
            }
        }
    }

    private void checkProduct(Errors errors, OrderDto orderDto) {
        Long productId = orderDto.getProductId();
        if (productId != null) {
            if (productDao.findById(productId) == null) {
                errors.rejectValue(PRODUCT_ID.getName(), ERROR_CODE_WRONG_FORMAT,
                        getErrorMessage(PRODUCT_ID, ERROR_CODE_WRONG_FORMAT));
            }
        }
    }

    private void checkDate(Errors errors, OrderDto orderDto) {
        if (!orderDto.getPreferredDate().isEmpty()) {
            if (!orderDto.getPreferredDate().matches(REGEX_PATTERN_DATE)) {
                errors.rejectValue(PREFERRED_DATE.getName(), ERROR_CODE_WRONG_FORMAT,
                        getErrorMessage(PREFERRED_DATE, ERROR_CODE_WRONG_FORMAT));
            }
        }
    }

    private void checkTime(Errors errors, OrderDto orderDto) {
        if (!orderDto.getPreferredTime().isEmpty()) {
            if (!orderDto.getPreferredTime().matches(REGEX_PATTERN_TIME)) {
                errors.rejectValue(PREFERRED_TIME.getName(), ERROR_CODE_WRONG_FORMAT,
                        getErrorMessage(PREFERRED_DATE, ERROR_CODE_WRONG_FORMAT));
            }
        }
    }
}
