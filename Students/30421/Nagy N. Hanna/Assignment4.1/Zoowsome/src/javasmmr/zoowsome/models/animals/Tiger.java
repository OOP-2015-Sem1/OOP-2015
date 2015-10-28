package javasmmr.zoowsome.models.animals;

public class Tiger extends Mammals {

	public Tiger(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Tiger");
		setNrOfLegs(4);
		setPercBodyHair(97);
		setNormalBodyTemp(37.5f);

	}

}
