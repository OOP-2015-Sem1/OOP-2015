package javasmmr.zoowsome.models.animals;

// cs�t�ny
public class Cockroach extends Insect {

	public Cockroach(Double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
		setName("Coackroach");
		setNrOfLegs(6);
		canFly = false;
		isDangerous = true;
	}

	
}
