package com.wodder.model.order;

import com.wodder.gui.observers.*;

public class TestObserver implements OrderObserver {

    public int orderCreatedCtn;
    public int itemAdded;

    @Override
    public void orderCreated() {
        orderCreatedCtn++;
    }

    @Override
    public void itemAdded() {
        itemAdded++;
    }

    @Override
    public void orderDeleted() {

    }

    @Override
    public void activeOrderChanged() {

    }
}
