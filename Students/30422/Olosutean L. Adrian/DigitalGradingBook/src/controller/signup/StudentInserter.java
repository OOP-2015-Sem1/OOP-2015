package controller.signup;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import org.jooq.Record;

import model.users.Credential;
import services.signup.StudentFieldsGetter;

public interface StudentInserter extends ClassIdGetter{
	
	
	public default void insertStudent(Credential cred,String clas) {
		db.create.insertInto(table("Student"))
		        .set(field("username"), cred.username)
		        .set(field("password"), cred.password)
		        .set(field("firstName"), cred.firstName)
		        .set(field("lastName"), cred.lastName)
		        .set(field("phoneNumber"), cred.phoneNumber)
		        .set(field("email"), cred.email)
		        .set(field("class_id"), getClassId(clas))
		        .execute();
		        
	};
	
}