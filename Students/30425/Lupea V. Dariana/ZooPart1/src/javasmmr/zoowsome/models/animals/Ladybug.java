package javasmmr.zoowsome.models.animals;

public class Ladybug extends Insect {

	public Ladybug() {
		setName("Ladybug");
		setNrOfLegs(6);
		setCanFly(true);
		setDangerous(false);
	}

	public Ladybug(int nrOfLegs, String name, boolean canFly, boolean dangerous) {
		super(nrOfLegs, name, canFly, dangerous);
	}

}
