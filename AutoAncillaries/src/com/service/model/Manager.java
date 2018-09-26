package com.service.model;

public class Manager {
	private String managerId;
	private String password;
	private String name;
	private String role;

	public Manager() {
	}

	public Manager(String managerId, String password, String name, String role) {
		super();
		this.managerId = managerId;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Manager [managerId=" + managerId + ", password=" + password + ", name=" + name + ", role=" + role + "]";
	}

}
