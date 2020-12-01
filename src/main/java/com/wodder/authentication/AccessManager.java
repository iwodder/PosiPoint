package com.wodder.authentication;

import com.wodder.model.users.*;

public abstract class AccessManager {

    public final User login(Credentials c) {
        if (validCredentials(c)) {
            User user = createUser(c);
            c.clear();
            return user;
        } else {
            return null;
        }
    }

    public abstract boolean validCredentials(Credentials c);
    public abstract User createUser(Credentials c);
    public abstract void addUser(String fName, String lName, String password, String role);
}
