package com.netcracker.crm.service;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.ProductStatusDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface ProductService {
    Product persist(ProductDto productDto);

    List<ProductStatusDto> getStatuses();

    List<ProductGroupDto> getProductsWithoutGroup();

    List<String> getNames(String likeTitle);

    Map<String,Object> getProductsRow(ProductRowRequest orderRowRequest);
}
