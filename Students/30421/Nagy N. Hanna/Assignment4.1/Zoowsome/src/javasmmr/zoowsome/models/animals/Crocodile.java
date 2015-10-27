package javasmmr.zoowsome.models.animals;

public class Crocodile extends Reptile {

	public Crocodile(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Crocodile");
		setNrOfLegs(4);
		setLaysEggs(true);
	}

}
