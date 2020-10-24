package com.wodder.model.users;

public class RegularUser extends User {

    private Role role = Role.STAFF;

    public RegularUser(String fName, String lName) {
        super(fName, lName);
    }

    @Override
    public final boolean isManager() {
        return false;
    }

    @Override
    public String roleName() {
        return role.getRole();
    }
}
