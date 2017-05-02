package com.netcracker.crm.controller.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductStatusDto;
import com.netcracker.crm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by Pasha on 29.04.2017.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/csr/addProduct", method = RequestMethod.POST)
    public String addProduct(ProductDto productDto, String disc, String grp) throws IOException {
        productDto.setDiscount(disc.length() > 2 ? mapper.readValue(disc, Discount.class) : null);
        productDto.setGroup(grp.length() > 2 ? mapper.readValue(grp, Group.class) : null);
        Product product = productService.persist(productDto);
        return "Product with title : " + product.getTitle() + " successful added";
    }


    @GetMapping("/csr/load/productStatus")
    public List<ProductStatusDto> productStatus() {
        return productService.getStatuses();
    }




}
