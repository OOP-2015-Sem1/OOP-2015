package javasmmr.zoowsome.models.animals;

public class Zebra extends Mammal {
	public Zebra(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setName("Zebrrra");
		setNrOfLegs(4);
		setPercBodyHair(80);
		setNormalBodyTemp(40);
	}

	public Zebra(String name, int nrLegs, float normBodyTemp, float percBodyHair, double maintCost, double dangerP) {
		super(name, nrLegs, normBodyTemp, percBodyHair, maintCost, dangerP);
	}

}
