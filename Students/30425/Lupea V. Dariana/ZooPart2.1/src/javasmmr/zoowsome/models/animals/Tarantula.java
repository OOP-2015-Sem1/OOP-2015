package javasmmr.zoowsome.models.animals;

public class Tarantula extends Insect {

	public Tarantula(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(12);// at least
		setName("Tarantula");
		setCanFly(false);
		setDangerous(true);
	}

	public Tarantula(int nrOfLegs, String name, boolean canFly, boolean dangerous, double maintCost, double dangerP) {
		super(nrOfLegs, name, canFly, dangerous, maintCost, dangerP);
	}

}
