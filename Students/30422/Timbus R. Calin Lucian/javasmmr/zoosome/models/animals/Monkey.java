package javasmmr.zoosome.models.animals;

public class Monkey extends Mammal {
	public Monkey(int nrOfLegs, String name, float normalBodyTemperature, float percHairBody) {
		this.nrOfLegs = nrOfLegs;
		this.name = name;
		this.normalBodyTemp = normalBodyTemperature;
		this.percHairBody = percHairBody;
	}

	public Monkey() {
		this(2, "Joanna", 20.3F, 12);
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
