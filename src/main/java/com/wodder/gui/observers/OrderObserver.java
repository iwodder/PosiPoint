package com.wodder.gui.observers;

public interface OrderObserver {

    void orderCreated();

    void itemAdded();

    void orderDeleted();

    void activeOrderChanged();
}
