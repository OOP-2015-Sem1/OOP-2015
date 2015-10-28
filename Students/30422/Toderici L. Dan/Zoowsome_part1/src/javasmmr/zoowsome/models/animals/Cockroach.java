package javasmmr.zoowsome.models.animals;

public class Cockroach extends Insect 
{
	public Cockroach() 
	{
		setName("Red");
		setNrOfLegs(10);
		setCanFly(false);
		setIsDangerous(false);
	}
	
	public Cockroach(String name) {
		setName(name);
		setNrOfLegs(10);
		setCanFly(false);
		setIsDangerous(false);
	}
}
