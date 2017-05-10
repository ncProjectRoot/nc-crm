package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class PausedOrder extends OrderState {
    public PausedOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.PAUSED);
    }

    @Override
    public History resumeOrder() {
        History history = getOrderHistory();
        order.setState(new ActiveOrder(order));

        return history;
    }

    @Override
    public History cancelOrder() {
        History history = getOrderHistory();
        order.setState(new DisabledOrder(order));

        return history;
    }
}
