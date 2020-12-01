package com.wodder.model.system;

import com.wodder.authentication.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SystemModelTest {

    private SystemModel model;
    private final Credentials userCreds = new Credentials("mscott", new char[]{'p','a','s','s','w','0','r','d'});
    private final Credentials managerCreds = new Credentials("jdoe", new char[]{'a','b','c','1','2','3'});

    @BeforeEach
    void setUp() {
        model = new SystemModel();
    }

    @Test
    @DisplayName("Valid credentials should log a user into the system")
    void loginTest() {
        model.login(userCreds);
        assertTrue(model.isLoggedIn());
    }

    @Test
    @DisplayName("Initial model shouldn't be logged in")
    void initialState() {
        assertFalse(model.isLoggedIn());
    }


    @Test
    @DisplayName("Regular user should return the proper role")
    void regularUserIsNotManager() {
        model.login(userCreds);
        assertFalse(model.userIsManager());
        assertEquals("Staff", model.roleName());
    }

    @Test
    @DisplayName("Manager user should return the proper role")
    void managerUserIsManager() {
        model.login(managerCreds);
        assertTrue(model.userIsManager());
        assertEquals("Manager", model.roleName());
    }

    @Test
    @DisplayName("Logging in should notify observers")
    void loginObserver() {
        TestObserver obs = registerTestObserver();
        model.login(userCreds);
        assertEquals(1, obs.loginCnt);
    }

    @Test
    @DisplayName("Logging out should notify observers")
    void logoutObserver() {
        TestObserver obs = registerTestObserver();
        model.login(userCreds);
        model.logoutUser();
        assertEquals(1, obs.logoutCnt);
    }

    private TestObserver registerTestObserver() {
        TestObserver obs = new TestObserver();
        model.registerUserObserver(obs);
        return obs;
    }

    @Test
    @DisplayName("Can add a staff user to the system")
    void addStaff() {
        model.addUser("John","Smith", "123", "Staff");
        model.login(new Credentials("jsmith", new char[]{'1','2','3'}));
        assertTrue(model.isLoggedIn());
        assertFalse(model.userIsManager());
    }

    @Test
    @DisplayName("Can add a manager user to the system")
    void addManager() {
        model.addUser("John","Smith", "123", "Manager");
        model.login(new Credentials("jsmith", new char[]{'1','2','3'}));
        assertTrue(model.isLoggedIn());
        assertTrue(model.userIsManager());
    }
}
