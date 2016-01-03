package controller;

import java.sql.*;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class Database {
	
	private String userName;
    private String password;
    private String url ;
    public Connection connection;
    public DSLContext create;
	
	public Database(String userName,String password,String url) throws SQLException{
		setDatabaseParameters(userName, password, url);
		connect();
	}
	
	public void setDatabaseParameters(String userName,String password,String url){
		this.userName = userName;
	    this.password = password;
	    this.url = url;
	}
	
	private void connect() throws SQLException {
		connection = DriverManager.getConnection(url, userName, password);
		create = DSL.using(connection, SQLDialect.MYSQL);
	}
	
	
}