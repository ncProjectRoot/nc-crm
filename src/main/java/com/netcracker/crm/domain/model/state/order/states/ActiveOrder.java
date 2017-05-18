package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

/**
 * Created by bpogo on 5/9/2017.
 */
public class ActiveOrder extends OrderState {

    public ActiveOrder(Order order) {
        super(order);
        this.order.setStatus(OrderStatus.ACTIVE);
    }

    @Override
    public History requestToPauseOrder() {
        order.setState(new RequestToPauseOrder(order));
        History history = getOrderHistory(DESC_REQUEST_TO_PAUSE_ORDER);

        return history;
    }

    @Override
    public History requestToDisableOrder() {
        order.setState(new RequestToDisableOrder(order));
        History history = getOrderHistory(DESC_REQUEST_TO_DISABLE_ORDER);

        return history;
    }
}
