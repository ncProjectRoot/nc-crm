package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.OrderRowRequest;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.ProductStatusDto;
import com.netcracker.crm.service.entity.OrderService;
import com.netcracker.crm.service.entity.ProductService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.ERROR_SERVER_ERROR;
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_PRODUCT_CREATED;

/**
 * Created by Pasha on 29.04.2017.
 */
@RestController
public class ProductRestController {
    private final ProductService productService;
    private final OrderService orderService;
    private final BindingResultHandler bindingResultHandler;
    private final ProductValidator productValidator;
    private final ResponseGenerator<Product> generator;

    @Autowired
    public ProductRestController(ProductService productService, OrderService orderService,
                             BindingResultHandler bindingResultHandler, ProductValidator productValidator,
                             ResponseGenerator<Product> generator) {
        this.productService = productService;
        this.orderService = orderService;
        this.bindingResultHandler = bindingResultHandler;
        this.productValidator = productValidator;
        this.generator = generator;
    }

    @RequestMapping(value = "/csr/addProduct", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@Valid ProductDto productDto, BindingResult bindingResult) {
        productValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()){
            return bindingResultHandler.handle(bindingResult);
        }

        Product product = productService.persist(productDto);
        if(product.getId() > 0){
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_PRODUCT_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping("/csr/load/productStatus")
    public List<ProductStatusDto> productStatus() {
        return productService.getStatuses();
    }

    @GetMapping("/csr/load/productWithoutGroup")
    public List<ProductGroupDto> productsWithoutGroup() {
        return productService.getProductsWithoutGroup();
    }

    @GetMapping("/csr/load/productNames")
    public List<String> productNames(String likeTitle) {
        return productService.getNames(likeTitle);
    }

    @GetMapping("/csr/load/orders")
    public Map<String, Object> orders(OrderRowRequest orderRowRequest) throws IOException {
        return orderService.getOrderRow(orderRowRequest);
    }


}
