package com.netcracker.crm.controller.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.crm.domain.OrderRowRequest;
import com.netcracker.crm.domain.model.Discount;
import com.netcracker.crm.domain.model.Group;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.ProductStatusDto;
import com.netcracker.crm.service.OrderService;
import com.netcracker.crm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private OrderService orderService;

    private ObjectMapper mapper = new ObjectMapper();

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

    @GetMapping("/csr/load/orders")
    public Map<String, Object> orders(OrderRowRequest orderRowRequest) throws IOException {
        return orderService.getOrderRow(orderRowRequest);
    }


}
