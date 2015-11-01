package javasmmr.zoowsome.models.animals;

public class ButterFly extends Insect{

	public ButterFly(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("ButterFly");
		setNrOfLegs(6);
		canFly = true;
		isDangerous = false;
	}
	
}
