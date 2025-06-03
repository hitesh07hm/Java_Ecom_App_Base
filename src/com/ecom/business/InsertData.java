package com.ecom.business;

import java.sql.Connection;
import java.sql.Statement;

public class InsertData {

	public static void createTable(Connection connection) {
		String userData = "CREATE TABLE user ( username VARCHAR(16) PRIMARY KEY , firstName VARCHAR(16), lastName VARCHAR(16), password VARCHAR(20),city VARCHAR(16), mailId VARCHAR(30), mobileNumber VARCHAR(12), roles VARCHAR (10) DEFAULT 'User')";
		
		String productData ="CREATE TABLE products( product_Id INT PRIMARY KEY, name VARCHAR(20), description VARCHAR(120), price decimal(10,3), quantity INT ) ";
	   
		String cartTable = "CREATE TABLE cart(username VARCHAR(16), product_Id INT, quantity INT, PRIMARY KEY (username, product_Id), FOREIGN KEY (username) REFERENCES user(username), FOREIGN KEY (product_Id) REFERENCES products(product_Id) )";
		
		String orders = " CREATE TABLE orders( order_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(16), total_Amount DECIMAL(10,2), order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (username) REFERENCES user(username) ) "; 
		

		
		try(Statement stmnt= connection.createStatement()){ 
			stmnt.execute(userData); 
			stmnt.execute(productData); 
			stmnt.execute(cartTable);
			stmnt.execute(orders);
			System.out.println("Tables created successfully....."); 
			}catch (Exception e) { 
			e.printStackTrace(); 
			}
	}
	
	public static void insertDataToDb(Connection connection) {
		
		String insertProductData = "INSERT INTO `products` (product_Id , name , description , price, quantity) VALUES\n" 
				+ "( 1 , 'Desk Chair' , 'Ergonomic office chair' , '249.99' , '30' ),\n"
				+ "( 2 , 'Paper Shredder' , '8-sheet cross-cut shredder' , '89.99' , '45' ),\n"
				+ "( 3 , 'Printer' , 'Wireless color printer' , '199.99' , '35' ),\n"
				+ "( 4 , 'Filing Cabinet' , '3-drawer metal cabinet' , '129.99' , '25' ),\n"
				+ "( 5 , 'Desk Lamp' , 'LED adjustable lamp' , '39.99' , '85' ),\n"
				+ "( 6 , 'Stapler Set' , 'Professional stapler kit' , '19.99' , '120' ),\n"
				+ "( 7 , 'Whiteboard' , 'Magnetic dry erase board' , '49.99' , '60' ),\n"
				+ "( 8 , 'Document Scanner' , 'Portable sheet scanner' , '159.99' , '30' ),\n"
				+ "( 9 , 'Label Maker' , 'Bluetooth label printer' , '69.99' , '50' ),\n"
				+ "( 10 , 'Calculator' , 'Desktop business calculator' , '24.99' , '95' )";
		
		String insertAdminUser = "INSERT INTO `user` (username, firstName, lastName, password, city, mailId, mobileNumber, roles) VALUES\n" 
				+ "( 'admin', 'admin', 'manager', 'admin123', 'pune','hm1232@gmail.com','9216558803','admin' )";
		
		try(Statement stmnt = connection.createStatement()) {
			stmnt.execute(insertProductData);
			stmnt.execute(insertAdminUser);
			System.out.println("Data inserted successfully....");
			} catch (Exception e) {
			e.printStackTrace();
			}		
	}
	
	public static void loadData() {
		Connection con =null;
		try {
		con= DbConnection.makeConnection();
		if(con!=null) {
		System.out.println("Connection to database successful..");
		createTable(con);
		insertDataToDb(con);
		}

		} catch (Exception e) {
		e.printStackTrace();
		}finally {
			DbConnection.closeConnection(con);
		}
		}

		public static void main(String[] args) {
			InsertData.loadData();
		}
}
