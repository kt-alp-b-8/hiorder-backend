package com.example.event;

import java.util.List;


public class OrdersReadyForPaymentEvent {
    private List<Long> orderIds;

    public OrdersReadyForPaymentEvent(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }
}
