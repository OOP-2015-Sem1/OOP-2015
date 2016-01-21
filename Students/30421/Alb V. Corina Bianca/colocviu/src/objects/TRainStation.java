package objects;

public class TRainStation {
	
	private boolean trainInStation;
	private boolean receiveOrDepart;
	
	public boolean isInStation() {
		return trainInStation;
	}
	
	public void setInStation(boolean isOrNot) {
		trainInStation = isOrNot;
	}
	
	public boolean receiveTrain() {
		return receiveOrDepart;
	}
	
	public void setReceive(boolean j){
		receiveOrDepart = j;
	}
	
	public boolean check(Train thisTrain) {
		return trainInStation;
	}
	
}