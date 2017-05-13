package com.netcracker.crm.scheduler.cacher.impl;

import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.User;
import com.netcracker.crm.scheduler.cacher.Cache;
import com.netcracker.crm.scheduler.searcher.OrderSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pasha on 12.05.2017.
 */
@Component
public class OrderCache extends Cache<Order> {
    /**
     * Map for caching csr orders, which need activate after hour or less
     * Long - csr id
     * {@code Map<Long,Order>} map with csr orders
     */
    private Map<Long, Map<Long, Order>> csrOrderCache;
    private static final int INITIAL_CACHE_CAPACITY = 1000;

    @Autowired
    private OrderSearcher searcher;


    public OrderCache() {
        this.csrOrderCache = new ConcurrentHashMap<>(INITIAL_CACHE_CAPACITY);
    }


    public void fillCache() {
        for (Order order : searcher.searchForActivate()) {
            Map<Long, Order> orders;
            if (csrOrderCache.get(order.getCsr().getId()) == null
                    || csrOrderCache.get(order.getCsr().getId()).isEmpty()) {
                orders = new HashMap<>();
            } else {
                orders = csrOrderCache.get(order.getCsr().getId());
            }
            orders.put(order.getId(), order);
            csrOrderCache.put(order.getCsr().getId(), orders);
        }
    }

    @Override
    public void putElement(Long key, Order element) {
        Map<Long, Order> orders;
        if (csrOrderCache.get(key) != null) {
            orders = csrOrderCache.get(key);
        } else {
            orders = new HashMap<>();
        }
        orders.put(element.getId(), element);
        csrOrderCache.put(key, orders);
    }

    public Integer getCountElements(Long csrId) {
        checkByCsrId(csrId);
        return csrOrderCache.get(csrId).keySet().size();
    }

    public Map<Long, Order> getElement(Long csrId) {
        checkByCsrId(csrId);
        return csrOrderCache.get(csrId);
    }

    private void checkByCsrId(Long csrId) {
        csrOrderCache.computeIfAbsent(csrId, k -> convertListOrder(searcher.searchCsrOrder(csrId)));
    }

    public void removeOrderFromCache(Long csrId, Long orderId) {
        if(csrOrderCache.get(csrId) != null) {
            csrOrderCache.get(csrId).remove(orderId);
        }
    }

    @Override
    public void removeElement(Long key) {
        csrOrderCache.remove(key);
    }

    @Override
    public void removeElement(Long key, Order element) {
        removeOrderFromCache(key, element.getId());
    }


    /**
     * Take a list of online csr-s and take set of all cached csr-s
     * after remove from set all csr that is online, after clean
     * all that remained
     * */
    public void cleanCache() {
        List<User> csrOnline = searcher.getOnlineCsrs();
        Set<Long> csrCache = csrOrderCache.keySet();
        stayOfflineCsr(csrCache, csrOnline);
        cleanOfflineCsr(csrCache);
    }

    /**
     * @param cacheCsr  set that stay after removing
     * @param onlineCsr array online csrs that be removed from cache csr
     */
    private void stayOfflineCsr(Set<Long> cacheCsr, List<User> onlineCsr) {
        for (User user : onlineCsr) {
            cacheCsr.remove(user.getId());
        }
    }

    private void cleanOfflineCsr(Set<Long> cacheCsr) {
        for (Long id : cacheCsr) {
            System.out.println(id);
            csrOrderCache.remove(id);
        }
    }


    private Map<Long, Order> convertListOrder(List<Order> orders) {
        Map<Long, Order> orderMap = new HashMap<>();
        for (Order order : orders) {
            orderMap.put(order.getId(), order);
        }
        return orderMap;
    }
}
