package javasmmr.zoowsome.models.animals;

public class Butterfly extends Insect 
{
	 public Butterfly()
	 {
		 this.setName("Lie");
		 this.setNrOfLegs(6);
		 this.setCanFly(true);
		 this.setIsDangerous(false);
	 }
	 
	 public Butterfly(String name, int nrOfLegs, boolean flyStatus, boolean dangerStatus)
	 {
		 this.setNrOfLegs(nrOfLegs);
		 this.setName(name);
		 this.setCanFly(flyStatus);
		 this.setIsDangerous(dangerStatus);
	 }
	 
}
