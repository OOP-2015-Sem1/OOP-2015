package javasmmr.zoowsome.models.animals;

public class Butterfly extends Insect {

	public Butterfly(double maintCost, double dangerP) {
		super(maintCost, dangerP);
		setNrOfLegs(6);
		setName("LovelyButterfly");
		setCanFly(true);
		setDangerous(false);
	}

	public Butterfly(int nrOfLegs, String name, boolean canFly, boolean dangerous, double maintCost, double dangerP) {
		super(nrOfLegs, name, canFly, dangerous, maintCost, dangerP);
	}
}
