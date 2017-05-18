package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class RequestToResumeOrder extends OrderState {

    public RequestToResumeOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.REQUEST_TO_RESUME);
    }

    @Override
    public History resumeOrder() {
        order.setState(new ActiveOrder(order));
        History history = getOrderHistory(DESC_ORDER_RESUMED);

        return history;
    }
}
