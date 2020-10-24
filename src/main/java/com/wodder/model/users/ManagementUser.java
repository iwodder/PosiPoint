package com.wodder.model.users;

public class ManagementUser extends User {

    private Role role = Role.MANAGER;

    public ManagementUser(String fName, String lName) {
        super(fName, lName);
    }

    @Override
    public final boolean isManager() {
        return true;
    }

    @Override
    public String roleName() {
        return role.getRole();
    }
}
