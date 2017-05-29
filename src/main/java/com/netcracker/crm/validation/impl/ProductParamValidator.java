package com.netcracker.crm.validation.impl;

import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_PRODUCT_PARAM_ALREADY_EXIST;
import com.netcracker.crm.dao.ProductParamDao;
import com.netcracker.crm.dto.ProductParamDto;
import com.netcracker.crm.validation.AbstractValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import static com.netcracker.crm.controller.message.MessageProperty.ERROR_CODE_REQUIRED;
import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.domain.model.ProductParam;
import com.netcracker.crm.dto.mapper.impl.ProductParamMapper;
import static com.netcracker.crm.validation.field.ProductParamDtoField.PARAM_NAME;
import static com.netcracker.crm.validation.field.ProductParamDtoField.VALUE;
import java.util.List;

/**
 *
 * @author YARUS
 */
@Component
@PropertySource(value = "classpath:message.properties")
public class ProductParamValidator extends AbstractValidator {

    private final ProductParamDao productParamDao;
    private final ProductDao productDao;
    private final ProductParamMapper paramMapper;

    @Autowired
    public ProductParamValidator(ProductParamDao productParamDao, ProductDao productDao, ProductParamMapper paramMapper) {
        this.productParamDao = productParamDao;
        this.productDao = productDao;
        this.paramMapper = paramMapper;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductParamDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductParamDto productParamDto = (ProductParamDto) target;

        checkUnique(errors, productParamDto);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PARAM_NAME.getName(), ERROR_CODE_REQUIRED, getErrorMessage(PARAM_NAME, ERROR_CODE_REQUIRED));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, VALUE.getName(), ERROR_CODE_REQUIRED, getErrorMessage(VALUE, ERROR_CODE_REQUIRED));
    }
    
    public void validateOnEdit(Object target, Errors errors) {
        ProductParamDto productParamDto = (ProductParamDto) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PARAM_NAME.getName(), ERROR_CODE_REQUIRED, getErrorMessage(PARAM_NAME, ERROR_CODE_REQUIRED));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, VALUE.getName(), ERROR_CODE_REQUIRED, getErrorMessage(VALUE, ERROR_CODE_REQUIRED));
    }

    private void checkUnique(Errors errors, ProductParamDto productParamDto) {
        Long id = productParamDto.getProductId();
        List<ProductParam> productParams = productParamDao.findAllByProductId(id);
        for (ProductParam pr :productParams){
            if(pr.getParamName().equalsIgnoreCase(productParamDto.getParamName()))
                errors.rejectValue(PARAM_NAME.getName(), ERROR_CODE_PRODUCT_PARAM_ALREADY_EXIST,
                    getErrorMessage(PARAM_NAME, ERROR_CODE_PRODUCT_PARAM_ALREADY_EXIST));            
        }              
    }
}
