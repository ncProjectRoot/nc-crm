package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class RequestToPauseOrder extends OrderState {

    public RequestToPauseOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.REQUEST_TO_PAUSE);
    }

    @Override
    public History pauseOrder() {
        History history = getOrderHistory(DESC_ORDER_PAUSED);
        order.setState(new PausedOrder(order));

        return history;
    }
}
