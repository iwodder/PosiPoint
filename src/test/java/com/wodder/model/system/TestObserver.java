package com.wodder.model.system;

import com.wodder.gui.observers.*;
import com.wodder.model.users.*;

public class TestObserver implements UserObserver {

    public int logoutCnt;
    public int loginCnt;

    @Override
    public void userLoggedOut() {
        logoutCnt++;
    }

    @Override
    public void userLoggedIn(User user) {
        loginCnt++;
    }
}
