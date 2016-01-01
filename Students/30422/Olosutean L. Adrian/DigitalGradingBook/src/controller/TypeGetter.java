package controller;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;



public interface TypeGetter extends DatabaseController{

	public default String[] getTypes(String column, String table){
    	String[] result = db.create.select()
    			.from(table(table))
    			.fetchArray(field(column),String.class);
    	return result;
    	
    }
	
}
