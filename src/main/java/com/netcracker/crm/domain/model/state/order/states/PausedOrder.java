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
        order.setState(new RequestToResumeOrder(order));
        History history = getOrderHistory(DESC_REQUEST_TO_RESUME_ORDER);

        return history;
    }

    @Override
    public History requestToDisableOrder() {
        order.setState(new RequestToDisableOrder(order));
        History history = getOrderHistory(DESC_REQUEST_TO_DISABLE_ORDER);

        return history;
    }
}
