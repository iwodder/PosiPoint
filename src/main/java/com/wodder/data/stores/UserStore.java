package com.wodder.data.stores;

import com.wodder.model.users.*;

import java.sql.*;

public class UserStore {

	private final Connection connection;

	public UserStore(Connection connection) {
		this.connection = connection;
	}

	public boolean createUser(User user, String password) {
		if (inactive(user)) {
			return reactivate(user);
		} else {
			return createNewUser(user, password);
		}
	}

	public boolean updateUser(User user) {
		try(PreparedStatement stmt = connection.prepareStatement("""
				UPDATE USERS SET FIRST_NAME=?, LAST_NAME=?, USER_NAME=?, ROLE_ID=? WHERE
				ID=?;""")) {
			stmt.setString(1, user.fName());
			stmt.setString(2, user.lName());
			stmt.setString(3, user.userName());
			stmt.setLong(4, getRoleId(user.roleName()));
			stmt.setLong(5, user.getId());
			return stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User getUser(String firstName, String lastName) {
		try (PreparedStatement stmt = connection.prepareStatement("""
				SELECT u.*, r.NAME as NAME FROM USERS u JOIN ROLES r ON u.ROLE_ID=r.ID
				WHERE u.FIRST_NAME=? AND u.LAST_NAME=? AND ACTIVE=1""",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				if (rs.last()) {
					int rowNum = rs.getRow();
					if (rowNum > 1) {
						throw new RuntimeException("Expected to find one result, found multiple");
					} else {
						User result = new UserFactory().createUser(
								rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"),
								rs.getString("NAME"));
						result.setId(rs.getLong("ID"));
						return result;
					}
				}
			} else {
				return null;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return null;
	}

	public String getUserPassword(String userName) {
		try (PreparedStatement statement = connection.prepareStatement("""
				SELECT PASSWORD FROM USERS WHERE USER_NAME = ? AND ACTIVE=1""",
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			statement.setString(1, userName);
			if (statement.execute()) {
				ResultSet rs = statement.getResultSet();
				if (rs.last()) {
					int row = rs.getRow();
					if (row > 1) {
						throw new SQLException();
					} else {
						return rs.getString("PASSWORD");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean deleteUser(String userName) {
		try (PreparedStatement stmt = connection.prepareStatement("""	
				UPDATE USERS SET ACTIVE=0 WHERE USER_NAME=?""")) {
			stmt.setString(1, userName);
			return stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean inactive(User user) {
		try (PreparedStatement stmt = connection.prepareStatement("""
				SELECT u.* FROM USERS u WHERE u.FIRST_NAME=? AND u.LAST_NAME=? AND u.USER_NAME=? AND ACTIVE=0;""",
			ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			stmt.setString(1, user.fName());
			stmt.setString(2, user.lName());
			stmt.setString(3, user.userName());
			if (stmt.execute()) {
				ResultSet rs = stmt.getResultSet();
				if (rs.last()) {
					int rowNum = rs.getRow();
					if (rowNum > 1) {
						throw new RuntimeException("Expected to find one result, found multiple");
					} else {
						return true;
					}
				}
			} else {
				return false;
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return false;
	}

	private boolean reactivate(User user) {
		try (PreparedStatement stmt = connection.prepareStatement("""	
				UPDATE USERS SET ACTIVE=1 WHERE USER_NAME=? AND FIRST_NAME=? AND LAST_NAME=?""")) {
			stmt.setString(1, user.userName());
			stmt.setString(2, user.fName());
			stmt.setString(3, user.lName());
			return stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean createNewUser(User user, String password) {
		try (PreparedStatement stmt = connection.prepareStatement("""
				INSERT INTO USERS (FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, ACTIVE, ROLE_ID) 
				VALUES (?, ?, ?, ?, ?, ?);""", Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.fName());
			stmt.setString(2, user.lName());
			stmt.setString(3, user.userName());
			stmt.setString(4, password);
			stmt.setInt(5, 1);
			stmt.setInt(6, getRoleId(user.roleName()));
			if (stmt.executeUpdate() == 1) {
				user.setId(getNewId(stmt));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private long getNewId(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		return rs.getLong(1);
	}

	private int getRoleId(String name) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("""
			SELECT ID FROM ROLES WHERE NAME = ?""");
		stmt.setString(1, name.toUpperCase());
		stmt.execute();
		ResultSet rs = stmt.getResultSet();
		if (rs.last()) {
			int row = rs.getRow();
			if (row > 1) {
				throw new SQLException();
			} else {
				return rs.getInt("ID");
			}
		}
		return -1;
	}
}
