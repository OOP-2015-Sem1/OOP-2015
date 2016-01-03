package javasmmr.zoowsome.models.animals;

//import javasmmr.zoowsome.models.animals.Aquatic.waterType;

public class Salamander extends Aquatic {

	public Salamander(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Salamander");
		setNrOfLegs(4);
		setAvgSwimDepth(20);
		
		//not sure on this ...
		waterType wType = waterType.SaltWater;

	}

}
