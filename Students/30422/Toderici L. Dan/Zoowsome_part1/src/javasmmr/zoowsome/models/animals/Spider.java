package javasmmr.zoowsome.models.animals;

public class Spider extends Insect 
{

	 public Spider() 
	 {
		 setNrOfLegs(8);
		 setName("Neal");
		 setCanFly(false);
		 setIsDangerous(true);
	 }
	 
	 public Spider(String name) 
	 {
		 setNrOfLegs(8);
		 setName(name);
		 setCanFly(false);
		 setIsDangerous(true);
	 }
}
