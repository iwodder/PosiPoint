package com.wodder.model.users;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {
    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        userFactory = new UserFactory();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Staff", "staff"})
    void canCreateStaffUser(String role) {
        User u = userFactory.createUser("John", "Smith", role);
        assertTrue(u instanceof RegularUser);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Manager", "manager"})
    void canCreateManagementUser(String role) {
        User u = userFactory.createUser("John", "Smith", role);
        assertTrue(u instanceof ManagementUser);
    }
}