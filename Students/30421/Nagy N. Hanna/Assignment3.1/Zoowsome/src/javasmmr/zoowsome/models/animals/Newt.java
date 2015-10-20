package javasmmr.zoowsome.models.animals;

//import javasmmr.zoowsome.models.animals.Aquatic.waterType;

public class Newt extends Aquatic {

	public Newt() {
		setName("Newt");
		setNrOfLegs(4);
		avgSwimDepth = 20;
		
		//not sure on this ... 
		waterType wType = waterType.FreshWater;

	}

}
