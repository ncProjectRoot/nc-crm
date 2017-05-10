package com.netcracker.crm.domain.model.state.order.states;

import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class ProcessingOrder extends OrderState {
    public ProcessingOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.PROCESSING);
    }

    @Override
    public History activateOrder() {
        History history = getOrderHistory();
        order.setState(new ActiveOrder(order));

        return history;
    }
}
