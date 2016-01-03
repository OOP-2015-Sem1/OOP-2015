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

	private static Connection connection;
	private static Statement statement;

	public void executeSqlStatement(String statement){
		// TODO: create SQL statement and execute it
	}
	
	public DatabaseConnect() {

		setConnection(null);
		setStatement(null);

		try {
			// Register JDBC driver:
			Class.forName("com.mysql.jdbc.Driver");

			// Open connection:
			System.out.println("Connecting to database...");
			setConnection(DriverManager.getConnection(DB_URL, USER, PASSWORD));

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
			setStatement(getConnection().createStatement());
			String sql;
			sql = "SELECT user_id, user_name, user_password FROM users_table";

			ResultSet rs = getStatement().executeQuery(sql);

			while (rs.next()) {
				String user_id = rs.getString("user_id");
				String user_name = rs.getString("user_name");
				String user_password = rs.getString("user_password");

				System.out.print("user_id: " + user_id);
				System.out.print(", user_name: " + user_name);
				System.out.println(", user_password: " + user_password);

			}
			rs.close();
			getStatement().close();
			getConnection().close();

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		DatabaseConnect.connection = connection;
	}

	public static Statement getStatement() {
		return statement;
	}

	public static void setStatement(Statement statement) {
		DatabaseConnect.statement = statement;
	}
	
}
