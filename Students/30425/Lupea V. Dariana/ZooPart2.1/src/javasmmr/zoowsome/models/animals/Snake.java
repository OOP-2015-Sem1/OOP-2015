package javasmmr.zoowsome.models.animals;

public class Snake extends Reptile {

	public Snake(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(0);
		setName("Sssnake");
		setLaysEggs(true);

	}

	public Snake(int NrOfLegs, String name, boolean eggs, double maintCost, double dangerP) {
		super(NrOfLegs, name, eggs, maintCost, dangerP);
	}

}
