package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class DisabledOrder extends OrderState {
    public DisabledOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.DISABLED);
    }
}
