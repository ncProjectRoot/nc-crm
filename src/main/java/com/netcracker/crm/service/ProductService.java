package com.netcracker.crm.service;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductStatusDto;

import java.util.List;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface ProductService {
    Product persist(ProductDto productDto);

    List<ProductStatusDto> getStatuses();

    List<String> getNames(String likeTitle);
}
