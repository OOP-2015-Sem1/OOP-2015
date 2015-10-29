package javasmmr.zoowsome.models.animals;

public abstract class Insect extends Animal {

	protected boolean canFly;
	protected boolean isDangerous;

	public Insect(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
	}

}
