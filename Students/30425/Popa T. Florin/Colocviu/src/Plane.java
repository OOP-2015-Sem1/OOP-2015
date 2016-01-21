import java.util.Random;
import java.awt.List;
import java.util.Collections;

public class Plane extends Airport {

	Random r = new Random();
	int number = r.nextInt(10);
	private String name = "Jet";
	private int profit;
	List l = new List();
	 
	
	public void profit(int compartment[]){
		
		for(int i = 0; i<compartment.length;i++){
			profit+=compartment[i];
		
		}
		
	}
	
}
