package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.*;
import com.netcracker.crm.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.ProductSqlQuery.*;

import org.springframework.stereotype.Repository;

/**
 * @author Karpunets
 * @since 29.04.2017
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    private static final Logger log = LoggerFactory.getLogger(DiscountDaoImpl.class);

    @Autowired
    private DiscountDao discountDao;
    @Autowired
    private GroupDao groupDao;

    private SimpleJdbcInsert productInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private ProductWithDetailExtractor productWithDetailExtractor;

    @Override
    public Long create(Product product) {
        if (product.getId() != null) {
            return null;
        }

        Long discountId = getDiscountId(product.getDiscount());
        Long groupId = getGroupId(product.getGroup());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_TITLE, product.getTitle())
                .addValue(PARAM_PRODUCT_DEFAULT_PRICE, product.getDefaultPrice())
                .addValue(PARAM_PRODUCT_STATUS_ID, product.getStatus().getId())
                .addValue(PARAM_PRODUCT_DESCRIPTION, product.getDescription())
                .addValue(PARAM_PRODUCT_DISCOUNT_ID, discountId)
                .addValue(PARAM_PRODUCT_GROUP_ID, groupId);

        long newId = productInsert.executeAndReturnKey(params)
                .longValue();

        product.setId(newId);

        log.info("Product with id: " + newId + " is successfully created.");
        return newId;
    }

    @Override
    public Long update(Product product) {
        Long productId = product.getId();
        if (productId == null) {
            return null;
        }
        Long discountId = getDiscountId(product.getDiscount());
        Long groupId = getGroupId(product.getGroup());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_ID, productId)
                .addValue(PARAM_PRODUCT_TITLE, product.getTitle())
                .addValue(PARAM_PRODUCT_DEFAULT_PRICE, product.getDefaultPrice())
                .addValue(PARAM_PRODUCT_STATUS_ID, product.getStatus().getId())
                .addValue(PARAM_PRODUCT_DESCRIPTION, product.getDescription())
                .addValue(PARAM_PRODUCT_DISCOUNT_ID, discountId)
                .addValue(PARAM_PRODUCT_GROUP_ID, groupId);

        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_PRODUCT, params);

        if (affectedRows > 0) {
            log.info("Product with id: " + productId + " is successfully updated.");
            return productId;
        } else {
            log.error("Product was not updated.");
            return -1L;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue(PARAM_PRODUCT_ID, id);
            long deletedRows = namedJdbcTemplate.update(SQL_DELETE_PRODUCT, params);
            if (deletedRows == 0) {
                log.error("Product has not been deleted");
                return null;
            } else {
                log.info("Product with id " + id + " was successfully deleted");
                return deletedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(Product product) {
        if (product != null) {
            return delete(product.getId());
        }
        return null;
    }

    @Override
    public Product findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_ID, id);

        List<Product> allProduct = namedJdbcTemplate.query(SQL_FIND_PRODUCT_BY_ID, params, productWithDetailExtractor);
        Product product = null;
        if (allProduct.size() != 0) {
            product = allProduct.get(0);
        }
        return product;
    }

    @Override
    public Product findByTitle(String title) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_TITLE, title);

        List<Product> allProduct = namedJdbcTemplate.query(SQL_FIND_PRODUCT_BY_TITLE, params, productWithDetailExtractor);
        Product product = null;
        if (allProduct.size() != 0) {
            product = allProduct.get(0);
        }
        return product;
    }

    @Override
    public List<Product> findAllByGroupId(Long groupId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_GROUP_ID, groupId);

        return namedJdbcTemplate.query(SQL_FIND_ALL_PRODUCT_BY_GROUP_ID, params, productWithDetailExtractor);
    }

    private Long getDiscountId(Discount discount) {
        if (discount != null) {
            Long discountId = discount.getId();
            if (discountId != null) {
                return discountId;
            }
            discountId = discountDao.create(discount);

            return discountId;
        }
        return null;
    }

    private Long getGroupId(Group group) {
        if (group != null) {
            Long groupId = group.getId();
            if (groupId != null) {
                return groupId;
            }
            groupId = groupDao.create(group);

            return groupId;
        }
        return null;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.productInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_PRODUCT_TABLE)
                .usingGeneratedKeyColumns(PARAM_PRODUCT_ID);
        productWithDetailExtractor = new ProductWithDetailExtractor(discountDao, groupDao);
    }

    private static final class ProductWithDetailExtractor implements ResultSetExtractor<List<Product>> {

        private DiscountDao discountDao;
        private GroupDao groupDao;

        public ProductWithDetailExtractor(DiscountDao discountDao, GroupDao groupDao) {
            this.discountDao = discountDao;
            this.groupDao = groupDao;
        }

        @Override
        public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<Product> allProduct = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong(PARAM_PRODUCT_ID));
                product.setTitle(rs.getString(PARAM_PRODUCT_TITLE));
                product.setDefaultPrice(rs.getDouble(PARAM_PRODUCT_DEFAULT_PRICE));
                product.setDescription(rs.getString(PARAM_PRODUCT_DESCRIPTION));

                Status status = Status.getStatusByID(rs.getLong(PARAM_PRODUCT_STATUS_ID));
                if (status instanceof ProductStatus) {
                    product.setStatus((ProductStatus) status);
                }

                Long discountId = rs.getLong(PARAM_PRODUCT_DISCOUNT_ID);
                if (discountId > 0) {
                    product.setDiscount(discountDao.findById(discountId));
                }

                Long groupId = rs.getLong(PARAM_PRODUCT_GROUP_ID);
                if (groupId > 0) {
                    product.setGroup(groupDao.findById(groupId));
                }

                allProduct.add(product);
            }
            return allProduct;
        }
    }
}
