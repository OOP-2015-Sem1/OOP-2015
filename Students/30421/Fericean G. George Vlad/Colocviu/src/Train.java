import java.util.LinkedHashSet;
import java.util.Set;

public class Train<T> {
	public String trainName;
	public int totalProfit = 0;
	public int nrWagons;
	Set wagons = new LinkedHashSet();
	Wagon wagon1 = new Wagon();

	public void addWagon() {
		wagons.add(wagon1);
	}

	public String getTrainName() {
		return this.trainName;
	}

	public int getTrainWagons() {
		return this.nrWagons;
	}

	public int getTrainProfit() {
		return this.totalProfit;
	}
}
