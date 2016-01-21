import java.util.ArrayList;

public class Train {
	public ArrayList<Wagon> train = new ArrayList<Wagon>();
	public String name; 
	Train(int nrofwagons, String typeofcargo, String nameoftrain) {
		name = nameoftrain;
		if (typeofcargo == "cargo" || typeofcargo == "passangers") {
			for (int i = 0; i < nrofwagons; i++) {
				Wagon wag = new Wagon(typeofcargo, 100);
				train.add(wag);
			}
		}
	}
}
