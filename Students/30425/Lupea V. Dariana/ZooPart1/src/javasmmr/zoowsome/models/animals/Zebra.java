package javasmmr.zoowsome.models.animals;

public class Zebra extends Mammal {
	public Zebra() {
		setName("Zebrrra");
		setNrOfLegs(4);
		setPercBodyHair(80);
		setNormalBodyTemp(40);
	}

	public Zebra(String name, int nrLegs, float normBodyTemp, float percBodyHair) {
		super(name, nrLegs, normBodyTemp, percBodyHair);
	}

}
