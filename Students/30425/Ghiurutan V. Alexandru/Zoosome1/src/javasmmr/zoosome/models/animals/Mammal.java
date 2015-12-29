package javasmmr.zoosome.models.animals;

abstract public class Mammal extends Animal {
	private float normalBodyTemp;
	private float pereBodyHair;

	public Mammal(int nrOfLegs, String name, float normalBodyTemp, float pereBodyHair) {
		super(nrOfLegs, name);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPereBodyHair(pereBodyHair);
	}

	public float getNormalBodyTemp() {
		return this.normalBodyTemp;
	}

	public float getPereBodyHair() {
		return this.pereBodyHair;
	}

	public void setNormalBodyTemp(float normalBodyTemp) {
		this.normalBodyTemp = normalBodyTemp;
	}

	public void setPereBodyHair(float pereBodyHair) {
		this.pereBodyHair = pereBodyHair;
	}
}
