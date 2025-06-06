package com.ecom.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Authentication {
	public static String currentUser = null;

	public static void userRegister() {
		Connection connect = null;
		System.out.println("Enter details for registrations ");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your first Name :- ");
		String firstName = sc.nextLine();
		System.out.println("Enter your last Name :- ");
		String lastName = sc.nextLine();
		System.out.println("Enter your username  :- ");
		String username = sc.nextLine();
		if (username.isEmpty() || username.replace(" ", "").isEmpty()) {
			System.out.println("username cannot be empty");
			System.out.println("Register Failed retry....");
			return;
		}
		System.out.println("Enter your Password :- ");
		String password = sc.nextLine();
		if (password.isEmpty() || password.replace(" ", "").isEmpty()) {
			System.out.println("Password cannot be empty");
			System.out.println("Register Failed retry....");
			return;
		}
		System.out.println("Enter your city Name:- ");
		String city = sc.nextLine();
		System.out.println("Enter your mailId  :- ");
		String mailId = sc.nextLine();
		if (mailId.isEmpty() || mailId.replace(" ", "").isEmpty()) {
			System.out.println("mailId cannot be empty");
			System.out.println("Register Failed retry....");
			return;
		}
		System.out.println("Enter your mobileNumber  :- ");
		String mobileNumber = sc.nextLine();
		if (mobileNumber.isEmpty() || mobileNumber.replace(" ", "").isEmpty()) {
			System.out.println("mobileNumber cannot be empty");
			System.out.println("Register Failed retry....");
			return;
		}

		String checkUserExists = "SELECT username, mailId, mobileNumber " + "FROM user WHERE username = '" + username
				+ "' OR mailId = '" + mailId + "' OR mobileNumber = '" + mobileNumber + "'";

		String insertUserData = "INSERT INTO user (firstName,lastName,username, password, city, mailId, mobileNumber) VALUES('"
				+ firstName + "', '" + lastName + "', '" + username + "','" + password + "', '" + city + "', '" + mailId
				+ "', '" + mobileNumber + "' )";

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				try (Statement stmnt = connect.createStatement()) {
					ResultSet checkuser = stmnt.executeQuery(checkUserExists);

					if (checkuser.next()) {
						System.out.println("user already exists :- username or mailId or Mobile Number....");
						System.out.println(
								"if you have never registered with mailID or mobile Number than try more unique username ");
						return;
					}

					stmnt.execute(insertUserData);
					System.out.println("registration succefully, login with your username :- " + username);
					userLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}
	}

	public static void userLogin() {
		System.out.println("User Login, Please Enter details :- ");

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Username :- ");
		String username = sc.nextLine();
		System.out.println("Enter your Password :- ");
		String password = sc.nextLine();

		boolean isLoginVerified = verifyUser(username, password);
		if (isLoginVerified) {
			System.out.println("Login Sucessfull...");
		} else if (!isLoginVerified) {
			System.out.println("Login failed try again ...");
		}

	}

	public static boolean verifyUser(String username, String password) {
		Connection connect = null;

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				Statement stmt = connect.createStatement();
				String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password
						+ "'";
				ResultSet rs = stmt.executeQuery(query);

				if (rs.next()) {
					String role = rs.getString("roles");
					currentUser = username;
					System.out.println("Login successful! ");

					if ("User".equalsIgnoreCase(role)) {
						UserOptions.userOps();
					} else if ("Admin".equalsIgnoreCase(role)) {
						AdminOptions.adminOps();
					} else {
						System.out.println("Unknown role detected.");
					}
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}
		return false;
	}

	public static void authOps() {
		System.out.println("Authentication Operation");
		System.out.println("1. User Registration");
		System.out.println("2. User Login");
		System.out.println("3. Back to main menu");
		System.out.println("4. Exit");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your choice..");
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				userRegister();
				break;
			case 2:
				userLogin();
				break;
			case 3:
				Main.main(null);
				return;
			case 4:
				System.exit(0);
			default:
				System.out.println("Enter write choice");
			}
		}
	}
}
