package javasmmr.zoowsome.models.animals;

//import javasmmr.zoowsome.models.animals.Aquatic.waterType;

public class Frog extends Aquatic {

	public Frog(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Frog");
		setNrOfLegs(4);
		setAvgSwimDepth(24);
		
		//not sure on this ...
		waterType wType = waterType.FreshWater;

	}

}
