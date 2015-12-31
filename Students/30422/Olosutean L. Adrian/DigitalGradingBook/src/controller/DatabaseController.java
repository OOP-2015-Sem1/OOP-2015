package controller;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import java.sql.SQLException;
import java.util.ArrayList;

import org.jooq.Record;
import org.jooq.Result;

import model.utils.StudentClass;
import model.utils.LookupTable;
import model.utils.Specialization;
import model.utils.Subject;

public interface DatabaseController {
	public static Database db = setDatabase();
	
	static Database setDatabase(){
		Database db = null;
		try{
			db = new Database("root", "", "jdbc:mysql://localhost:3306/School");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return db;
	}
	
	
}
