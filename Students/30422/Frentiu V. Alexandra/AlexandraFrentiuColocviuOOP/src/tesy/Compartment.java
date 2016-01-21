package tesy;

import java.util.*;
import java.util.UUID;

public  class  Compartment {
String compartment;
final int MAX_NR_OF_PASSENGERS = 100;
public int countPass=0,countCargoItems=0;
final int ticketPrice = 90;
Passenger passanger;
CargoItem cargoItem;
ArrayList carriable;
static int totalProfit = 0;
int i,n =9;
public  Compartment(String compName)
{
	this.compartment = compName;
	String x =  UUID.randomUUID().toString();
	carriable = new ArrayList();
	for(i=1;i<=n;i++)
	{
		if(compName.compareTo("passanger")==0)//check if it is passanger or cargo item
		{
			if(countPass<MAX_NR_OF_PASSENGERS)
			{
			passanger = new Passenger("passenger");
			countPass++;
			}
		else System.out.println("out of space for passengers"); 
		}
		else 
			cargoItem = new CargoItem("cargoItem");
		countCargoItems++;
			
	}
	
	
	
	
}

public void computeProfit()
{
	Iterator i =  carriable.iterator();
	while(i.hasNext())
	{
		if(i.next() instanceof Passenger)
		totalProfit +=passanger.calculateProfit();
		//else if (i.next() instanceof CargoItem)
		//	totalProfit +=CargoItem.calculateProfit();
		
		//
	}
		
}

}
