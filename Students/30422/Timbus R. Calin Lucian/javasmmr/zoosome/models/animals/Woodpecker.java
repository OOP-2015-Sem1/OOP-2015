package javasmmr.zoosome.models.animals;

public class Woodpecker extends Bird {

	
	public Woodpecker(String name, int avgFlightAltitude, boolean migrates,int nrOfLegs){
		this.name = name;
		this.avgFlightAltitude = avgFlightAltitude;
		this.migrates = migrates;
		this.nrOfLegs = nrOfLegs;
	}
	
	public Woodpecker(){
		this("Crimilula",120,true,2);
	}
	
	@Override
	public void setAvgFlightAltitude(int avgFlightAltitude) {
		this.avgFlightAltitude = avgFlightAltitude;
	}

	@Override
	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	@Override
	public int getAvgFlightAltitude() {
		return avgFlightAltitude;
	}

	@Override
	public boolean getMigrates() {
		return migrates;
	}

}
