package com.wodder.model.users;

import java.util.*;

public enum Role {
	STAFF("Staff"),
	MANAGER("Manager");

	private final String name;

	Role(String s) {
		this.name = s;
	}

	protected String getRole() {
		return this.name;
	}

	public static Iterator<String> getRoles() {
		List<String> names = new ArrayList<>();
		for (Role r : Role.values()) {
			names.add(r.name);
		}
		return names.iterator();
	}
}
