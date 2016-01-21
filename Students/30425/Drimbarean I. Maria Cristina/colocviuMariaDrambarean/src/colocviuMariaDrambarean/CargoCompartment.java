package colocviuMariaDrambarean;

public class CargoCompartment extends Compartment{
	 public void computeProfit(){
		 CargoItem item=(CargoItem) getObjectList().getLast();
		 int profit=item.getProfit();
		 setProfit(getObjectList().size()*profit);
	 }
}
