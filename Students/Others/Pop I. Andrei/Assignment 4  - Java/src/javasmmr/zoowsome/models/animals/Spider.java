package javasmmr.zoowsome.models.animals;

public class Spider extends Insect {

	public Spider() {
		this(8, "Spider", true, false, 0.2, 0.5);
	}

	public Spider(int nrLegs, String name, boolean dangerous, boolean canFly, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setIsDangerous(true);
		setCanFly(canFly);
	}

}
