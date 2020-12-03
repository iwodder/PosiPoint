package com.wodder.data.stores;

import com.wodder.model.users.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


class UserStoreTest extends DatabaseTest {

	private UserStore userStore;

	@BeforeEach
	void setUp() {
		userStore = new UserStore(connection());
	}

	@Test
	@DisplayName("Able to create a regular user")
	void createUser() {
		User u = new RegularUser("Andy","Bernard");
		assertTrue(userStore.createUser(u,"nardD0g"));
		assertEquals(u, userStore.getUser("Andy", "Bernard"));
	}

	@Test
	@DisplayName("Able to create a management user")
	void createMgmtUser() {
		User u = new ManagementUser("Robert","California");
		assertTrue(userStore.createUser(u,"ceo"));
		assertEquals(u, userStore.getUser("Robert", "California"));
	}

	@Test
	@DisplayName("Able to update a user")
	void updateUser() {
		User u = userStore.getUser("Michael", "Scott");
		assertNotNull(u);
		u.setFirstName("Toby");
		u.setLastName("Flenderson");
		assertTrue(userStore.updateUser(u));
		assertEquals(u, userStore.getUser("Toby", "Flenderson"));
		u.setFirstName("Michael");
		u.setLastName("Scott");
		assertTrue(userStore.updateUser(u));
	}

	@Test
	@DisplayName("Valid user first name and last name returns a user")
	void getUser() {
		User u = userStore.getUser("Michael", "Scott");
		assertEquals("mscott", u.userName());
		assertEquals(1L, u.getId());
		assertEquals("Michael", u.fName());
		assertEquals("Scott", u.lName());
	}

	@Test
	@DisplayName("Invalid user first name and last name returns nothing")
	void userTest() {
		assertNull(userStore.getUser("Andy", "Bernard"));
	}

	@Test
	@DisplayName("Able to successfully delete a user")
	void deleteUser() {
		User u = userStore.getUser("Michael", "Scott");
		assertTrue(userStore.deleteUser("mscott"));
		assertNull(userStore.getUser("Michael", "Scott"));
		assertTrue(userStore.createUser(u, "temp"));
	}

	@Test
	@DisplayName("Valid username returns a non-empty password")
	void getUserPassword() {
		assertNotEquals("", userStore.getUserPassword("mscott"));
	}

	@Test
	@DisplayName("Invalid username returns an empty string")
	void passwordTest() {
		assertEquals("", userStore.getUserPassword("abernard"));
	}
}
