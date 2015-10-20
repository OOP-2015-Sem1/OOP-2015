package javasmmr.zoowsome.models.animals;

public class Spider extends Insect{
	
	public Spider(){
		this(8, "Spider", true, false);
	}
	
	public Spider(int nrLegs, String name, boolean dangerous, boolean canFly) {
		setNrOfLegs(nrLegs);
		setName(name);
		setIsDangerous(true);
		setCanFly(canFly);
	}

}
