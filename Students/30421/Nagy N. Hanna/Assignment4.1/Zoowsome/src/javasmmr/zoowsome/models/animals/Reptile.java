package javasmmr.zoowsome.models.animals;

public abstract class Reptile extends Animal{
	
	private boolean laysEggs;

	
	public Reptile(Double maintenanceCost,double dangerPerc){
		super(maintenanceCost,dangerPerc);
	}
	
	
	public boolean isLaysEggs() {
		return laysEggs;
	}

	public void setLaysEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}

}
