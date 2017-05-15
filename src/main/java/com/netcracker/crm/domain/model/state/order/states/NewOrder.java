package com.netcracker.crm.domain.model.state.order.states;

import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

import static com.netcracker.crm.domain.model.OrderStatus.*;

/**
 * Created by bpogo on 5/9/2017.
 */
public class NewOrder extends OrderState {

    public NewOrder(Order order) {
        super(order);
        this.stateName = NEW.getName();
        this.order.setStatus(NEW);
    }

    @Override
    public History newOrder() {
        return getOrderHistory(DESC_ORDER_NEW);
    }

    @Override
    public History processOrder() {
        History history = getOrderHistory(DESC_ORDER_PROCESSING);
        order.setState(new ProcessingOrder(order));

        return history;
    }
}
