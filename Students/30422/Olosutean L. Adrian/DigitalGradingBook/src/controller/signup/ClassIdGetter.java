 package controller.signup;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import org.jooq.Record;

import controller.DatabaseController;

public interface ClassIdGetter extends DatabaseController{
	public default int getClassId(String clas) {
		String sql = db.create.select(field("class_id"))
				.from(table("Class"))
				.where(field("class_name").equal(inline(clas)))
				.getSQL();
		
			Record row = db.create.fetchOne(sql);
			int result = (int) row.getValue("class_id");
			return result;
	}
}
