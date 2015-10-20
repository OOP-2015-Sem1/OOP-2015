package javasmmr.zoowsome.models.animals;

//import javax.print.attribute.SetOfIntegerSyntax;

public class Butterfly extends Insect{
	
	public Butterfly() {
		this(6, "Butterfly", false, true);
	}
	
	public Butterfly(int nrLegs, String name, boolean dangerous, boolean canFly) {
		setNrOfLegs(nrLegs);
		setName(name);
		setIsDangerous(true);
		setCanFly(canFly);
	}
	
}
