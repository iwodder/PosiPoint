package com.wodder.authentication;

import com.wodder.model.users.*;
import org.apache.commons.lang3.tuple.*;

import java.util.*;

public class InMemory extends AccessManager {

    private Map<String, Pair<String, String>> db;

    public InMemory() {
        db = new HashMap<>();
        addUser("iwodder","passw0rd","staff");
        addUser("cnobrega","abc123","manager");

    }

    @Override
    public boolean validCredentials(Credentials credentials) {
        Pair<String, String> p = db.get(credentials.getUserName());
        if (p != null) {
            char[] dbP = p.getLeft().toCharArray();
            return Arrays.equals(dbP, credentials.getPassword());
        } else {
            return false;
        }
    }

    @Override
    public User createUser(Credentials credentials) {
        String role = db.get(credentials.getUserName()).getRight();
        switch (role.toLowerCase()){
            case "staff":
                return new RegularUser("First", "Last");
            case "manager":
                return new ManagementUser("Manager", "Manager");
            default:
                return null;
        }
    }

    @Override
    public void addUser(String userName, String password, String role) {
        if (!db.containsKey(userName)) {
            db.put(userName, Pair.of(password, role));
        }
    }
}
