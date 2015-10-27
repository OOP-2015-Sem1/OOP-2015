package javasmmr.zoosome.models.animals;

public class Tiger extends Mammal {

	public Tiger(int nrOfLegs, String name, float normalBodyTemperature, float percHairBody) {
		this.nrOfLegs = nrOfLegs;
		this.name = name;
		this.normalBodyTemp = normalBodyTemperature;
		this.percHairBody = percHairBody;
	}

	public Tiger() {
		this(4, "Ionut", 20.3F, 12);
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
