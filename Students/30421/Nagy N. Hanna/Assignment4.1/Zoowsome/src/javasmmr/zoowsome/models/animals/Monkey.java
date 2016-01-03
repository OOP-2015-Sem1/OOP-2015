package javasmmr.zoowsome.models.animals;

public class Monkey extends Mammals {

	public Monkey(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Monkey");
		setNrOfLegs(4);
		setPercBodyHair(90);
		setNormalBodyTemp(39.4f);

	}
}