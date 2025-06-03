package com.ecom.business;

public class Product {
	private int product_Id;
	private String description;
	private String name;
	private double price;
	private int quantity;

	public Product(int product_Id, String description, String name, double price, int quantity) {
		this.product_Id = product_Id;
		this.description = description;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public int getProduct_Id() {
		return product_Id;
	}

	public void setProduct_Id(int product_Id) {
		this.product_Id = product_Id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [product_Id=" + product_Id + ", description=" + description + ", name=" + name + ", price=" + price + "]";
	}

}
