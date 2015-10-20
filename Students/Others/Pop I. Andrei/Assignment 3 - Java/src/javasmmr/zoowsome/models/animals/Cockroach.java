package javasmmr.zoowsome.models.animals;

public class Cockroach extends Insect{
	
	public Cockroach() {
		this(6, "Cockroach", false, false);
	}
	
	public Cockroach(int nrLegs, String name, boolean dangerous, boolean canFly) {
		setNrOfLegs(nrLegs);
		setName(name);
		setIsDangerous(true);
		setCanFly(canFly);
	}
}
