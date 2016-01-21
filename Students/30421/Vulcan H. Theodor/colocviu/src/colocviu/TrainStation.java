package colocviu;

import java.util.ArrayList;

public class TrainStation {

	public int nrOfTrains= 0;
	public Train[] trainsInStation= new Train[20];
	
	
	public void recieveTrain(){
		Train rTrain=new Train();
		trainsInStation[nrOfTrains]=rTrain;
		nrOfTrains++;
		
	}
	
	public void departTrain(String name){
		boolean startShiftingTrains= false;
		for (int i=0;i<nrOfTrains;i++){
			if (trainsInStation[i].name.equals(name) || startShiftingTrains==true){
				trainsInStation[i]=trainsInStation[i+1];
				startShiftingTrains=true;
			}
		}
		if (startShiftingTrains==false) System.out.println("no train with that name found");
	}
	
	public void checkingTrain(String name){
		boolean trainFound=false;
		for (int i=0;i<nrOfTrains;i++){
			if (trainsInStation[i].name.equals(name)) {
				trainFound=true;
				break;
			}
		}
		if (trainFound==true) System.out.println("The train is in the station");
		else System.out.println("There is no such train in the station");
	
	}
}
