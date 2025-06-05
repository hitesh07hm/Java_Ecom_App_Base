package com.ecom.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class AdminOptions {

	public static String currentUser = Authentication.currentUser;
	private static double displayTotal = 0;

	public static void addProduct() {
		Connection connect = null;
		System.out.println("Create a New Product:- ");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your product_Id :- ");
		int product_Id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter your name :- ");
		String name = sc.nextLine();
		System.out.println("Enter your description :- ");
		String description = sc.nextLine();
		System.out.println("Enter your price  :- ");
		double price = sc.nextDouble();
		System.out.println("Enter your quantity :- ");
		int quantity = sc.nextInt();

		String insertProduct = "INSERT INTO products (product_Id, name, description, price, quantity) VALUES("
				+ product_Id + ", '" + name + "', '" + description + "', " + price + ", " + quantity + ")";

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				try (Statement stmnt = connect.createStatement()) {
					stmnt.execute(insertProduct);
					System.out.println("--------------------------------------------");
					System.out.println(name + "Product added success");
					System.out.println("continue Adding other product");
					adminOps();
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

	public static void calculateBill(String username) {
		Connection connect = null;

		String viewCartQuery = "SELECT p.name, p.price, c.quantity, (p.price * c.quantity) AS total FROM cart c "
				+ "JOIN products p ON c.product_id = p.product_id " + "WHERE c.username = '" + username + "'";

		String grandTotal = "SELECT SUM(p.price * c.quantity) AS grandTotal "
				+ "FROM cart c JOIN products p ON c.product_Id = p.product_Id " + "WHERE c.username = '" + username
				+ "'";

		try {
			connect = DbConnection.makeConnection();
			Statement stmnt = connect.createStatement();
			ResultSet result = stmnt.executeQuery(viewCartQuery);

			System.out.println("\nBill for user: " + username);

			System.out.println("Name\t\t\tQty\tPrice\tTotal");
			while (result.next()) {
				String name = result.getString("name");
				double price = result.getDouble("price");
				int quantity = result.getInt("quantity");
				double total = result.getDouble("total");
				System.out.println(name + "\t\t" + quantity + "\t" + price + "\t" + total);
			}

			ResultSet massTotal = stmnt.executeQuery(grandTotal);
			if (massTotal.next()) {
				double gTotal = massTotal.getDouble("grandTotal");
				displayTotal = gTotal;
				System.out.println("----------------------------------------------");
				System.out.println("\t\t\t  Grand Total:- " + gTotal);
				System.out.println("----------------------------------------------");
			}

			System.out.println("Press 1 to Display Total Amount");
			System.out.println("Press 2 to Go Back to Admin Menu");

			Scanner sc = new Scanner(System.in);
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				displayAmount();
				break;
			case 2:
				adminOps();
				break;
			default:
				System.out.println("Invalid choice, returning to menu.");
				adminOps();
				break;
			}

			result.close();
			massTotal.close();
			stmnt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}
	}

	public static void displayAmount() {
		
		if(displayTotal != 0) {
			System.out.println("\n Total Bill for user: ");
		    System.out.println("----------------------------------------------");
		    System.out.println("\t\t  Grand Total:- " + displayTotal);
		    System.out.println("----------------------------------------------");
		    
		    displayTotal = 0;
		    
		    
			System.out.println("Press 1 to Go Back to Admin Menu");

			Scanner sc = new Scanner(System.in);
			int choose = sc.nextInt();
			switch (choose) {
			
			case 1:
				adminOps();
				break;
			default:
				System.out.println("Invalid choice, returning to menu.");
				adminOps();
				break;
			}
		}else {
			System.out.println("First calculate Bill for user...");
		}
		 

	}

	public static void quantityCheck() {
		Connection connect = null;
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter product ID :");
		int product_Id = sc.nextInt();

		String showQty = "SELECT product_Id,name,quantity from products WHERE product_Id = " + product_Id + " ";

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				try (Statement stmnt = connect.createStatement()) {
					ResultSet result = stmnt.executeQuery(showQty);

					System.out.println("product_Id\t\tname\t\t\tquantity");

					while (result.next()) {
						int product_id = result.getInt("product_Id");
						String name = result.getString("name");
						int quantity = result.getInt("quantity");
						System.out.println(product_id + "\t" + name + "\t\t" + quantity);
					}
					result.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}

	}

	public static void checkRegisterUser() {
		Connection connect = null;
		Scanner sc = new Scanner(System.in);

		String showAllUser = "SELECT username, firstName, lastName, password, city, mailId, mobileNumber, roles from user ";

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				try (Statement stmnt = connect.createStatement()) {
					ResultSet result = stmnt.executeQuery(showAllUser);

					System.out.println(
							"username\t\tfirstName\t\t\tlastName\t\t\tcity\t\tmailId\t\t\tmobileNumber\t\troles");

					while (result.next()) {
						String username = result.getString("username");
						String firstName = result.getString("firstName");
						String lastName = result.getString("lastName");
						String city = result.getString("city");
						String mailId = result.getString("mailId");
						String mobileNumber = result.getString("mobileNumber");
						String roles = result.getString("roles");
						System.out.println(username + "\t" + firstName + "\t\t" + lastName + "\t\t" + city + "\t"
								+ mailId + "\t" + mobileNumber + "\t" + roles);
					}
					result.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}
	}

	public static void userHistory(String userName) {
		Connection connect = null;

		String viewUserHistory = "SELECT order_id,username,total_Amount,order_date FROM orders o WHERE o.username = '" + userName + "' ";
		
		try {
			connect = DbConnection.makeConnection();
			if(connect != null) {
				try(Statement stmnt = connect.createStatement()){
					ResultSet result = stmnt.executeQuery(viewUserHistory);
					
					System.out.println("History for user :- " +userName );
					
					System.out.println("Order_id\tusername\ttotal_Amount\torder_date");
					while (result.next()) {
						int order_Id = result.getInt("order_id");
						String username = result.getString("username");
						double totalAmount = result.getDouble("total_Amount");
						Timestamp orderDate = result.getTimestamp("order_date");
						System.out.println(order_Id + "\t\t" + username + "\t\t" + totalAmount + "\t\t" + orderDate);
					}
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbConnection.closeConnection(connect);
		}
		
	}

	public static void adminOps() {
		System.out.println("Admin Operation");
		System.out.println("1. Add product item");
		System.out.println("2. Calculate Bill");
		System.out.println("3. Display amount to End User");
		System.out.println("4. Check Quantity");
		System.out.println("5. Check registered user");
		System.out.println("6. Check the particular user history");
		System.out.println("7. View all Products");
		System.out.println("8. Back to main menu");
		System.out.println("9. Exit");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your choice..");
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				AdminOptions.addProduct();
				break;
			case 2:
				sc.nextLine();
				System.out.print("Enter username to calculate bill: ");
				String username = sc.nextLine();
				AdminOptions.calculateBill(username);
				break;
			case 3:
				if (displayTotal != 0) {
					sc.nextLine();
					System.out.print("Total Billing Amount :-  ");
					displayAmount();
				} else {
					System.out.println("calculate bill for user First");
					adminOps();
				}
				break;
			case 4:
				AdminOptions.quantityCheck();
				break;
			case 5:
				AdminOptions.checkRegisterUser();
				break;
			case 6:
				sc.nextLine();
				System.out.print("Enter username to check user history: ");
				String userName = sc.nextLine();
				AdminOptions.userHistory(userName);
				break;
			case 7:
				GuestOptions.viewProductItemAdmin();
				break;
			case 8:
				Main.main(null);
				return;
			case 9:
				System.exit(0);
			default:
				System.out.println("Enter write choice");
			}
		}
	}

}
