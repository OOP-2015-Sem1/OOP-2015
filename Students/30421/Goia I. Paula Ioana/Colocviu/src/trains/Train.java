package trains;

import java.util.LinkedList;
import java.util.UUID;

import source.CargoItem;
import source.Passanger;

public class Train {
	
	public String name;
	public LinkedList<Wagon> train;
	
	public Train(){
		this.name=UUID.randomUUID().toString();
		this.train=new LinkedList<Wagon>();
	}
	
	
	public void createPassangerTrain(){
		 Wagon<Passanger> passangerWagon= new Wagon<Passanger>();
		 passangerWagon.addPasangers();
		 this.train.addLast(passangerWagon);
	}
	public void createCargoTrain(){
		 Wagon <CargoItem> cargoWagon =new Wagon<CargoItem>();
		 cargoWagon.addCargo();
		 this.train.addLast(cargoWagon);
	}
	 public int computeTrainPassangerProfit(){
		 int nrwagons=this.train.size();
		 int profit =0;
		 for(int i=0;i< nrwagons;i++)
		 for(Wagon p:train){
			  profit+=p.computeProfitForPassangerWagon();
			 
		 }
		 return profit;
	 }
	 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Wagon> getTrain() {
		return train;
	}
	public void setTrain(LinkedList<Wagon> train) {
		this.train = train;
	}

	
	
	
}
