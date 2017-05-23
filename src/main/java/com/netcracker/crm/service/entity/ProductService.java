package com.netcracker.crm.service.entity;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.request.ProductRowRequest;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.ProductDto;
import com.netcracker.crm.dto.ProductGroupDto;
import com.netcracker.crm.dto.bulk.ProductBulkDto;

import java.util.List;
import java.util.Map;

/**
 * Created by Pasha on 01.05.2017.
 */
public interface ProductService {

    Product create(ProductDto productDto, User user);

    boolean update(ProductDto productDto, User user);

    Product getProductsById(Long id);
    List<Product> getProductsByGroupId(Long id);

    List<AutocompleteDto> getAutocompleteDto(String pattern);

    List<AutocompleteDto> getAutocompleteDtoWithoutGroup(String pattern);

    List<AutocompleteDto> getActualProductsAutocompleteDtoByCustomer(String pattern, User customer);

    List<AutocompleteDto> getPossibleProductsAutocompleteDtoByCustomer(String pattern, User customer);

    Map<String, Object> getProductsRow(ProductRowRequest orderRowRequest);

    boolean hasCustomerAccessToProduct(Long productId, Long customerId);

    boolean changeStatus(Long productId, Long statusId, User user);

    boolean bulkUpdate(ProductBulkDto bulkDto, User user);

    List<Product> getProductsByDiscountId(Long id, User user);
}
