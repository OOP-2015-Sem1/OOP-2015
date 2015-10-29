package javasmmr.zoowsome.models.animals;

import java.time.LocalTime;

public class Monkey extends Mammal {

	private static final LocalTime beginTime = LocalTime.of(17, 15);
	private static final LocalTime endTime = LocalTime.of(17, 40);// I have implemented it only for the Monkey class
	
	public Monkey(int nrLegs, String name, float bodyTemp, float hairPerc, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setNormalBodyTemp(bodyTemp);
		setPercBodyHair(hairPerc);

	}

	public Monkey() {
		this(4, "Monkey", 37f, 100, 3.6, 0);
	}
	
	public double getPredisposition(){
		
		LocalTime actualTime = LocalTime.now();
		if(actualTime.compareTo(beginTime) == 1 && actualTime.compareTo(endTime) == -1)
			return 0.9;
		else
			return 0;
		
	}

}
