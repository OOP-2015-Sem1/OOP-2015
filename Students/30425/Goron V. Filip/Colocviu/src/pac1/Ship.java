package pac1;

public class Ship {
	public String name;
	public int bayID;
	public Compartment[] Compartments = new Compartment[100];
	private int comps = 0;

	Ship(){
	}
	
	Ship(String name, int bayID){
		this.name = name;
		this.bayID = bayID;
	}
	
	public void addComp(boolean type){
		Compartments[comps] = new Compartment(type);
	}
	
	public int getComLength(){
		return Compartments.length;
	}
	
	public int computeProfits(){
		int profits = 0;
		
		for(int i = 0; i < Compartments.length ; i++){
			profits = profits + Compartments[i].computeProfits();
		}
		return profits;
	}

}
