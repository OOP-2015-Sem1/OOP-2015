import java.util.ArrayList;
import java.util.UUID;

public class Wagon {

	//String st= UUID.randomUUID();
	
	public String genericWagon;
	 Wagon(String genWagon)
	{
		this.genericWagon = genWagon;
	}
		
	public String getWagon()
	{
		return this.genericWagon;
	}	
	
	String wagon[]={"passenger", "cargo"};
	
	public final int max = 100;
	public final int price = 100;
	public static int nrpass = 0;
	public static int nrCarrObj = 0;
	
	public void construction()
	{
	 CargoItem crg= new CargoItem();
	}
	
	for (int nr=1; nr=max; nr++)
	{
		nrpass++;
		nrCarrObj++;
	}
	
	int passeengerTax= nrpass*price;	
	profit = passengerTax * nrCarrObj;
	
	
}}
