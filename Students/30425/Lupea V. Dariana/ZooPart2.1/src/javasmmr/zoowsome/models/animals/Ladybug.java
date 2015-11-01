package javasmmr.zoowsome.models.animals;

public class Ladybug extends Insect {

	public Ladybug(double maintCost, double dangerP) {
		super(maintCost, dangerP);

		setName("Ladybug");
		setNrOfLegs(6);
		setCanFly(true);
		setDangerous(false);
	}

	public Ladybug(int nrOfLegs, String name, boolean canFly, boolean dangerous, double maintCost, double dangerP) {
		super(nrOfLegs, name, canFly, dangerous, maintCost, dangerP);
	}

}
