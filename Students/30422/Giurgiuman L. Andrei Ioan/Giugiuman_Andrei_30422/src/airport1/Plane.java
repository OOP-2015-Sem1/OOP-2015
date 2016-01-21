package airport1;
import java.util.*;

public class Plane implements Comparator {
private static int nrCompartments;
private String name;
private double profit;
ArrayList<Compartment> compartments = new ArrayList<Compartment>();

public Plane()
{computeProfit();
nrCompartments=compartments.size();
}

private double computeProfit()
{
	 return profit;
	
}
public double getProfit(){
	return profit;
}
private String getName (){
	return name;
}

@Override
public int compare(Object arg0, Object arg1) {
	// TODO Auto-generated method stub
	return 0;
}

}
 

//public  Comparator<Plane> namecomp = new Comparator<Plane>(){
//public int compare(Plane plane1, Plane plane2) {
	//S1=plane1.getName().toUpperCase();
 //S2=plane2.getName().toUpperCase();
	 //return s1.compareTo(s2);}

