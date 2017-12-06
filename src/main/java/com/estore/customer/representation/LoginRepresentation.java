package com.estore.customer.representation;

public class LoginRepresentation {
	private Long userId;
	private String userName;
	private String name;
	private String password;
	private String rol;

	public LoginRepresentation() {
		
	}
	
	public LoginRepresentation(Long userId, String userName, String name, String password, String rol) {
		this.userId = userId;
		this.userName = userName;
		this.name = name;
		this.password = password;
		this.rol = rol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

}
