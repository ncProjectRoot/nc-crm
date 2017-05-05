package com.netcracker.crm.controller.rest;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.ProductStatusDto;
import com.netcracker.crm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 29.04.2017.
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/csr/addProduct", method = RequestMethod.POST)
    public String addProduct(ProductDto productDto) throws IOException {
        Product product = productService.persist(productDto);
        return "Product with title : " + product.getTitle() + " successful added";
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

    @GetMapping("/csr/load/products")
    public Map<String, Object> orders(ProductRowRequest orderRowRequest) throws IOException {
        return productService.getProductsRow(orderRowRequest);
    }


}
