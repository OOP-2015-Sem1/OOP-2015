package javasmmr.zoowsome.models.animals;

public class Animal implements Killer{
	private int nrOfLegs;
	private String name;
	private final double maintenanceCost;
	private final double dangerPerc;
	private boolean takenCareOf;
	
	public Animal(double maintenanceCost, double dangerPerc)
	{
		this.maintenanceCost = maintenanceCost;
		this.dangerPerc = dangerPerc;
		setTakenCareOf(false);
	}

	public void setNrOfLegs(int nrLegs){
		this.nrOfLegs=nrLegs;
	}
	
	public int getNrOfLegs() {
		return nrOfLegs;
	}
	
	public void setName(String s){
		this.name=s ;
	}
	public String getName(){
		return name;
	}
	
	public double getMaintenanceCost()
	{
		return this.maintenanceCost;
	}

	public double getDangerPerc()
	{
		return this.dangerPerc;
	}

	public boolean kill()  {
		
		return(Math.random() < this.dangerPerc + getPredisposition());
		
	}

	public double getPredisposition()  {
		return 0;
	}

	public boolean getTakenCareOf() {
		return takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}

}