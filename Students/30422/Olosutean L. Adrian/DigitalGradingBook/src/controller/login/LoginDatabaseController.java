package controller.login;


import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import org.jooq.Record;
import org.jooq.SelectField;

import controller.DatabaseController;
import model.users.Credential;

public interface LoginDatabaseController extends DatabaseController{
	
	default boolean isValidLogin(String table, String username,String introducedPassword){
		String sql = db.create.select(field("username"),field("password"))
				.from(table(table))
				.where(field("username").equal(inline(username)))
				.getSQL();
		
		try{
			Record row = db.create.fetchOne(sql);
			String correctPassword =(String)row.getValue("password");
			
			return introducedPassword.equals(correctPassword);
		}
		catch(Exception e){
			return false;
		}
	}
	
	public default boolean isValidStudent(Credential cred){
		return isValidLogin("Student", cred.username, cred.password);
	}
	
	public default boolean isValidTeacher(Credential cred){
		return isValidLogin("Teacher", cred.username, cred.password);
	}
	

}
