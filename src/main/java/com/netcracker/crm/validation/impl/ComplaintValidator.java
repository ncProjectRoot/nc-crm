package com.netcracker.crm.validation.impl;

import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.dto.ComplaintDto;
import com.netcracker.crm.validation.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_REQUIRED;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_WRONG_FORMAT;
import static com.netcracker.crm.validation.field.ComplaintDtoField.*;

/**
 * Created by Pasha on 06.05.2017.
 */
@Component
@PropertySource(value = "classpath:message.properties")
public class ComplaintValidator extends AbstractValidator {

    private final OrderDao orderDao;

    @Autowired
    public ComplaintValidator(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ComplaintDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ComplaintDto complaintDto = (ComplaintDto) target;
        checkOrder(errors, complaintDto);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, MESSAGE.getName(), ERROR_CODE_REQUIRED, getErrorMessage(MESSAGE, ERROR_CODE_REQUIRED));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, ORDER_ID.getName(), ERROR_CODE_REQUIRED, getErrorMessage(ORDER_ID, ERROR_CODE_REQUIRED));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, TITLE.getName(), ERROR_CODE_REQUIRED, getErrorMessage(TITLE, ERROR_CODE_REQUIRED));
    }

    private void checkOrder(Errors errors, ComplaintDto complaintDto) {
        Long orderId = complaintDto.getOrderId();
        if (orderId == null || orderDao.findById(orderId) == null) {
            errors.rejectValue(ORDER_ID.getName(), ERROR_CODE_WRONG_FORMAT,
                    getErrorMessage(ORDER_ID, ERROR_CODE_WRONG_FORMAT));
        }
    }
}
