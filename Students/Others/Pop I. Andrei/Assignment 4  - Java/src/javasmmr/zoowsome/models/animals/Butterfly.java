package javasmmr.zoowsome.models.animals;

//import javax.print.attribute.SetOfIntegerSyntax;

public class Butterfly extends Insect {

	public Butterfly() {
		this(6, "Butterfly", false, true, 0.1, 0);
	}

	public Butterfly(int nrLegs, String name, boolean dangerous, boolean canFly, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setIsDangerous(true);
		setCanFly(canFly);
	}

}
