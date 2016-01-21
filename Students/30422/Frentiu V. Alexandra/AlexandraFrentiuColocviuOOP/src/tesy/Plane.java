package tesy;

import java.util.*;
import java.util.LinkedHashSet;

public class Plane {
	String Pname,compartmentName;
	LinkedHashSet lh;
	Compartment c;
	double sum = 0;
public Plane()
{   int i,n;
	n =8;//foer now
	 lh = new LinkedHashSet();
	for(i=1;i<=n;i++)//n unique planes
		 c = new Compartment(compartmentName);
	lh.add(c);
}
public void setName(String name)
{
	this.Pname= name;
}
public void setCompartment(String compName)
{
	this.compartmentName = compName;//if cargo or person

}
public String getCompartment()
{
	return compartmentName;
	}
protected  double calculateProfit()
{
	Iterator it = lh.iterator();
	
	while(it.hasNext())//retrieve sum
	{
		//sum +=it.next().computeProfit;
	}
	return sum;
}
}





