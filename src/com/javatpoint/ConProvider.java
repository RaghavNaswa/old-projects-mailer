package com.javatpoint;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class ConProvider {

	public static Connection getConnection() {
		java.sql.Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mailer", "root", "9717303302g");
		} catch (Exception e) {
			System.out.println(e);
		}
		return (Connection) con;
	}
}
