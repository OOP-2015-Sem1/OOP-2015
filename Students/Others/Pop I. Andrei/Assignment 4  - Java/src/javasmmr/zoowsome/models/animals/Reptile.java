package javasmmr.zoowsome.models.animals;

public abstract class Reptile extends Animal {

	public Reptile(double maintenance, double damage) {
		super(maintenance, damage);
		// TODO Auto-generated constructor stub
	}

	private boolean laysEggs;

	public boolean getLaysEggs() {
		return laysEggs;
	}

	public void setLaysEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}
}
