package javasmmr.zoowsome.models.animals;

public class Cockroach extends Insect 
{
	public Cockroach() 
	{
		super(0.5,0.1);
		setName("Red");
		setNrOfLegs(10);
		setCanFly(false);
		setIsDangerous(false);
	}
	
	public Cockroach(String name) 
	{
		super(0.5,0.1);
		setName(name);
		setNrOfLegs(10);
		setCanFly(false);
		setIsDangerous(false);
	}
}
