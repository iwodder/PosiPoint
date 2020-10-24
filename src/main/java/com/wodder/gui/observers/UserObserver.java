package com.wodder.gui.observers;

import com.wodder.model.users.*;

public interface UserObserver {

    void userLoggedOut();

    void userLoggedIn(User user);
}
