package com.wodder.model.order;

import com.wodder.gui.observers.*;
import com.wodder.model.users.*;

import java.util.*;

public class TestUserSubject implements UserSubject {

    private List<UserObserver> observerList = new ArrayList<>();

    public void createTestUser() {
        User u = new RegularUser("Test", "User");
        observerList.forEach(o -> o.userLoggedIn(u));
    }

    @Override
    public void registerUserObserver(UserObserver o) {
        observerList.add(o);
    }

    @Override
    public void removeUserObserver(UserObserver o) {

    }
}
