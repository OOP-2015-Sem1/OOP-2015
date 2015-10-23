package javasmmr.zoowsome.models.animals;

public class Tarantula extends Insect {

	public Tarantula() {
		setNrOfLegs(12);// at least
		setName("Tarantula");
		setCanFly(false);
		setDangerous(true);
	}

	public Tarantula(int nrOfLegs, String name, boolean canFly, boolean dangerous) {
		super(nrOfLegs, name, canFly, dangerous);
	}

}
