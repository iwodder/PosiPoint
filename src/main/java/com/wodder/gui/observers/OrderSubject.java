package com.wodder.gui.observers;

public interface OrderSubject {

    void registerOrderObs(OrderObserver orderObserver);

    void removeOrderObserver(OrderObserver orderObserver);

}
