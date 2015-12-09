package theseal.model;

public interface Submersible {
	public abstract void submerge();

	public abstract void surface();

	public abstract boolean isSubmerged();

	public abstract void consumeAir();

	public abstract int airLeft();

	public abstract boolean outOfAir();

	public abstract void replentishAir();
}
