package javasmmr.zoowsome.models.animals;

public class Cockroach extends Insect {

	public Cockroach() {
		this(6, "Cockroach", false, false, 0.1, 0.2);
	}

	public Cockroach(int nrLegs, String name, boolean dangerous, boolean canFly, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setIsDangerous(true);
		setCanFly(canFly);
	}
}
