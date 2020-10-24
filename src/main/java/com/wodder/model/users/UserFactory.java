package com.wodder.model.users;

public class UserFactory {

    public UserFactory() {

    }

    public User createUser(String fName, String lName, String role) {
        String type = role.toLowerCase();
        switch (type) {
            case "manager":
                return new ManagementUser(fName, lName);
            case "staff":
                return new RegularUser(fName, lName);
            default:
                throw new IllegalArgumentException(String.format("Unable to create role of type [ %s ]", role));
        }
    }
}
