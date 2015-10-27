package javasmmr.zoosome.models.animals;

public class Cow extends Mammal {

	public Cow(int nrOfLegs, String name, float normalBodyTemperature, float percHairBody) {
		this.nrOfLegs = nrOfLegs;
		this.name = name;
		this.normalBodyTemp = normalBodyTemperature;
		this.percHairBody = percHairBody;
	}

	public Cow() {
		this(4, "Ionela", 20.3F, 12);
	}
	@Override
	public void setNormalBodyTemperature(float normalBodyTemp) {
		this.normalBodyTemp = normalBodyTemp;
	}

	@Override
	public float getPercHairBody() {
		return this.percHairBody;
	}

	@Override
	public void setPercHairdBody(float percHairBody) {
		this.percHairBody = percHairBody;

	}

	@Override
	public float getNormalBodyTemp() {
		return this.normalBodyTemp;
	}

}
