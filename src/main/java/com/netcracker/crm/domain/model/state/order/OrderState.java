/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.netcracker.crm.domain.model.state.order;


import com.netcracker.crm.domain.model.History;
import com.netcracker.crm.domain.model.Order;
import com.netcracker.crm.domain.model.OrderStatus;
import com.netcracker.crm.domain.model.state.order.states.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;

/**
 * OrderState interface.
 */
public class OrderState {
    protected Order order;

    public OrderState(Order order) {
        this.order = order;
    }

    public History acceptOrder() {
        throw new NotImplementedException();
    }

    public History activateOrder() {
        throw new NotImplementedException();
    }

    public History pauseOrder() {
        throw new NotImplementedException();
    }

    public History resumeOrder() {
        throw new NotImplementedException();
    }

    public History cancelOrder() {
        throw new NotImplementedException();
    }

    protected Order getOrder() {
        return order;
    }

    protected History getOrderHistory() {
        History history = new History();

        history.setOrder(this.order);
        history.setDateChangeStatus(LocalDateTime.now());
        //TODO: implement logic to create history description
        history.setDescChangeStatus("Change Status");
        history.setOldStatus(order.getStatus());

        return history;
    }

    /**
     * Use this method to set appropriate OrderState
     * when order is restoring (for example: from DB)
     *
     * @param status - order status
     * @param order  - restored object
     * @return - appropriate order state
     */
    public static void setStateForOrder(OrderStatus status, Order order) {
        switch (status) {
            case NEW:
                order.setState(new NewOrder(order));
                break;
            case PROCESSING:
                order.setState(new ProcessingOrder(order));
                break;
            case ACTIVE:
                order.setState(new ActiveOrder(order));
                break;
            case PAUSED:
                order.setState(new PausedOrder(order));
                break;
            case DISABLED:
                order.setState(new DisabledOrder(order));
                break;
            default:
                break;
        }
    }
}
