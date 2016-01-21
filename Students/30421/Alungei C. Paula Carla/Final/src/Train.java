import java.util.ArrayList;

public class Train {
	private String name = new String();
	private Double profit;
	private int nrOfWagons = 0;
	private String category = new String();

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public int calculateProfit() {
		return (int) (profit * nrOfWagons);
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}


	ArrayList<Wagon> settingTrain = new ArrayList<Wagon>();

	void addWagon() {
		  Wagon nextWagon = new Wagon (tokens [01 , tokens[l], tokans[2] , tokens[3]);
		 settingTrain .add(nextWagon);
		nrOfWagons++;
		 }

}
