package com.netcracker.crm.validation;

import com.netcracker.crm.validation.field.DtoField;
import org.springframework.core.env.Environment;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

import static com.netcracker.crm.controller.message.MessageProperty.REPLACE_WILD_CARD;

/**
 * Created by bpogo on 5/6/2017.
 */
public abstract class AbstractValidator implements Validator {
    private static final String DEFAULT_VALIDATION_ERROR = "Validation error";

    @Resource
    private Environment env;

    protected String getErrorMessage(DtoField field, String errorCode) {
        if (env.containsProperty(errorCode)) {
            String errorTemplate = env.getProperty(errorCode);
            if (errorTemplate.startsWith(REPLACE_WILD_CARD)) {
                return errorTemplate.replace(REPLACE_WILD_CARD, field.getErrorName());
            } else {
                return errorTemplate.replace(REPLACE_WILD_CARD, field.getErrorName().toLowerCase());
            }
        }
        return DEFAULT_VALIDATION_ERROR;
    }
}
