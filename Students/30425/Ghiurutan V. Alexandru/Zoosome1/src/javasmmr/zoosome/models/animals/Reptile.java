package javasmmr.zoosome.models.animals;

abstract public class Reptile extends Animal {
	private boolean laysEggs;

	public Reptile(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean laysEggs) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf);
		this.setLaysEggs(laysEggs);
	}

	public boolean isLayingEggs() {
		return this.laysEggs;
	}

	public void setLaysEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}
}
