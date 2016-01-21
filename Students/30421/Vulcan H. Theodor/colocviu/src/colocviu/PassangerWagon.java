package colocviu;

import java.util.UUID;

public class PassangerWagon extends Wagon {

	Passanger[] passangerArray= new Passanger[100];
	int nrOfPassangers=0;
	
	public void addPassanger(){
		Passanger pass= new Passanger(UUID.randomUUID().toString());
		passangerArray[nrOfPassangers]= pass;
		nrOfPassangers++;
	}

	@Override
	public void calculateProfit() {
		super.profit=100* nrOfPassangers ;
		
	}
	
}
