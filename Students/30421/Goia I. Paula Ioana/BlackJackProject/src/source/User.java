package source;

import java.io.Serializable;

public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String userPassword;
	private int money;
	
	public User(String userName , String userPassword){
		this.userName = userName;
		this.userPassword = userPassword; 
		this.money = 0;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
