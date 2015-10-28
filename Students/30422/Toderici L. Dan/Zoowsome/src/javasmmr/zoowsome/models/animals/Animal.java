package javasmmr.zoowsome.models.animals;

public class Animal {
	private int nrOfLegs;
	private String name;

	public void setNrOfLegs(int nrLegs) {
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
}