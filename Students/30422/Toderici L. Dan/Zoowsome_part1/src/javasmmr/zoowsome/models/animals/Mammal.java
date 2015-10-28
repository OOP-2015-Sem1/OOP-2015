package javasmmr.zoowsome.models.animals;

public class Mammal extends Animal
{
	private float normalBodyTemp;
	private float percBodyHair;
	
	public void setNormalBodyTemp (float bodytemp) 
	{
		this.normalBodyTemp = bodytemp;
	}
	public float getBodyTemp()
	{
		return normalBodyTemp;
	}
	
	public void setPercBodyHair (float percHair) 
	{
		this.percBodyHair = percHair;
	}
	public float getPercBobdyHair()
	{
		return percBodyHair;
	}
}
