package javasmmr.zoowsome.models.animals;

public class Tiger extends Mammal{
	
	public Tiger(int nrLegs, String name, float bodyTemp, float hairPerc) {
		
		setNrOfLegs(nrLegs);
		setName(name);
		setNormalBodyTemp(bodyTemp);
		setPercBodyHair(hairPerc);
		
	}
	
	public Tiger(){
		this(4, "Tiger", 37.5f, 100);
	}
	
}
