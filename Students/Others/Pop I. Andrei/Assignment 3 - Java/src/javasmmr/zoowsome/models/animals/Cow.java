package javasmmr.zoowsome.models.animals;

public class Cow extends Mammal {

	public Cow(int nrLegs, String name, float bodyTemp, float hairPerc) {

		setNrOfLegs(nrLegs);
		setName(name);
		setNormalBodyTemp(bodyTemp);
		setPercBodyHair(hairPerc);
	}

	public Cow() {
		this(4, "Cow", 37, 100);
	}

}
