package javasmmr.zoowsome.models.animals;

public class Alligator extends Reptile {

	public Alligator() {
		setNrOfLegs(4);
		setName("Alligator");
		setLaysEggs(true);
	}

	public Alligator(int NrOfLegs, String name, boolean eggs) {
		super(NrOfLegs, name, eggs);
	}

}
