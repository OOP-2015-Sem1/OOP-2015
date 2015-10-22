package javasmmr.zoowsome.models.animals;

public class Butterfly extends Insect {

	public Butterfly() {
		setNrOfLegs(6);
		setName("LovelyButterfly");
		setCanFly(true);
		setDangerous(false);
	}

	public Butterfly(int nrOfLegs, String name, boolean canFly, boolean dangerous) {
		super(nrOfLegs, name, canFly, dangerous);
	}
}
