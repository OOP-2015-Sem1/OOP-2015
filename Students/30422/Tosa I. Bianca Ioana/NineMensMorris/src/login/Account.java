package login;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	public String name;
	public int score;


	public Account() {
	}

	public Account(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}


}
