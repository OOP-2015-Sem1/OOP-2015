package javasmmr.zoowsome.models.animals;

public class Butterfly extends Insect 
{
	 public Butterfly()
	 {
		 super(0.5, 0);
		 this.setName("Lie");
		 this.setNrOfLegs(6);
		 this.setCanFly(true);
		 this.setIsDangerous(false);
	 }
	 
	 public Butterfly(String name, int nrOfLegs, boolean flyStatus, boolean dangerStatus)
	 {
		 super(0.5, 0);
		 this.setNrOfLegs(nrOfLegs);
		 this.setName(name);
		 this.setCanFly(flyStatus);
		 this.setIsDangerous(dangerStatus);
	 }
	 
}
