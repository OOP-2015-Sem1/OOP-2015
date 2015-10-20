package javasmmr.zoowsome.models.animals;

public class Monkey extends Mammal {

	public Monkey(int nrLegs, String name, float bodyTemp, float hairPerc) {
		
		setNrOfLegs(nrLegs);
		setName(name);
		setNormalBodyTemp(bodyTemp);
		setPercBodyHair(hairPerc);
		
	}
	
	public Monkey(){
		this(4, "Monkey", 37f, 100);
	}
	
}
