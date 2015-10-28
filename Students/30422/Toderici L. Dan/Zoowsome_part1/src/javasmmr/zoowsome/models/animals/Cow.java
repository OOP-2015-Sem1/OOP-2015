package javasmmr.zoowsome.models.animals;

public class Cow extends Mammal
{
	public Cow()
	{
		this.setName("Shushan");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(38.6f);
		this.setPercBodyHair(80.0f);
	}
	
	public Cow( String name, int nrOfLegs, float normalBodyTemp, float percBodyHair )
	{
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}
}
