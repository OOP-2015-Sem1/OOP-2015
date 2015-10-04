package main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a,b;
		a=0;
		b=1;
		int c;
		int sum=0;
		final int MAX=4000000;
		
		while(a<MAX && b<MAX){
			c=a+b;
			a=b;
			b=c;
			System.out.println(c);
			
		}

	}

}
