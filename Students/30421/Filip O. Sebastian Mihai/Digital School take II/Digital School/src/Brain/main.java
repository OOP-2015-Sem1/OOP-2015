package Brain;

import java.sql.Connection;
import java.sql.DriverManager;

import GUI.LogIn;
import GUI.TypeOfLogIn;

public class main {

	
	public static void main(String[] args) {
		/*
		// TODO Auto-generated method stub
		try{
			// 1. get a connection to db
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool","root","");
			//2/ create statement 
			Statement myStmt = myConn.createStatement();
			//3. execute sql query
			String name;
			name = "hector";
			ResultSet myRs = myStmt.executeQuery("select teachername from teacher join teacherclassroom on (teacherid = idteacher) where classid = '8'");
			//4. process result set
			while (myRs.next())
				System.out.println(myRs.getString("teachername"));
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		*/
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
