package source;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<User> userList;
	private String filePerson;
	private ObjectOutputStream out ;
	private ObjectInputStream in;
	
	
	public Account(){
		this.userList= new ArrayList<>(); 
		this.filePerson = "BlackJK.paula";
		read();
	}
	public ArrayList<User> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<User> userList) {
		this.userList = userList;
	}
	
	//============================================ Serializare / Deserializare
	
	public void write( ArrayList < User > list){
		try {
			FileOutputStream fileOut =  new  FileOutputStream(filePerson);
            this.out = new ObjectOutputStream(fileOut);
            out.writeObject(list);
		    out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	@SuppressWarnings("unchecked")
	public void read(){
		try {
			FileInputStream filein =  new  FileInputStream(filePerson);
			this.in = new ObjectInputStream(filein);
			this.userList =    (ArrayList<User>) in.readObject();
		    in.close();
		} catch (IOException | ClassNotFoundException e) {
		}
	}
	
	public int getMoneyOfTheLoggedInUser(User user){
		for(User u : this.userList){
			if(  (user.getUserName().equals(u.getUserName()))  && (user.getUserPassword().equals(u.getUserPassword()))  )
							return u.getMoney();
		}
		return 0;
	}
}
