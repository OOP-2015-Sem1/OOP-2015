package javasmmr.zoowsome.models.animals;

abstract public class Mammal extends Animal {

	private float normalBodyTemp;
	private float percBodyHair;

	public float getNormalBodyTemp() {
		return normalBodyTemp;
	}

	public void setNormalBodyTemp(float normalBodyTemp) {
		this.normalBodyTemp = normalBodyTemp;
	}

	public float getPercBodyHair() {
		return percBodyHair;
	}

	public void setPercBodyHair(float percBodyHair) {
		this.percBodyHair = percBodyHair;
	}

	public Mammal() {
		setName(name);
		setNrOfLegs(nrOfLegs);
		setNormalBodyTemp(normalBodyTemp);
		setPercBodyHair(percBodyHair);
	}

	public Mammal(String name, int nrLegs, float normBodyTemp, float percBodyHair) {
		setName(name);
		setNrOfLegs(nrLegs);
		setNormalBodyTemp(normBodyTemp);
		setPercBodyHair(percBodyHair);

	}
}
