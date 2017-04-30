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
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
public class ProductDAOImpl implements ProductDao {

    private static final Logger log = LoggerFactory.getLogger(DiscountDaoImpl.class);

    @Autowired
    private static DiscountDao discountDao;
    @Autowired
    private static GroupDao groupDao;
    @Autowired
    private static StatusDao statusDao;

    private SimpleJdbcInsert productInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public Long create(Product product) {
        if (product.getId() != null) {
            return -1L;
        }

        Long discountId = getDiscountId(product.getDiscount());
        Long groupId = getGroupId(product.getGroup());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_TITLE, product.getTitle())
                .addValue(PARAM_PRODUCT_DEFAULT_PRICE, product.getDefaultPrice())
                .addValue(PARAM_PRODUCT_STATUS_ID, product.getStatus().getId())
                .addValue(PARAM_PRODUCT_DESCRIPTION, product.getDescription())
                .addValue(PARAM_PRODUCT_DISCOUNT_ID, discountId)
                .addValue(PARAM_PRODUCT_GROUP_ID, groupId)
                ;

        long newId = productInsert.executeAndReturnKey(params)
                .longValue();

        log.info("User with id: " + newId + " is successfully created.");
        return newId;
    }

    @Override
    public Long update(Product product) {
        Long discountId = getDiscountId(product.getDiscount());
        Long groupId = getGroupId(product.getGroup());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_TITLE, product.getTitle())
                .addValue(PARAM_PRODUCT_DEFAULT_PRICE, product.getDefaultPrice())
                .addValue(PARAM_PRODUCT_STATUS_ID, product.getStatus().getId())
                .addValue(PARAM_PRODUCT_DESCRIPTION, product.getDescription())
                .addValue(PARAM_PRODUCT_DISCOUNT_ID, discountId)
                .addValue(PARAM_PRODUCT_GROUP_ID, groupId)
                ;

        int affectedRows = namedJdbcTemplate.update(SQL_UPDATE_PRODUCT, params);

        if (affectedRows > 0) {
            log.info("Product with id: " + product.getId() + " is successfully updated.");
            return product.getId();
        } else {
            log.error("Product was not updated.");
            return -1L;
        }
    }

    @Override
    public Long delete(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public Long delete(Product object) {
        throw new NotImplementedException();
    }

    @Override
    public Product findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_ID, id);

        List<Product> allProduct = namedJdbcTemplate.query(SQL_FIND_PRODUCT_BY_ID, params, new ProductWithDetailExtractor());
        return allProduct.get(0);
    }

    @Override
    public Product findByTitle(String title) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_TITLE, title);

        List<Product> allProduct = namedJdbcTemplate.query(SQL_FIND_PRODUCT_BY_TITLE, params, new ProductWithDetailExtractor());
        return allProduct.get(0);
    }

    @Override
    public List<Product> findAllByGroupId(Long groupId) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_PRODUCT_GROUP_ID, groupId);

        return  namedJdbcTemplate.query(SQL_FIND_ALL_PRODUCT_BY_GROUP_ID, params, new ProductWithDetailExtractor());
    }

    private Long getDiscountId(Discount discount) {
        Long discountId = discount.getId();
        if (discountId != null) {
            return discountId;
        }
        discountId = discountDao.create(discount);

        return discountId;
    }

    private Long getGroupId(Group group) {
        Long groupId = group.getId();
        if (groupId != null) {
            return groupId;
        }
        groupId = groupDao.create(group);

        return groupId;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.productInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_PRODUCT_TABLE)
                .usingGeneratedKeyColumns(PARAM_PRODUCT_ID);
    }

    private static final class ProductWithDetailExtractor implements ResultSetExtractor<List<Product>> {
        @Override
        public List<Product> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<Product> allProduct = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong(PARAM_PRODUCT_ID));
                product.setTitle(rs.getString(PARAM_PRODUCT_TITLE));
                product.setDefaultPrice(rs.getDouble(PARAM_PRODUCT_DEFAULT_PRICE));
                product.setDescription(rs.getString(PARAM_PRODUCT_DESCRIPTION));

                long statusId = rs.getLong(PARAM_PRODUCT_STATUS_ID);
                Status status = statusDao.findById(statusId);

                if (status instanceof ProductStatus) {
                    product.setStatus((ProductStatus) status);
                }


                Long discountId = rs.getLong(PARAM_PRODUCT_DISCOUNT_ID);
                if (discountId > 0) {
                    Discount discount = discountDao.findById(discountId);
                    product.setDiscount(discount);
                }

                Long groupId = rs.getLong(PARAM_PRODUCT_GROUP_ID);
                if (groupId > 0) {
                    Group group = groupDao.findById(groupId);
                    product.setGroup(group);
                }

                allProduct.add(product);
            }
            return allProduct;
        }
    }
}
