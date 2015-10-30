package javasmmr.zoowsome.models.animals;

public class Lizard extends Reptile {

	public Lizard(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(4);
		setName("Lizard");
		setLaysEggs(true);
	}

	public Lizard(int NrOfLegs, String name, boolean eggs, double maintCost, double dangerP) {
		super(NrOfLegs, name, eggs, maintCost, dangerP);
	}

}
