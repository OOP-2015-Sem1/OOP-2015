package javasmmr.zoowsome.models.animals;

public class Monkey extends Mammal {

	public Monkey() {
		setName("Monkey");
		setNrOfLegs(4);
		setPercBodyHair(66);
		setNormalBodyTemp(32);
	}

	public Monkey(String name, int nrLegs, float normBodyTemp, float percBodyHair) {
		super(name, nrLegs, normBodyTemp, percBodyHair);
	}
}
