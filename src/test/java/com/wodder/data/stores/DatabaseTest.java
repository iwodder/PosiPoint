package com.wodder.data.stores;

import org.junit.jupiter.api.*;

import java.net.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

	private static Connection conn;

	@BeforeAll
	static void setup() {
		try {
			URL createSql = DatabaseTest.class.getResource("/sqlscripts/create.sql");
			URL populateSql = DatabaseTest.class.getResource("/sqlscripts/populate.sql");
			String create = createSql.getPath();
			String populate = populateSql.getPath();
			DriverManager.getConnection(String.format("jdbc:h2:mem:PosiPoint;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM '%s'\\;RUNSCRIPT FROM '%s'", create, populate));
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Couldn't open in-memory connection");
		}
	}

	protected Connection connection() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:h2:mem:PosiPoint");
			assertNotNull(conn);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unable to get a second connection");
			return null;
		}
	}
}
