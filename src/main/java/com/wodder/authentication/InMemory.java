package com.wodder.authentication;

import com.wodder.model.users.*;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.tuple.*;
import org.mindrot.jbcrypt.*;

import java.util.*;

public class InMemory extends AccessManager {

    private Map<String, Pair<String, String>> db;

    public InMemory() {
        db = new HashMap<>();
        addUser("michael", "scott", "passw0rd", "staff");
        addUser("jane", "doe", "abc123", "manager");

    }

    @Override
    public boolean validCredentials(Credentials credentials) {
        Pair<String, String> p = db.get(credentials.getUserName());
        if (p != null) {
            return BCrypt.checkpw(new String(credentials.getPassword()), p.getLeft());
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
    public void addUser(String fName, String lName, String password, String role) {
        String userName = createUserName(fName, lName);
        if (!db.containsKey(userName)) {
            db.put(userName, Pair.of(hashPassword(password), role));
        }
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private String createUserName(String fname, String lname) {
        if (StringUtils.isNoneBlank(fname, lname)) {
            return fname.toLowerCase().charAt(0) + lname.toLowerCase();
        } else {
            throw new IllegalArgumentException("Unable to generate user name");
        }
    }
}
