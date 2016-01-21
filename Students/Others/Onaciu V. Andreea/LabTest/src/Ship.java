import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
public class Ship {
   String name;
   Set<Compartment> compartments=new LinkedHashSet();
   String type;
   int profit;
   
   public Ship(String type){
	   this.name=UUID.randomUUID().toString();
	   this.type=type;
	   this.profit=0;
   }
   
   public void addCompartments(){
	   if (type.equals("Cargo")) compartments.add(new CargoCompartment());
	   else compartments.add(new PassengerCompartment());
   }
   
   public int calculateProfit(){
	   int sum=0;
	   for(Compartment i:compartments){
		   sum+=i.calculateProfit();
	   }
	   this.profit=profit;
	   return profit;
   }
   
   
}
