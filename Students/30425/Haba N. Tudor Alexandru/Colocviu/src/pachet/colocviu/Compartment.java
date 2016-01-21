package pachet.colocviu;
import java.util.UUID;

public class Compartment extends CargoItem{
	final String x=UUID.randomUUID().toString();
	final int passnr=100;
	int profit;
	String type;
	int peoplenr;
	int howmany;
	
	public Compartment(){
		peoplenr=(int) Math.random();
		if (type == "passenger")
			profit=100*peoplenr; 
		else if (type == "cargo")  
			profit=profit*howmany;
		
	}

}
