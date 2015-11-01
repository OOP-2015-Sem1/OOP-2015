package javasmmr.zoowsome.models.animals;

public class Insect extends Animal
{
	private boolean canFly;
	private boolean isDangerous;
	
	public Insect(double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}
	
	public void setCanFly (boolean choice) 
	{
		this.canFly = choice;
	}
	public boolean getCanFLy(){
		return canFly;
	}

	public void setIsDangerous (boolean choice) 
	{
		this.isDangerous = choice;
	}
	public boolean getIsDangerous(){
		return isDangerous;
	}
}
