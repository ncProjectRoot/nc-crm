package com.netcracker.crm.dto.mapper.impl;

import com.netcracker.crm.dao.ProductDao;
import com.netcracker.crm.dao.UserDao;
import com.netcracker.crm.domain.model.*;
import com.netcracker.crm.domain.real.RealOrder;
import com.netcracker.crm.dto.AutocompleteDto;
import com.netcracker.crm.dto.OrderDto;
import com.netcracker.crm.dto.OrderHistoryDto;
import com.netcracker.crm.dto.mapper.Mapper;
import com.netcracker.crm.dto.row.OrderRowDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Karpunets
 * @since 21.05.2017
 */
@Component
public class OrderMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ProductDao productDao;
    private final UserDao userDao;

    @Autowired
    public OrderMapper(ProductDao productDao, UserDao userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    public Mapper<OrderDto, RealOrder> dtoToModel() {
        return (dto, model) -> {
            Product product = productDao.findById(dto.getProductId());
            User customer = userDao.findById(dto.getCustomerId());

            model.setProduct(product);
            model.setCustomer(customer);
            model.setStatus(OrderStatus.NEW);
            model.setDate(LocalDateTime.now());

            StringBuilder preferredDataTime = new StringBuilder();
            if (!dto.getPreferredDate().isEmpty()) {
                preferredDataTime.append(dto.getPreferredDate());
                if (!dto.getPreferredTime().isEmpty()) {
                    preferredDataTime.append('T');
                    preferredDataTime.append(dto.getPreferredTime());
                }
            }
            if (preferredDataTime.length() != 0) {
                model.setPreferedDate(LocalDateTime.parse(preferredDataTime));
            }
        };
    }

    public Mapper<Order, OrderRowDto> modelToRowDto() {
        return (model, rowDto) -> {
            rowDto.setId(model.getId());
            rowDto.setStatus(model.getStatus().getName());
            rowDto.setProductId(model.getProduct().getId());
            rowDto.setProductTitle(model.getProduct().getTitle());
            rowDto.setProductStatus(model.getProduct().getStatus().getName());
            rowDto.setCustomer(model.getCustomer().getId());
            if (model.getCsr() != null) {
                rowDto.setCsr(model.getCsr().getId());
            }
            if (model.getDate() != null) {
                rowDto.setDateFinish(model.getDate().format(FORMATTER));
            }
            if (model.getPreferedDate() != null) {
                rowDto.setPreferredDate(model.getPreferedDate().format(FORMATTER));
            }
        };
    }

    public Mapper<Order, AutocompleteDto> modelToAutocomplete() {
        return (model, autocompleteDto) -> {
            autocompleteDto.setId(model.getId());
            autocompleteDto.setValue(model.getProduct().getTitle() + " " + model.getDate().toLocalDate());
        };
    }

    public Mapper<History, OrderHistoryDto> historyToOrderHistoryDto() {
        return (history, orderHistoryDto) -> {
            orderHistoryDto.setId(history.getId());
            orderHistoryDto.setDateChangeStatus(history.getDateChangeStatus().toString());
            orderHistoryDto.setDescChangeStatus(history.getDescChangeStatus());
            orderHistoryDto.setOldStatus(history.getNewStatus().getName());
        };
    }
}
