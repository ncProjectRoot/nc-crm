package com.netcracker.crm.validation.impl;

import com.netcracker.crm.dto.bulk.DiscountBulkDto;
import com.netcracker.crm.validation.AbstractValidator;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_REQUIRED;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_WRONG_FORMAT;
import static com.netcracker.crm.validation.field.DiscountDtoField.*;

/**
 * Created by Pasha on 06.05.2017.
 */
@Component
@PropertySource(value = "classpath:message.properties")
public class BulkDiscountValidator extends AbstractValidator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DiscountBulkDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DiscountBulkDto bulkDto = (DiscountBulkDto) target;
        if (bulkDto.isPercentageChanged()) checkPercentage(errors, bulkDto);

        if (bulkDto.isDescriptionChanged())
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, DESCRIPTION.getName(), ERROR_CODE_REQUIRED, getErrorMessage(DESCRIPTION, ERROR_CODE_REQUIRED));
        if (bulkDto.isPercentageChanged())
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, PERCENTAGE.getName(), ERROR_CODE_REQUIRED, getErrorMessage(PERCENTAGE, ERROR_CODE_REQUIRED));
    }


    private void checkPercentage(Errors errors, DiscountBulkDto bulkDto) {
        Double percentage = bulkDto.getPercentage();

        if (percentage == null || percentage <= 0 || percentage >= 100) {
            errors.rejectValue(PERCENTAGE.getName(), ERROR_CODE_WRONG_FORMAT,
                    getErrorMessage(PERCENTAGE, ERROR_CODE_WRONG_FORMAT));
        }
    }
}
