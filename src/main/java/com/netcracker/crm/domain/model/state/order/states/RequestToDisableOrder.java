package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class RequestToDisableOrder extends OrderState {

    public RequestToDisableOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.REQUEST_TO_DISABLE);
    }

    @Override
    public History disableOrder() {
        order.setState(new DisabledOrder(order));
        History history = getOrderHistory("Order disabled.");

        return history;
    }
}
