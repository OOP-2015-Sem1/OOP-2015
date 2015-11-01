package javasmmr.zoowsome.models.animals;

//import javasmmr.zoowsome.models.animals.Aquatic.waterType;

public class Newt extends Aquatic {

	public Newt(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Newt");
		setNrOfLegs(4);
		setAvgSwimDepth(20);
		
		//not sure on this ... 
		waterType wType = waterType.FreshWater;

	}

}
