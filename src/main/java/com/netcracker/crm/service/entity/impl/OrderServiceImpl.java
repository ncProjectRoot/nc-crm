package com.netcracker.crm.service.entity.impl;

import com.netcracker.crm.dao.HistoryDao;
import com.netcracker.crm.dao.OrderDao;
import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.domain.model.UserRole;
import com.netcracker.crm.domain.real.RealOrder;
import com.netcracker.crm.domain.request.OrderRowRequest;
import com.netcracker.crm.dto.*;
import com.netcracker.crm.dto.mapper.Mapper;
import com.netcracker.crm.dto.mapper.ModelMapper;
import com.netcracker.crm.dto.mapper.impl.OrderMapper;
import com.netcracker.crm.dto.row.OrderRowDto;
import com.netcracker.crm.scheduler.cacher.impl.OrderCache;
import com.netcracker.crm.security.UserDetailsImpl;
import com.netcracker.crm.service.entity.OrderLifecycleService;
import com.netcracker.crm.service.entity.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Melnyk_Dmytro
 * @version 1.0
 * @since 02.05.2017
 */

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final HistoryDao historyDao;
    private final OrderLifecycleService lifecycleService;
    private final OrderCache orderCache;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, HistoryDao historyDao, OrderLifecycleService lifecycleService,
                            OrderCache orderCache, OrderMapper orderMapper) {
        this.orderDao = orderDao;
        this.historyDao = historyDao;
        this.lifecycleService = lifecycleService;
        this.orderCache = orderCache;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public Order create(OrderDto orderDto) {
        Order order = ModelMapper.map(orderMapper.dtoToModel(), orderDto, RealOrder.class);
        lifecycleService.createOrder(order);
        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.findById(id);
    }

    @Transactional
    @Override
    public List<AutocompleteDto> getAutocompleteOrder(String pattern, User user) {
        List<Order> orders;
        if (user.isContactPerson()) {
            orders = orderDao.findOrgOrdersByIdOrTitle(pattern, user.getId());
        } else {
            orders = orderDao.findByIdOrTitleByCustomer(pattern, user.getId());
        }
        return ModelMapper.mapList(orderMapper.modelToAutocomplete(), orders, AutocompleteDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getOrdersRow(OrderRowRequest orderRowRequest) {
        Map<String, Object> response = new HashMap<>();
        Long length = orderDao.getOrderRowsCount(orderRowRequest);
        response.put("length", length);
        List<Order> orders = orderDao.findOrderRows(orderRowRequest);

        List<OrderRowDto> ordersDto = ModelMapper.mapList(orderMapper.modelToRowDto(), orders, OrderRowDto.class);
        response.put("rows", ordersDto);
        return response;
    }

    @Override
    public List<Order> findByCustomer(User customer) {
        return orderDao.findAllByCustomerId(customer.getId());
    }

    @Override
    public boolean hasCustomerProduct(Long productId, Long customerId) {
        return orderDao.hasCustomerProduct(productId, customerId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkAccessToOrder(User user, Long orderId) {
        UserRole role = user.getUserRole();
        if (role.equals(UserRole.ROLE_ADMIN) || role.equals(UserRole.ROLE_PMG) || role.equals(UserRole.ROLE_CSR)) {
            return true;
        } else if (role.equals(UserRole.ROLE_CUSTOMER)) {
            Long count = null;
            if (user.isContactPerson()) {
                count = orderDao.checkOwnershipOfContactPerson(orderId, user.getId());
            } else {
                count = orderDao.checkOwnershipOfCustomer(orderId, user.getId());
            }
            return count > 0;
        }
        return false;
    }

    @Override
    public List<OrderViewDto> getCsrActivateOrder(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            Long csrId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            return convertMapToList(orderCache.getActivateElement(csrId));
        }
        return null;
    }


    @Override
    public List<OrderViewDto> getCsrPauseOrder(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            Long csrId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            return convertMapToList(orderCache.getPauseElement(csrId));
        }
        return null;
    }

    @Override
    public List<OrderViewDto> getCsrResumeOrder(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            Long csrId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            return convertMapToList(orderCache.getResumeElement(csrId));
        }
        return null;
    }

    @Override
    public List<OrderViewDto> getCsrDisableOrder(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetailsImpl) {
            Long csrId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            return convertMapToList(orderCache.getDisableElement(csrId));
        }
        return null;
    }

    @Override
    public Integer getCsrOrderCount(Authentication authentication) {
        Object o = authentication.getPrincipal();
        if (o instanceof UserDetailsImpl) {
            Long csrId = ((UserDetailsImpl) o).getId();
            return orderCache.getCountElements(csrId);
        }
        return 0;
    }

    private List<OrderViewDto> convertMapToList(Map<Long, Order> map) {
        List<OrderViewDto> orders = new ArrayList<>();
        Mapper<Order, OrderViewDto> mapper = orderMapper.modelToOrderViewDto();
        for (Map.Entry<Long, Order> m : map.entrySet()) {
            orders.add(ModelMapper.map(mapper, m.getValue(), OrderViewDto.class));
        }
        return orders;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<OrderHistoryDto> getOrderHistory(Long id) {
        List<History> histories = historyDao.findAllByOrderId(id);
        Set<OrderHistoryDto> orders = new TreeSet<>(orderHistoryDtoComparator);
        orders.containsAll(ModelMapper.mapList(orderMapper.historyToOrderHistoryDto(), histories, OrderHistoryDto.class));
        return orders;
    }

    private Comparator<OrderHistoryDto> orderHistoryDtoComparator = (o1, o2) -> o1.getId() > o2.getId() ? -1 : 1;

    @Override
    public GraphDto getStatisticalGraph(GraphDto graphDto) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(graphDto.getDatePattern());
        try {
            LocalDate fromDate = LocalDate.parse(graphDto.getFromDate(), dtf);
            LocalDate toDate = LocalDate.parse(graphDto.getToDate(), dtf);
            if (toDate.compareTo(fromDate) < 0) {
                throw new Exception("toDate is less then fromDate");
            }
            return historyDao.findOrderHistoryBetweenDateChangeByProductIds(fromDate, toDate, graphDto);
        } catch (Exception e) {
            return null;
        }
    }

}
