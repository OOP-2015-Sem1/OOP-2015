package javasmmr.zoowsome.models.animals;

public class Snake extends Reptile {

	public Snake() {
		setNrOfLegs(0);
		setName("Sssnake");
		setLaysEggs(true);

	}

	public Snake(int NrOfLegs, String name, boolean eggs) {
		super(NrOfLegs, name, eggs);
	}

}
