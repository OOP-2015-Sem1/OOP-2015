package javasmmr.zoowsome.models.animals;

public abstract class Aquatic extends Animal {

	private Integer avgSwimDepth;

	protected enum waterType {
		SaltWater, FreshWater
	}

	public Aquatic(Double maintenanceCost, double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		
	}

	public Integer getAvgSwimDepth() {
		return avgSwimDepth;
	}

	public void setAvgSwimDepth(Integer avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	};

}
