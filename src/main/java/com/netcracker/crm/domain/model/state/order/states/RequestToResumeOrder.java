package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

import static com.netcracker.crm.domain.model.OrderStatus.*;

/**
 * Created by bpogo on 5/9/2017.
 */
public class RequestToResumeOrder extends OrderState {

    public RequestToResumeOrder(Order order) {
        super(order);
        this.stateName = REQUEST_TO_RESUME.getName().replace("_", " ");
        this.order.setStatus(REQUEST_TO_RESUME);
    }

    @Override
    public History resumeOrder() {
        History history = getOrderHistory(DESC_ORDER_RESUMED);
        order.setState(new ActiveOrder(order));

        return history;
    }
}
