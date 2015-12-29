package javasmmr.zoowsome.models.animals;

public class Lizard extends Reptile {

	public Lizard() {
		setNrOfLegs(4);
		setName("Lizard");
		setLaysEggs(true);
	}

	public Lizard(int NrOfLegs, String name, boolean eggs) {
		super(NrOfLegs, name, eggs);
	}

}
