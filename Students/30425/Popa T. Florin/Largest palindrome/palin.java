import java.util.Scanner;
public class palin {
	
	public static int dCount(int x){
		int j=0;
		while(x!=0){
			x=x/10;
			j++;
		}
		return j;
	}
	
	public static int isPalin(int x){
		
		int n=x,rev=0,rmd=0;
		while(x>0){
			rmd=x%10;
			rev=rev*10+rmd;
			x=x/10;
		}
		if(rev==n){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.print("Enter the number of digits from 2 to 4: ");
		int a=0,b=0,n=0,max=1;
		int x=input.nextInt();
		if(x==2) n=99;
		else if(x==3) n=999;
		else if(x==4) n=9999;
		for(int i=1;i<=n;i++){
			for(int j=1;j<=n;j++){
				if(isPalin(i*j)==1 && dCount(i)==x && dCount(j)==x && i*j>max){
					a=i;
					b=j;
					max=i*j;
				}
			}
		}
		System.out.println(a+ " AND " +b);
		System.out.print("Therefore, the largest palindrome made from the product of two ");
		System.out.println(x+"-digit numbers is: "+max);
		input.close();
			
		
	}
}
