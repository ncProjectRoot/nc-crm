package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.*;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.proxy.HistoryProxy;
import com.netcracker.crm.dto.GraphDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.netcracker.crm.dao.impl.sql.HistorySqlQuery.*;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author YARUS
 */
@Repository
public class HistoryDaoImpl implements HistoryDao {

    private static final Logger log = LoggerFactory.getLogger(HistoryDaoImpl.class);

    @Autowired
    private ComplaintDao complaintDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    private SimpleJdbcInsert historyInsert;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private HistoryWithDetailExtractor historyWithDetailExtractor;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.historyInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(PARAM_HISTORY_TABLE)
                .usingGeneratedKeyColumns(PARAM_HISTORY_ID);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.historyWithDetailExtractor = new HistoryWithDetailExtractor(complaintDao, orderDao, productDao);
    }

    @Override
    public Long create(History history) {
        if (history.getId() != null) {
            return null;
        }
        Long orderId = getOrderId(history.getOrder());
        Long complaintId = getComplaintId(history.getComplaint());
        Long productId = getProductId(history.getProduct());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, history.getDateChangeStatus())
                .addValue(PARAM_HISTORY_DESC_CHANGE_STATUS, history.getDescChangeStatus())
                .addValue(PARAM_HISTORY_NEW_STATUS_ID, history.getNewStatus().getId())
                .addValue(PARAM_HISTORY_ORDER_ID, orderId)
                .addValue(PARAM_HISTORY_COMPLAINT_ID, complaintId)
                .addValue(PARAM_HISTORY_PRODUCT_ID, productId);

        long id = historyInsert.executeAndReturnKey(params).longValue();
        history.setId(id);
        log.info("History with id: " + id + " is successfully created.");
        return id;
    }

    private Long getOrderId(Order order) {
        if (order != null) {
            Long orderId = order.getId();
            if (orderId != null) {
                return orderId;
            } else {
                return orderDao.create(order);
            }
        }
        return null;
    }

    private Long getComplaintId(Complaint complaint) {
        if (complaint != null) {
            Long complaintId = complaint.getId();
            if (complaintId != null) {
                return complaintId;
            } else {
                return complaintDao.create(complaint);
            }
        }
        return null;
    }

    private Long getProductId(Product product) {
        if (product != null) {
            Long productId = product.getId();
            if (productId != null) {
                return productId;
            } else {
                return productDao.create(product);
            }
        }
        return null;
    }

    @Override
    public Long update(History history) {
        Long historyId = history.getId();
        if (historyId == null) {
            return null;
        }
        Long orderId = getOrderId(history.getOrder());
        Long complaintId = getComplaintId(history.getComplaint());
        Long productId = getProductId(history.getProduct());

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ID, historyId)
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, history.getDateChangeStatus())
                .addValue(PARAM_HISTORY_DESC_CHANGE_STATUS, history.getDescChangeStatus())
                .addValue(PARAM_HISTORY_NEW_STATUS_ID, history.getNewStatus().getId())
                .addValue(PARAM_HISTORY_ORDER_ID, orderId)
                .addValue(PARAM_HISTORY_COMPLAINT_ID, complaintId)
                .addValue(PARAM_HISTORY_PRODUCT_ID, productId);

        int updatedRows = namedJdbcTemplate.update(SQL_UPDATE_HISTORY, params);

        if (updatedRows > 0) {
            log.info("History with id: " + historyId + " is successfully updated.");
            return historyId;
        } else {
            log.error("History was not updated.");
            return null;
        }
    }

    @Override
    public Long delete(Long id) {
        if (id != null) {
            SqlParameterSource params = new MapSqlParameterSource().addValue(PARAM_HISTORY_ID, id);

            long affectedRows = namedJdbcTemplate.update(SQL_DELETE_HISTORY, params);

            if (affectedRows > 0) {
                log.info("History with id: " + id + " is successfully deleted.");
                return affectedRows;
            } else {
                log.error("History doesn't deleted.");
                return affectedRows;
            }
        }
        return null;
    }

    @Override
    public Long delete(History history) {
        if (history != null) {
            return delete(history.getId());
        }
        return null;
    }

    @Override
    public History findById(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ID, id);
        List<History> allHistory = namedJdbcTemplate.query(SQL_FIND_HISTORY_BY_ID, params, historyWithDetailExtractor);
        History history = null;
        if (allHistory.size() != 0) {
            history = allHistory.get(0);
        }
        return history;
    }

    @Override
    public List<History> findAllByDate(LocalDate date) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_DATE_CHANGE_STATUS, date);
        return namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_DATE, params, historyWithDetailExtractor);
    }

    @Override
    public List<History> findAllByOrderId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_ORDER_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_ORDER_ID, params, historyWithDetailExtractor);
    }

    @Override
    public List<History> findAllByComplaintId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_COMPLAINT_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_COMPLAINT_ID, params, historyWithDetailExtractor);
    }

    @Override
    public List<History> findAllByProductId(Long id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_HISTORY_PRODUCT_ID, id);
        return namedJdbcTemplate.query(SQL_FIND_ALL_HISTORY_BY_PRODUCT_ID, params, historyWithDetailExtractor);
    }

    @Override
    public GraphDto findComplaintHistoryBetweenDateChangeByProductIds(LocalDate fromDate, LocalDate toDate, GraphDto graphDto) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GRAPH_FROM_DATE, fromDate)
                .addValue(PARAM_GRAPH_TO_DATE, toDate);

        String sql = createSqlBetweenDateChangeAndProductIds(BEGIN_SQL_GRAPH_FOR_COMPLAINTS, graphDto.getElementIds(), ComplaintStatus.OPEN.getId());
        return namedJdbcTemplate.query(sql, params, new GraphExtractor(graphDto, fromDate, toDate));
    }

    @Override
    public GraphDto findOrderHistoryBetweenDateChangeByProductIds(LocalDate fromDate, LocalDate toDate, GraphDto graphDto) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue(PARAM_GRAPH_FROM_DATE, fromDate)
                .addValue(PARAM_GRAPH_TO_DATE, toDate);

        String sql = createSqlBetweenDateChangeAndProductIds(BEGIN_SQL_GRAPH_FOR_ORDER, graphDto.getElementIds(), OrderStatus.NEW.getId());
        return namedJdbcTemplate.query(sql, params, new GraphExtractor(graphDto, fromDate, toDate));
    }

    private String createSqlBetweenDateChangeAndProductIds(String beginSgl, List<Long> elementIds, Long statusId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(beginSgl);
        for (int i = 0; i < elementIds.size(); i++) {
            if (i == 0) {
                stringBuilder.append(" AND ( ");
            }
            stringBuilder.append(" o.product_id = ");
            stringBuilder.append(elementIds.get(i));
            if (i == elementIds.size() - 1) {
                stringBuilder.append(" ) ");
            } else {
                stringBuilder.append(" OR ");
            }
        }
        stringBuilder.append(" AND h.new_status_id = ");
        stringBuilder.append(statusId);
        stringBuilder.append(SQL_GRAPH_GROUP_BY_AND_ORDER_BY);
        return stringBuilder.toString();
    }

    private static final class HistoryWithDetailExtractor implements ResultSetExtractor<List<History>> {

        private ComplaintDao complaintDao;
        private OrderDao orderDao;
        private ProductDao productDao;

        HistoryWithDetailExtractor(ComplaintDao complaintDao, OrderDao orderDao, ProductDao productDao) {
            this.complaintDao = complaintDao;
            this.orderDao = orderDao;
            this.productDao = productDao;
        }

        @Override
        public List<History> extractData(ResultSet rs) throws SQLException, DataAccessException {
            ArrayList<History> allHistory = new ArrayList<>();
            while (rs.next()) {
                HistoryProxy history = new HistoryProxy(orderDao, complaintDao, productDao);
                history.setId(rs.getLong(PARAM_HISTORY_ID));
                history.setDateChangeStatus(rs.getTimestamp(PARAM_HISTORY_DATE_CHANGE_STATUS).toLocalDateTime());
                history.setDescChangeStatus(rs.getString(PARAM_HISTORY_DESC_CHANGE_STATUS));

                long statusId = rs.getLong(PARAM_HISTORY_NEW_STATUS_ID);
                if (statusId > 0) {
                    history.setNewStatus(Status.getStatusByID(statusId));
                }

                history.setOrderId(rs.getLong(PARAM_HISTORY_ORDER_ID));
                history.setComplaintId(rs.getLong(PARAM_HISTORY_COMPLAINT_ID));
                history.setProductId(rs.getLong(PARAM_HISTORY_PRODUCT_ID));
                allHistory.add(history);
            }
            return allHistory;
        }
    }

    private static final class GraphExtractor implements ResultSetExtractor<GraphDto> {

        private GraphDto graphDto;
        private LocalDate fromDate;
        private LocalDate toDate;

        private List<List<Long>> series = new ArrayList<>();
        private List<String> labels = new ArrayList<>();

        public GraphExtractor(GraphDto graphDto, LocalDate fromDate, LocalDate toDate) {
            this.graphDto = graphDto;
            this.fromDate = fromDate;
            this.toDate = toDate;

            long daysBetweenFromTo = DAYS.between(fromDate, toDate) + 1;
            for (int i = 0; i < graphDto.getElementIds().size(); i++) {
                series.add(new ArrayList<>(Collections.nCopies((int) daysBetweenFromTo, 0L)));
            }
        }

        @Override
        public GraphDto extractData(ResultSet rs) throws SQLException, DataAccessException {
//            graphDto.getElementIds(); // 2003, 2002

//            {
//               labels: [2017-05-13, 2017-05-14, 2017-05-15, 2017-05-16],
//               series: [
//2003                [0, 2, 1, 0],
//2002                [0, 1, 0, 0]
//               ]
//            }

            while (rs.next()) {
                LocalDate dataChange = rs.getTimestamp(PARAM_GRAPH_DATE_CHANGE).toLocalDateTime().toLocalDate();
                long elementId = rs.getLong(PARAM_GRAPH_ELEMENT_ID);
                long count = rs.getLong(PARAM_GRAPH_COUNT);

                checkDate(dataChange);

                int seriesIndex = graphDto.getElementIds().indexOf(elementId);
                series.get(seriesIndex).set(labels.size(), count);
            }
            if (!fromDate.equals(toDate)) {
                checkDate(toDate);
            }
            labels.add(fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            graphDto.setLabels(labels);
            graphDto.setSeries(series);
            return graphDto;
        }

        private void checkDate(LocalDate dataChange) {
            if (dataChange.equals(fromDate)) {
                return;
            } else {
                labels.add(fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                fromDate = fromDate.plusDays(1);
            }
            checkDate(dataChange);
        }
    }
}
