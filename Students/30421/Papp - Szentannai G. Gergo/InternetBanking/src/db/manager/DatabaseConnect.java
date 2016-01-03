package db.manager;

import java.sql.*;

public class DatabaseConnect {
	// TODO: connect to MySQL database (it is already implemented)

	// JDBC driver name and database URL:
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/internet_banking";

	// Database credentials:
	static final String USER = "sqlJavaUser";
	static final String PASSWORD = "1234";

	private Connection connection;
	private Statement statement;

	public DatabaseConnect() {

		connection = null;
		statement = null;

		try {
			// Register JDBC driver:
			Class.forName("com.mysql.jdbc.Driver");

			// Open connection:
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TEST:
		testConnection();
	}

	private void testConnection() {
		try {
			System.out.println("Creating statement...");
			statement = connection.createStatement();
			String sql;
			sql = "SELECT user_id, user_name, user_password FROM users_table";

			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String user_id = rs.getString("user_id");
				String user_name = rs.getString("user_name");
				String user_password = rs.getString("user_password");

				System.out.print("user_id: " + user_id);
				System.out.print(", user_name: " + user_name);
				System.out.println(", user_password: " + user_password);

			}
			rs.close();
			statement.close();
			connection.close();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
