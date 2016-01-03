package services.signup;

import model.users.Credential;

public interface Authenthication_I {
	Credential getCredential();
	String getUserType();
	void setState(String state);
}
