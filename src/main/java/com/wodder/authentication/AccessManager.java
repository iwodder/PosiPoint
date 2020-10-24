package com.wodder.authentication;

import com.wodder.model.users.*;

import java.util.*;

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

    //Default implementation for getting credentials, uses the console
    Credentials getCredentials() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Getting credentials");
        System.out.print("Enter username >");
        String username = scanner.nextLine();
        System.out.print("Enter password >");
        char[] pass = scanner.nextLine().toCharArray();
        return new Credentials(username, pass);
    }
    public abstract boolean validCredentials(Credentials c);
    public abstract User createUser(Credentials c);
    public abstract void addUser(String userName, String password, String role);
}
