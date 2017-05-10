package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.ProductService;
import com.netcracker.crm.validation.BindingResultHandler;
import com.netcracker.crm.validation.impl.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import static com.netcracker.crm.controller.message.MessageProperty.SUCCESS_PRODUCT_UPDATE;

/**
 * Created by Pasha on 29.04.2017.
 */
@RestController
public class ProductRestController {
    private final ProductService productService;
    private final BindingResultHandler bindingResultHandler;
    private final ProductValidator productValidator;
    private final ResponseGenerator<Product> generator;

    @Autowired
    public ProductRestController(ProductService productService,
                             BindingResultHandler bindingResultHandler, ProductValidator productValidator,
                             ResponseGenerator<Product> generator) {
        this.productService = productService;
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

    @RequestMapping(value = "/csr/post/product", method = RequestMethod.POST)
    public ResponseEntity<?> updateProduct(@Valid ProductDto productDto, BindingResult bindingResult) {
        productValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()){
            return bindingResultHandler.handle(bindingResult);
        }

        Product product = productService.update(productDto);
        if(product.getId() > 0){
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_PRODUCT_UPDATE, HttpStatus.OK);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/csr/load/productWithoutGroup")
    public List<ProductGroupDto> productsWithoutGroup() {
        return productService.getProductsWithoutGroup();
    }

    @GetMapping("/csr/load/productNames")
    public List<String> productNamesForCsr(String likeTitle) {
        return productService.getTitlesLikeTitle(likeTitle);
    }

    @GetMapping("/customer/load/productNames")
    public List<String> productNamesForCustomer(String likeTitle, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            return productService.getNamesByCustomerId(likeTitle, customer.getId());
        }
        return productService.getTitlesLikeTitle(likeTitle);
    }

    @GetMapping("/customer/load/actualProductNames")
    public List<String> actualProductNamesForCustomer(String likeTitle, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            return productService.getActualNamesByCustomerId(likeTitle, customer.getId());
        }
        return productService.getTitlesLikeTitle(likeTitle);
    }

    @GetMapping("/customer/load/possibleProductNames")
    public List<String> possibleProductNamesForCustomer(String likeTitle, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            return productService.getActualNamesByCustomerId(likeTitle, customer.getId(), customer.getAddress());
        }
        return productService.getTitlesLikeTitle(likeTitle);
    }


    @GetMapping("/csr/load/products")
    public Map<String, Object> allProductsForCsr(ProductRowRequest orderRowRequest) {
        return productService.getProductsRow(orderRowRequest);
    }

    @GetMapping("/customer/load/products")
    public Map<String, Object> customerProducts(ProductRowRequest orderRowRequest, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            orderRowRequest.setCustomerId(customer.getId());
        }
        orderRowRequest.setStatusId(ProductStatus.ACTUAL.getId());
        return productService.getProductsRow(orderRowRequest);
    }

    @GetMapping("/customer/load/possibleProducts")
    public Map<String, Object> possibleProductForCustomer(ProductRowRequest productRowRequest, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User customer;
        if (principal instanceof UserDetailsImpl) {
            customer = (UserDetailsImpl) principal;
            productRowRequest.setCustomerId(customer.getId());
            productRowRequest.setAddress(customer.getAddress());
        }
        productRowRequest.setStatusId(ProductStatus.ACTUAL.getId());
        return productService.getProductsRow(productRowRequest);
    }



}
