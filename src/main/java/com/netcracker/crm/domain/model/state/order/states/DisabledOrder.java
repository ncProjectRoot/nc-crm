package com.netcracker.crm.domain.model.state.order.states;


import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.OrderState;

import static com.netcracker.crm.domain.model.OrderStatus.*;

/**
 * Created by bpogo on 5/9/2017.
 */
public class DisabledOrder extends OrderState {
    public DisabledOrder(Order order) {
        super(order);
        this.stateName = DISABLED.getName();
        this.order.setStatus(DISABLED);
    }
}
