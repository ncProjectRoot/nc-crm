package com.netcracker.crm.controller.rest;

import static com.netcracker.crm.controller.message.MessageHeader.*;
import static com.netcracker.crm.controller.message.MessageProperty.*;
import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.ProductParam;
import com.netcracker.crm.dto.ProductParamDto;
import com.netcracker.crm.service.entity.ProductParamService;
import com.netcracker.crm.validation.BindingResultHandler;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author YARUS
 */
@RestController
@RequestMapping(value = "/productParams")
public class ProductParamRestController {
    private final ProductParamService productParamService;    
    private final ResponseGenerator<ProductParam> generator;
    private final BindingResultHandler bindingResultHandler;
    
    @Autowired
    public ProductParamRestController(ProductParamService productParamService, ResponseGenerator<ProductParam> generator, BindingResultHandler bindingResultHandler) {
        this.productParamService = productParamService;        
        this.generator = generator;
        this.bindingResultHandler = bindingResultHandler;
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<?> create(@Valid ProductParamDto productParamDto, BindingResult bindingResult) {
        //discountValidator.validate(productParamDto, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return bindingResultHandler.handle(bindingResult);
//        }
        ProductParam pParam = productParamService.create(productParamDto);
        if (pParam.getId() > 0) {
            return generator.getHttpResponse(pParam.getId(), SUCCESS_MESSAGE, SUCCESS_PRODUCT_PARAM_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<?> update(@Valid ProductParamDto productParamDto, BindingResult bindingResult) {
//        discountValidator.validate(productParamDto, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return bindingResultHandler.handle(bindingResult);
//        }
        if (productParamService.update(productParamDto)) {
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_PRODUCT_PARAM_UPDATED, HttpStatus.OK);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping
    @RequestMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CSR', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
//        discountValidator.validate(productParamDto, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return bindingResultHandler.handle(bindingResult);
//        }
        if (productParamService.delete(id)) {
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_PRODUCT_PARAM_DELETED, HttpStatus.OK);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
