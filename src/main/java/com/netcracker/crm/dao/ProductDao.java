package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.request.ProductRowRequest;

import java.util.List;
import java.util.Set;

/**
 * @author YARUS
 */
public interface ProductDao extends CrudDao<Product>, BulkDao<Product> {
    Product findByTitle(String title);

    List<Product> findAllByGroupId(Long id);

    List<Product> findAllWithoutGroup();

    List<String> findProductsTitleLikeTitle(String likeTitle);

    Long getProductRowsCount(ProductRowRequest orderRowRequest);

    List<Product> findProductRows(ProductRowRequest orderRowRequest);

    List<String> findProductsTitleByCustomerId(String likeTitle, Long customerId);

    List<String> findActualProductsTitleByCustomerId(String likeTitle, Long customerId, Long regionId);

    List<String> findActualProductsTitleByCustomerId(String likeTitle, Long customerId);

    Boolean hasCustomerAccessToProduct(Long productId, Long customerId);

    boolean hasSameStatus(Set<Long> productIDs);
}
