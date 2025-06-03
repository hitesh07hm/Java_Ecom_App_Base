package com.ecom.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class GuestOptions {
	public static void viewProductItemAdmin() {

		Connection con = null;
		String viewAllProduct = "SELECT name, price from products";
		try {
			con = DbConnection.makeConnection();
			if (con != null) {
				try (Statement stmnt = con.createStatement()) {
					ResultSet result = stmnt.executeQuery(viewAllProduct);
					System.out.println("Name\t\t\tPrice");
					while (result.next()) {
						String name = result.getString("name");
						double price =result.getDouble("price");
						System.out.println(name + "\t\t" + price);
					}

					result.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					DbConnection.closeConnection(con);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbConnection.closeConnection(con);
		}

	}

	public static void notPurchaseItem() {
		System.out.println("You need to login before making any purchase ");
		System.out.println(".......");
		System.out.println("Redirectly you to login .... ");
		Authentication.authOps();
	}

	public static void guestOps() {
		System.out.println("Guest Operation");
		System.out.println("1. View product item");
		System.out.println("2. Not purchase item");
		System.out.println("3. Back to main menu");
		System.out.println("4. Exit");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your choice..");
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				GuestOptions.viewProductItemAdmin();
				break;
			case 2:
				GuestOptions.notPurchaseItem();
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
