package javasmmr.zoosome.models.animals;

public abstract class Reptile extends Animal {
	public boolean laysEggs;
	
	public abstract boolean isItLayingEggs();
	public abstract void setLayingEggs(boolean laysEggs);
}
