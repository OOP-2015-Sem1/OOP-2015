package objects;

import java.util.ArrayList;

import interfaces.Wagon;

public class Train {
	private int nrOfWagons;
	private String trainName;
	private boolean isPassenger;
	private boolean isCargo;
	private int profit;
	private ArrayList<Wagon> wagonsList;
	
	public Train() {
		wagonsList = new ArrayList<>();
	}
	
	public void addToList(Wagon newTrain){
		wagonsList.add(newTrain);
	}
	
	public ArrayList<Wagon> getWagonsList() {
		return wagonsList;
	}

	public void setWagonsList(ArrayList<Wagon> wagonsList) {
		this.wagonsList = wagonsList;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getNrOfWagons() {
		return nrOfWagons;
	}

	public void setNrOfWagons(int nrOfWagons) {
		this.nrOfWagons = nrOfWagons;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public boolean isPassenger() {
		return isPassenger;
	}

	public void setPassenger(boolean isPassenger) {
		this.isPassenger = isPassenger;
	}

	public boolean isCargo() {
		return isCargo;
	}

	public void setCargo(boolean isCargo) {
		this.isCargo = isCargo;
	}

	public void addToProfit(int newProfit) {
		profit = profit + newProfit;
	}
	
	public int getProfit() {
		return profit;
	}
	
	
	
}