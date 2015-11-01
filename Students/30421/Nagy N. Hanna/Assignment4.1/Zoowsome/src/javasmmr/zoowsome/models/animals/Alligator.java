package javasmmr.zoowsome.models.animals;

public class Alligator extends Reptile {

	public Alligator(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Alligator");
		setNrOfLegs(4);
		setLaysEggs(true);
	}

}
