package javasmmr.zoowsome.models.animals;

public class Tiger extends Mammal 
{
	public Tiger()
	{
		this.setName("Muriel");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(40.6f);
		this.setPercBodyHair(85.6f);
	}

	public Tiger(String name, int nrOfLegs, float normalBodyTemp, float percBodyHair)
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}
}
