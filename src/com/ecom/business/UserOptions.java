package com.ecom.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserOptions {

	public static String currentUser = Authentication.currentUser;
	private static double displayTotal = 0;
	



	public static void setDisplayTotal(double displayTotal) {
		UserOptions.displayTotal = displayTotal;
	}

	public static void byProduct() {
		Connection connect = null;
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter product ID to buy:");
		int product_Id = sc.nextInt();

		System.out.println("Enter quantity:");
		int quantity = sc.nextInt();

		String addToCart = "INSERT INTO cart (username, product_ID, quantity ) VALUES('" + currentUser + "', "
				+ product_Id + ", " + quantity + ")";

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				Statement stmnt = connect.createStatement();
				stmnt.execute(addToCart);
				System.out.println("Product added to cart.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}

	}
	
	public static void buyProduct() {
		Connection connect = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Product Id to Buy :- ");
		
		int product_Id = sc.nextInt();
		
		try {
			connect =DbConnection.makeConnection();
			if(connect != null) {
				String checkProduct = " SELECT product_Id,quantity "
						+ "FROM products WHERE product_Id = " +product_Id ;
				
				Statement stmnt = connect.createStatement();
				ResultSet result =stmnt.executeQuery(checkProduct);
				if(!result.next()) {
					System.out.println("Product Id is not valid");
					return;
				}
				
				int checkQuantity =result.getInt("quantity");
				
				if(checkQuantity <= 0) {
					System.out.println("Product is ouyt of stockl");
					return;
				}
				
				System.out.println("Enter Quantity youi want to buy");
				int quantity =sc.nextInt();
				
				if (checkQuantity < quantity) {
					System.out.println("we have only :- " +checkQuantity + "quantity available in the stock " );
					return;	
				}
				
				String addToCart = "INSERT INTO cart (username, product_Id, quantity ) Values"
						+ "('"+currentUser+"', "+product_Id+", "+quantity+")";
				stmnt.execute(addToCart);
				System.out.println("Product added to Cart");
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DbConnection.closeConnection(connect);
		}
		
		
	}

	public static void viewCart() {

		Connection connect = null;
		

		String viewCartQuery = "SELECT p.product_Id, p.name, p.price, c.quantity, (p.price * c.quantity) AS total "
				+ "FROM cart c JOIN products p ON c.product_Id = p.product_Id " + "WHERE c.username = '" + currentUser
				+ "'";

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				try (Statement stmnt = connect.createStatement()) {
					ResultSet result = stmnt.executeQuery(viewCartQuery);

					System.out.println("Name\t\t\tQty\t Price \t Total");
					while (result.next()) {
						String name = result.getString("name");
						double price = result.getDouble("price");
						int quantity = result.getInt("quantity");
						double total = result.getDouble("total");
						System.out.println(name + "\t\t" + quantity + "\t" + price + "\t" + total);
					}

					
					calculateDisplayTotal();
					System.out.println("----------------------------------------------");
					System.out.println("\t\t\t  Grand Total:- " + displayTotal);
					System.out.println("----------------------------------------------");

					result.close();
					
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

	public static void purchaseItem() {
		Connection connect = null;
		Scanner sc = new Scanner(System.in);
		
		calculateDisplayTotal();
		if (displayTotal == 0) {
			System.out.println("Your cart is empty.");
			return;
		}

		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				try {
					Statement stmnt = connect.createStatement();

					String insertOrder = "INSERT INTO orders (username, total_Amount) "
					        + "VALUES ('" + currentUser + "', " + displayTotal + ")";
					stmnt.executeUpdate(insertOrder);
					
					String updateProductQty = "UPDATE products p JOIN cart c ON p.product_Id = c.product_Id "
							+ "SET p.quantity = p.quantity - c.quantity WHERE c.username = '" + currentUser + "'";
					stmnt.executeUpdate(updateProductQty);

					
					String clearCart = "DELETE FROM cart WHERE username = '" + currentUser + "'";
					stmnt.executeUpdate(clearCart);
					
					displayTotal=0;


					System.out.println("Purchase successful. Order placed.");

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

	public static void userOps() {
		System.out.println("User Operation");

		System.out.println("1. User view Product item as Sorted Order");
		System.out.println("2. Buy Product");
		System.out.println("3. View Cart");
		System.out.println("4. Purchase the item");
		System.out.println("5. Back to main menu");
		System.out.println("6. Exit");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your choice..");
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				GuestOptions.viewProductItemAdmin();
				break;
			case 2:
				UserOptions.buyProduct();
				break;
			case 3:
				UserOptions.viewCart();
				break;
			case 4:
				UserOptions.purchaseItem();
				break;
			case 5:
				Main.main(null);
			    return;
			case 6:
				System.exit(0);
			default:
				System.out.println("Enter write choice");
			}
		}
	}
	
	
	public static void calculateDisplayTotal() {
		Connection connect = null;
		try {
			connect = DbConnection.makeConnection();
			if (connect != null) {
				Statement stmnt = connect.createStatement();
				String grandTotal = "SELECT SUM(p.price * c.quantity) AS grandTotal "
						+ "FROM cart c JOIN products p ON c.product_Id = p.product_Id "
						+ "WHERE c.username = '" + currentUser + "'";
				ResultSet rs = stmnt.executeQuery(grandTotal);
				if (rs.next()) {
					displayTotal = rs.getDouble("grandTotal");
				}
				rs.close();
				stmnt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(connect);
		}
	}
	
	


}
