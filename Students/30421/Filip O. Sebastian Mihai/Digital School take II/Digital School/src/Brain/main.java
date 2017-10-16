package Brain;

import java.sql.Connection;
import java.sql.DriverManager;

import GUI.LogIn;
import GUI.TypeOfLogIn;

public class Main {

	
	public static void main(String[] args) {
		new TypeOfLogIn();
	}
	public static Connection getConnection()
    {
        Connection myConn=null;
        try
        {
        Class.forName("com.mysql.jdbc.Driver");

		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return myConn;        
    }
}
