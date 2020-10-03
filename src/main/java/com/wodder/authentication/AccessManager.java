package com.wodder.authentication;

import com.wodder.model.users.User;

import java.util.Arrays;
import java.util.Scanner;

public abstract class AccessManager {


    public final User login() {
        Credentials c = getCredentials();
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
    abstract boolean validCredentials(Credentials c);
    abstract User createUser(Credentials c);
    abstract void addUser(String userName, String password, String role);

    protected static final class Credentials {
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

        private void clear() {
            Arrays.fill(password, '*');
            this.userName = "";
        }
    }
}
