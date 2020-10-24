package com.wodder.authentication;

import java.util.*;

public class Credentials {
    private String userName;
    private final char[] password;

    public Credentials(String userName, char[] password) {
        if (userName == null || password == null) {
            throw new IllegalArgumentException("Cannot create credentials without proper parameters");
        }
        this.userName = userName;
        this.password = password;
    }

    public final String getUserName() {
        return userName;
    }

    public final char[] getPassword() {
        return password;
    }

    void clear() {
        Arrays.fill(password, '*');
        this.userName = "";
    }
}
