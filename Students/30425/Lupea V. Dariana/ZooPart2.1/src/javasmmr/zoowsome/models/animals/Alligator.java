package javasmmr.zoowsome.models.animals;

public class Alligator extends Reptile {

	public Alligator(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(4);
		setName("Alligator");
		setLaysEggs(true);
	}

	public Alligator(int NrOfLegs, String name, boolean eggs, double maintCost, double dangerP) {
		super(NrOfLegs, name, eggs, maintCost, dangerP);
	}

}
