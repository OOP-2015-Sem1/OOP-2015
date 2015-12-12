package account;

import java.io.*;
import java.util.*;

public class Account {
		
		private String accname;
	
		private List<Integer> scores = new ArrayList<Integer>();
		
		public Account(String accname){
			this.accname = accname;
			if (accountExists()){
				retrieveScores();
			}
		}
		
		public void addScore(int score){
			scores.add(score);
		}
		
		public void retrieveScores(){
			try
	        {
	            FileInputStream fis = new FileInputStream("Accounts/"+accname);
	            ObjectInputStream ois = new ObjectInputStream(fis);
	            scores = (List<Integer>) ois.readObject();
	            ois.close();
	            fis.close();
	         }catch(IOException ioe){
	             ioe.printStackTrace();
	             return;
			 }catch(ClassNotFoundException c){
				 System.out.println("Class not found");
				 c.printStackTrace();
				 return;
			 }
		}
		
		public void saveScores(){
			try{
		         FileOutputStream fos= new FileOutputStream("Accounts/"+accname);
		         ObjectOutputStream oos= new ObjectOutputStream(fos);
		         oos.writeObject(scores);
		         oos.close();
		         fos.close();
		       }catch(IOException ioe){
		            ioe.printStackTrace();
		        }
		}
		
		public int returnScore(){
			int fScore = 0;
			for (int score : scores){
				fScore += score;
			}
			return fScore;
		}
		
		public boolean accountExists(){
			return new File("Accounts/"+accname).exists();
		}
}
