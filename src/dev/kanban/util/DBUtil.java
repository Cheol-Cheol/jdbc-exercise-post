package dev.kanban.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String DB_NAME = "testdb";
	private static final String USER = "root";
	private static final String PASSWORD = "1234";
	private static Connection conn;
	
	public static Connection getConnection() {
		try {
			conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASSWORD);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
