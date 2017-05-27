
package com.netcracker.crm.validation.impl;

import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_REQUIRED;
import com.netcracker.crm.dao.ProductParamDao;
import com.netcracker.crm.dto.ProductParamDto;
import com.netcracker.crm.validation.AbstractValidator;
import static com.netcracker.crm.validation.field.ProductParamDtoField.PARAM_NAME;
import static com.netcracker.crm.validation.field.ProductParamDtoField.VALUE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author YARUS
 */
@Component
@PropertySource(value = "classpath:message.properties")
public class ProductParamValidator extends AbstractValidator{
    
    private final ProductParamDao productParamDao;
    
    @Autowired
    public ProductParamValidator(ProductParamDao productParamDao) {
        this.productParamDao = productParamDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductParamDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductParamDto productParam = (ProductParamDto) target;        

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PARAM_NAME.getName(), ERROR_CODE_REQUIRED, getErrorMessage(PARAM_NAME, ERROR_CODE_REQUIRED));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, VALUE.getName(), ERROR_CODE_REQUIRED, getErrorMessage(VALUE, ERROR_CODE_REQUIRED));        
    }
}
