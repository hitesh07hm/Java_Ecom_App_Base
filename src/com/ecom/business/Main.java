package com.ecom.business;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		System.out.println("Welcome to E-Commerce based application");
		System.out.println("1. User Operation");
		System.out.println("2. Guest Operations");
		System.out.println("3. Admin Operations ");
		System.out.println("4. Exit");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Enter your choice..");
			int choose = sc.nextInt();
			switch (choose) {
			case 1:
				Authentication.authOps();
				break;
			case 2:
				GuestOptions.guestOps();
				break;
			case 3:
				Authentication.authOps();
				break;
			case 4:
				System.exit(0);
			default:
				System.out.println("Enter write choice");
			}
		}

	}

}
