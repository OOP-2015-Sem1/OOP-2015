package colocviu;

import java.util.Random;
import java.util.UUID;

public class Train {

	public String name= UUID.randomUUID().toString();
	private Random randomGenerator= new Random();
	public int nrOfWagons=randomGenerator.nextInt(100);
	public Wagon[] linkedWagons= new Wagon[nrOfWagons];
	private String trainType= new String();
	
	public void addWagon(){
		if (trainType==null) {
			int typeNr= randomGenerator.nextInt(1);
			if (typeNr==0) trainType="Cargo";
			else trainType="Passanger";
		}
		if (trainType.equals("cargo")){
			for(int i=0;i<nrOfWagons;i++){
				linkedWagons[i]= new CargoWagon();
			}
		}
		if (trainType.equals("passanger")){
			for(int i=0;i<nrOfWagons;i++){
				linkedWagons[i]= new PassangerWagon();
			}
		}
		
	}
}
