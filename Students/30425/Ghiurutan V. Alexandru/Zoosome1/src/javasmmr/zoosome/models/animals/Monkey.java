package javasmmr.zoosome.models.animals;

public class Monkey extends Mammal {
	public Monkey() {
		this(4, "Monkey", 39.0F, 95.0F);
	}

	public Monkey(int nrOfLegs, String name, float normalBodyTemp, float pereBodyHair) {
		super(nrOfLegs, name, normalBodyTemp, pereBodyHair);
	}
}
