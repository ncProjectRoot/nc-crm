package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Product;
import java.util.List;

/**
 *
 * @author YARUS
 */
public interface ProductDao extends CrudDao<Product>{
    Product findByTitle(String title);

    List<Product> findAllByGroupId(Long id);
}
