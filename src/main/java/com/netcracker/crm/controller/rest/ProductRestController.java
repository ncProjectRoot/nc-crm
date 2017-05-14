package com.netcracker.crm.controller.rest;

import com.netcracker.crm.controller.message.ResponseGenerator;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.ProductStatus;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.controller.message.MessageHeader.ERROR_MESSAGE;
import static com.netcracker.crm.controller.message.MessageHeader.SUCCESS_MESSAGE;
import static com.netcracker.crm.controller.message.MessageProperty.*;

/**
 * Created by Pasha on 29.04.2017.
 */
@RequestMapping(value = "/products")
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

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')")
    public ResponseEntity<?> create(@Valid ProductDto productDto, BindingResult bindingResult) {
        productValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }

        Product product = productService.persist(productDto);
        if (product.getId() > 0) {
            return generator.getHttpResponse(product.getId(), SUCCESS_MESSAGE, SUCCESS_PRODUCT_CREATED, HttpStatus.CREATED);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')")
    public ResponseEntity<?> update(@Valid ProductDto productDto, BindingResult bindingResult) {
        productValidator.validate(productDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return bindingResultHandler.handle(bindingResult);
        }

        Product product = productService.update(productDto);
        if (product.getId() > 0) {
            return generator.getHttpResponse(SUCCESS_MESSAGE, SUCCESS_PRODUCT_UPDATE, HttpStatus.OK);
        }
        return generator.getHttpResponse(ERROR_MESSAGE, ERROR_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/withoutGroup")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')")
    public ResponseEntity<List<ProductGroupDto>> productsWithoutGroup() {
        return new ResponseEntity<>(productService.getProductsWithoutGroup(), HttpStatus.OK);
    }

    @GetMapping("/names")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_CUSTOMER')")
    public ResponseEntity<List<String>> productNames(String likeTitle, String type, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = (UserDetailsImpl) principal;
        List<String> result;
        if (user.getUserRole() == UserRole.ROLE_CUSTOMER) {
            switch (type) {
                case "actual":
                    result = productService.getActualNamesByCustomerId(likeTitle, user.getId());
                    break;
                case "possible":
                    result = productService.getActualNamesByCustomerId(likeTitle, user.getId(), user.getAddress());
                    break;
                default:
                    result = productService.getNamesByCustomerId(likeTitle, user.getId());
            }
        } else {
            result = productService.getTitlesLikeTitle(likeTitle);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CSR', 'ROLE_CUSTOMER')")
    public ResponseEntity<Map<String, Object>> productRows(ProductRowRequest productRowRequest, String type, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        User user = (UserDetailsImpl) principal;
        Map<String, Object> result;
        if (user.getUserRole() == UserRole.ROLE_CUSTOMER) {
            productRowRequest.setCustomerId(user.getId());
            productRowRequest.setStatusId(ProductStatus.ACTUAL.getId());
            switch (type) {
                case "possible":
                    productRowRequest.setAddress(user.getAddress());
                    result = productService.getProductsRow(productRowRequest);
                    break;
                default:
                    result = productService.getProductsRow(productRowRequest);
            }
        } else {
            result = productService.getProductsRow(productRowRequest);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
