package javasmmr.zoowsome.models.animals;

public class Cow extends Mammals {

	
	
	public Cow(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Cow");
		setNrOfLegs(4);
		setPercBodyHair(70);
		setNormalBodyTemp(38.6f);
		
	}

}
