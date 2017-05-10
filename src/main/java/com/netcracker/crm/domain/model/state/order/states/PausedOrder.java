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
    public History requestToResumeOrder() {
        History history = getOrderHistory(DESC_REQUEST_TO_RESUME_ORDER);
        order.setState(new RequestToResumeOrder(order));

        return history;
    }

    @Override
    public History requestToDisableOrder() {
        History history = getOrderHistory(DESC_REQUEST_TO_DISABLE_ORDER);
        order.setState(new RequestToDisableOrder(order));

        return history;
    }
}
