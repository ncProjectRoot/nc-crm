package com.netcracker.crm.excel.converter;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.excel.OrderMapKey;

import java.time.LocalDateTime;
import java.util.*;

import static com.netcracker.crm.excel.OrderMapKey.*;

/**
 * Created by Pasha on 22.05.2017.
 */
public class OrderExcelConverter {

    public Map<OrderMapKey, List<?>> convertToMap(List<Order> orders) {
        List<LocalDateTime> preferedDates = new ArrayList<>();
        List<LocalDateTime> orderDates = new ArrayList<>();
        List<String> productTitles = new ArrayList<>();
        List<String> custFullNames = new ArrayList<>();
        List<OrderStatus> statuses = new ArrayList<>();
        List<Long> csrId = new ArrayList<>();

        for (Order order : orders) {
            custFullNames.add(getFullName(order.getCustomer()));
            productTitles.add(order.getProduct().getTitle());
            preferedDates.add(order.getPreferedDate());
            if (order.getCsr() == null) {
                csrId.add(null);
            } else {
                csrId.add(order.getCsr().getId());
            }
            orderDates.add(order.getDate());
            statuses.add(order.getStatus());
        }

        Map<OrderMapKey, List<?>> result = new LinkedHashMap<>();
        result.put(FULL_NAME, custFullNames);
        result.put(PRODUCT_TITLE, productTitles);
        result.put(DATE, orderDates);
        result.put(PREFERED_DATE, preferedDates);
        result.put(STATUS, statuses);
        result.put(CSR_ID, csrId);
        return result;
    }


    private String getFullName(User user) {
        return user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
    }
}
