package controller.signup;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import org.jooq.Record;

import controller.DatabaseController;


public interface UserFinder extends DatabaseController{
	public default boolean userIsFound(String username, String table){
		String sql = db.create.select(field("username"))
				.from(table(table))
				.where(field("username").equal(inline(username)))
				.getSQL();
		try{
			Record row = db.create.fetchOne(sql);
			if (row.getValue("username") != null) 
				return true;
			
			return false;
		}
		catch(Exception e){
			return false;
		}
	}
}
