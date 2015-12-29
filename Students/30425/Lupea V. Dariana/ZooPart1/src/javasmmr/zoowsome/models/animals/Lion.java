package javasmmr.zoowsome.models.animals;

public class Lion extends Mammal {

	public Lion() {
		setName("Lion");
		setNrOfLegs(4);
		setPercBodyHair(75);
		setNormalBodyTemp(35);
	}

	public Lion(String name, int nrLegs, float normBodyTemp, float percBodyHair) {
		super(name, nrLegs, normBodyTemp, percBodyHair);
	}
}
