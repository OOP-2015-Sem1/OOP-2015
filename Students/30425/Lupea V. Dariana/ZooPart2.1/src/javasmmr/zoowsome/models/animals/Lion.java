package javasmmr.zoowsome.models.animals;

public class Lion extends Mammal {

	public Lion(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setName("Lion");
		setNrOfLegs(4);
		setPercBodyHair(75);
		setNormalBodyTemp(35);
	}

	public Lion(String name, int nrLegs, float normBodyTemp, float percBodyHair, double maintCost, double dangerP) {
		super(name, nrLegs, normBodyTemp, percBodyHair, maintCost, dangerP);
	}
}
