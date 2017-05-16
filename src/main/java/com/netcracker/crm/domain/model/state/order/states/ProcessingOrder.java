package com.netcracker.crm.domain.model.state.order.states;

import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

import static com.netcracker.crm.domain.model.OrderStatus.*;

/**
 * Created by bpogo on 5/9/2017.
 */
public class ProcessingOrder extends OrderState {

    public ProcessingOrder(Order order) {
        super(order);
        this.stateName = PROCESSING.getName();
        this.order.setStatus(PROCESSING);
    }

    @Override
    public History activateOrder() {
        History history = getOrderHistory(DESC_ORDER_ACTIVATED);
        order.setState(new ActiveOrder(order));

        return history;
    }
}
