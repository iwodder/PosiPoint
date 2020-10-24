package com.wodder.model.users;

import java.util.*;

public abstract class User implements Cloneable {

    protected final String fName;
    protected final String lName;

    public enum Role {
        STAFF("Staff"),
        MANAGER("Manager");

        private final String name;

        Role(String s) {
            this.name = s;
        }

        protected String getRole() {
            return this.name;
        }
    }

    public static Iterator<String> getRoles() {
        List<String> names = new ArrayList<>();
        for (Role r : Role.values()) {
            names.add(r.name);
        }
        return names.iterator();
    }

    public User(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
    }

    public String userName() {
        return fName.toLowerCase().charAt(0) + lName.toLowerCase();
    }

    @Override
    public User clone() {
        return new UserFactory().createUser(this.fName, this.lName, this.roleName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!fName.equals(user.fName)) return false;
        return lName.equals(user.lName);
    }

    @Override
    public int hashCode() {
        int result = fName.hashCode();
        result = 31 * result + lName.hashCode();
        return result;
    }

    public abstract boolean isManager();

    public abstract String roleName();
}
