package com.wodder.model.users;

public abstract class User {

    protected final String fName;
    protected final String lName;

    public User(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public abstract boolean isManager();

    public abstract String userName();
}
