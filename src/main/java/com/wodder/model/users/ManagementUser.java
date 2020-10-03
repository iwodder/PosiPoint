package com.wodder.model.users;

public class ManagementUser extends User {

    public ManagementUser(String fName, String lName) {
        super(fName, lName);
    }

    @Override
    public final boolean isManager() {
        return false;
    }

    @Override
    public String userName() {
        return fName + " " + lName;
    }
}
