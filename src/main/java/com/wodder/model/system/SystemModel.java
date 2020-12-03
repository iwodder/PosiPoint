package com.wodder.model.system;

import com.wodder.authentication.*;
import com.wodder.gui.observers.*;
import com.wodder.model.users.*;

import java.util.*;
import java.util.function.*;

public class SystemModel implements UserSubject {

    private User currentUser;
    private final AccessManager accessManager;
    private final ArrayList<UserObserver> userObservers;

    public SystemModel() {
        accessManager = new InMemory();
        userObservers = new ArrayList<>();
    }

    public String userName() {
        return currentUser.userName();
    }

    public String roleName() {
        return currentUser.roleName();
    }

    public boolean userIsManager() {
        return currentUser != null && currentUser.isManager();
    }

    public User currentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public void login(Credentials credentials) {
        this.currentUser = accessManager.login(credentials);
        if (this.currentUser != null) {
            updateUserObservers((obs) -> obs.userLoggedIn(currentUser));
        }
    }

    public void logoutUser() {
        currentUser = null;
        updateUserObservers(UserObserver::userLoggedOut);
    }

    public Iterator<String> getRoleNames() {
        return Role.getRoles();
    }

    public void addUser(String fName, String lName, String password, String role) {
        accessManager.addUser(fName, lName, password, role);
    }

    private void updateUserObservers(Consumer<UserObserver> action) {
        userObservers.forEach(action);
    }

    @Override
    public void registerUserObserver(UserObserver o) {
        userObservers.add(o);
    }

    @Override
    public void removeUserObserver(UserObserver o) {
        int idx = userObservers.indexOf(o);
        if (idx > -1) {
            userObservers.remove(idx);
        }
    }
}
