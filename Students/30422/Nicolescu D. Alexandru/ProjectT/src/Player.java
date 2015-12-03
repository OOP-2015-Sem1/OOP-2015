import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private int score;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
