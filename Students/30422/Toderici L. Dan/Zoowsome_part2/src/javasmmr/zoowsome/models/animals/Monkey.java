package javasmmr.zoowsome.models.animals;

public class Monkey extends Mammal 
{
	
	public Monkey()
	{
		super(4.5,0.35);
		this.setName("Misha");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(36.2f);
		this.setPercBodyHair(60.4f);
	}
	public Monkey(String name, int nrOfLegs, float normalBodyTemp, float percBodyHair)
	{
		super(4.5,0.35);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}
	
}
