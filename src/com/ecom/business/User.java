package com.ecom.business;

public class User {
	private String firstName; 
	private String lastName; 
	private String username; 
	private String password; 
	private String city; 
	private String mailId; 
	private String mobileNumber;
	private String roles;
	
	public User(String firstName, String lastName, String username, String password, String city, String mailId, String mobileNumber, String roles) { 
	super(); 
	this.firstName = firstName; 
	this.lastName =lastName; 
	this.username= username; 
	this.password =password; 
	this.city= city; 
	this.mailId =mailId; 
	this.mobileNumber =mobileNumber; 
	this.roles =roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailid(String mailId) {
		this.mailId = mailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password="
				+ password + ", city=" + city + ", mailId=" + mailId + ", mobileNumber=" + mobileNumber + ", roles="
				+ roles + "]";
	}

	
	
	
	
	
}
