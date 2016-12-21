package db.manager;

import main.Application;

public class UserManager {
	public String getUserDetails(String user_id) {
		String outSql = new String();
		String sqlStatement = new String(
				"SELECT user_id, user_name FROM users_table WHERE  user_id = '" + user_id + "'");
		outSql = Application.databaseConnect.executeSqlStatementForStrings(sqlStatement);
		return outSql;
	};

	public void getAllUsers() {
		
	};

	public void newUser(String user_id, String user_name, String user_password) {
		
	};

	public void deleteUser(String user_id) {
		
	};

	public void updateUserName(String user_id, String newUser_name) {
		
	};

}
