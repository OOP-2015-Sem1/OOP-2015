package javasmmr.zoowsome.models.animals;

public class Spider extends Insect {

	public Spider(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Spider");
		setNrOfLegs(6);
		canFly = false;
		isDangerous = true;
	}

}
