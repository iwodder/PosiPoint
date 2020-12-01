package com.wodder.authentication;

import com.wodder.model.users.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryAccessManagerTest {

	private AccessManager accessManager;
	private Credentials credentials;

	@BeforeEach
	void setup() {
		accessManager = new InMemory();
		credentials = new Credentials("mscott", new char[]{'p','a','s','s','w','0','r','d'});
	}

	@Test
	void validCredentials() {
		assertTrue(accessManager.validCredentials(credentials));
	}

	@Test
	void createUser() {
		User u = accessManager.createUser(credentials);
		assertNotNull(u);
		assertTrue(u instanceof RegularUser);
	}

	@Test
	void addUser() {
		accessManager.addUser("john", "smith", "catdog", "STAFF");
		User u = accessManager.login(new Credentials("jsmith", new char[]{'c','a','t','d','o','g'}));
		assertNotNull(u);
		assertTrue(u instanceof RegularUser);
	}
}
