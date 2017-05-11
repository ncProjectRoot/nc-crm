package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Address;
import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface ProductService {
    Product persist(ProductDto productDto);

    Product update(ProductDto productDto);

    List<ProductGroupDto> getProductsWithoutGroup();

    List<String> getTitlesLikeTitle(String likeTitle);

    Map<String, Object> getProductsRow(ProductRowRequest orderRowRequest);

    List<String> getNamesByCustomerId(String likeTitle, Long customerId);

    List<String> getActualNamesByCustomerId(String likeTitle, Long customerId, Address address);

    Product getProductsById(Long id);

    List<String> getActualNamesByCustomerId(String likeTitle, Long customerId);

    boolean hasCustomerAccessToProduct(Long productId, Long customerId);
}
