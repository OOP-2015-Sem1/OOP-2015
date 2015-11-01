package javasmmr.zoowsome.models.animals;

public class Snake extends Reptile {

	public Snake(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Snake");
		setNrOfLegs(0);
		setLaysEggs(true);
	}

}
