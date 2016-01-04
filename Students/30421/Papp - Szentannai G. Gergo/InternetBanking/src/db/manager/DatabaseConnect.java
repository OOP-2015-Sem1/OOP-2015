package db.manager;

import java.sql.*;

public class DatabaseConnect {

	// JDBC driver name and database URL:
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/internet_banking";

	// Database credentials:
	static final String USER = "sqlJavaUser";
	static final String PASSWORD = "1234";

	// Global variables used for connection and the queries:
	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet rs = null;

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
		// executeSqlStatement("SELECT user_id, user_name, user_password FROM
		// users_table");
	}

	/**
	 * It is only implemented for accessing String type columns.
	 * 
	 * @param statement
	 * @return
	 */
	public String executeSqlStatementForStrings(String statement) {
		String outString = new String("");
		try {
			System.out.println("Creating statement...");
			setStatement(getConnection().createStatement());

			setRs(getStatement().executeQuery(statement));
			ResultSetMetaData resultSetMetaData = getRs().getMetaData();
			int columCount = resultSetMetaData.getColumnCount();

			while (getRs().next()) {
				for (int columnIndex = 1; columnIndex <= columCount; columnIndex++) {
					outString = outString + getRs().getString(columnIndex) + "; ";
				}
				outString = outString + "\n";

			}
			System.out.println(outString);

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return (outString);
	}

	public ResultSet executeSqlStatement(String statement) {

		try {
			System.out.println("Creating statement...");
			setStatement(getConnection().createStatement());
			System.out.println("Executing query...");
			System.out.println("STATEMENT: "+statement);
			setRs(getStatement().executeQuery(statement));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getRs();

	}

	private void testConnection() {
		try {
			System.out.println("Creating statement...");
			setStatement(getConnection().createStatement());
			String sql;
			sql = "SELECT user_id, user_name, user_password FROM users_table";

			setRs(getStatement().executeQuery(sql));

			while (getRs().next()) {
				String user_id = getRs().getString("user_id");
				String user_name = getRs().getString("user_name");
				String user_password = getRs().getString("user_password");

				System.out.print("user_id: " + user_id);
				System.out.print(", user_name: " + user_name);
				System.out.println(", user_password: " + user_password);

			}

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	public void closeDatabase() {

		try {
			getStatement().close();
			getConnection().close();
			getRs().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Getters and setters:

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

	public static ResultSet getRs() {
		return rs;
	}

	public static void setRs(ResultSet rs) {
		DatabaseConnect.rs = rs;
	}

}
