package asd;

public class Gamble {
	private int money;
	private int bet;
	public Gamble(){
		money=1000;
		bet=0;
	}
	public int getMoney(){
		return money;
	}
	public int getBet(){
		return bet;
	}
	public void setMoney(int x){
		money=x;
	}
	public void setBet(int x){
		bet=x;
	}
}
