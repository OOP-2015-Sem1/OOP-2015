package javasmmr.zoowsome.models.animals;

public class Monkey extends Mammal 
{
	
	public Monkey()
	{
		this.setName("Misha");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(36.2f);
		this.setPercBodyHair(60.4f);
	}
	public Monkey(String name, int nrOfLegs, float normalBodyTemp, float percBodyHair)
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}
	
}
