package com.wodder.gui.observers;

public interface UserSubject {

    void registerUserObserver(UserObserver o);

    void removeUserObserver(UserObserver o);

}
