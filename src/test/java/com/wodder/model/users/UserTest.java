package com.wodder.model.users;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("Regular user has the appropriate role")
    void userRoleName() {
        User u = new RegularUser("John", "Smith");
        assertEquals(User.Role.STAFF.getRole(), u.roleName());
    }

    @Test
    @DisplayName("Management user has the appropriate role")
    void managerRoleName() {
        User u = new ManagementUser("John", "Smith");
        assertEquals(User.Role.MANAGER.getRole(), u.roleName());
    }

    @Test
    @DisplayName("Regular user can get user name")
    void correctRegularUserName() {
        User u = new RegularUser("John", "Smith");
        assertEquals("jsmith", u.userName());
    }

    @Test
    @DisplayName("Manager user can get user name")
    void correctManagerUserName() {
        User u = new ManagementUser("John", "Smith");
        assertEquals("jsmith", u.userName());
    }

}