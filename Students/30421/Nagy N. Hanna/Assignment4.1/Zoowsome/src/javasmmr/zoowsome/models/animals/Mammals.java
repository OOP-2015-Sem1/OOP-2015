package javasmmr.zoowsome.models.animals;

public abstract class Mammals extends Animal {

	public Mammals(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
	}

	private float percBodyHair;
	private float normalBodyTemp;

	public float getPercBodyHair() {
		return percBodyHair;
	}

	public void setPercBodyHair(float percBodyHair) {
		this.percBodyHair = percBodyHair;
	}

	public float getNormalBodyTemp() {
		return normalBodyTemp;
	}

	public void setNormalBodyTemp(float normalBodyTemp) {
		this.normalBodyTemp = normalBodyTemp;
	}

}
