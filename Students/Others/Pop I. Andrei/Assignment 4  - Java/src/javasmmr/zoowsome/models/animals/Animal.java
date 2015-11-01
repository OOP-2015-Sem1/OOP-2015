package javasmmr.zoowsome.models.animals;

public abstract class Animal implements Killer{
	private int nrOfLegs;
	private String name;
	final double maintenanceCost;
	final double damagePerc;
	private boolean takenCareOf = false;

	public Animal(){
		this(0.1, 0);
	}
	
	public Animal(double maintenance, double damage) {
		if(maintenance < 0.1)
			maintenanceCost = 0.1;
		else if(maintenance > 8.0)
			maintenanceCost = 8.0;
		else
			maintenanceCost = maintenance;
		
		if(damage < 0)
			damagePerc = 0;
		else if(damage > 1)
			damagePerc = 1;
		else
			damagePerc = damage;
	}	
	
	public boolean kill() {
		double nr= Math.random();
		if(nr < damagePerc + this.getPredisposition())
			return true;
		else
			return false;
	}
	
	public double getPredisposition() {
		return 0;
	}
	
	public boolean isTakenCareOf() {
		return takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}

	public int getNrOfLegs() {
		return nrOfLegs;
	}

	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getMaintenanceCost() {
		return maintenanceCost;
	}
	
	public double getDamagePerc() {
		return damagePerc;
	}

}