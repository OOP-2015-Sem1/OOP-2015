package trains;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class Train {

	public Train(int idTrain) {
		Set<Integer> wagon = new HashSet<Integer>(); // the number of the wagon

		String wagons = JOptionPane.showInputDialog("How many wagons has the train?");
		String wagonType = JOptionPane.showInputDialog("What type of wagons: passenger or cargoitem?");
		int noOfWagon = Integer.parseInt(wagons); // number of wagons but we will
													// use this to assign a
													// number to every wagon 

		while (noOfWagon != 0) {
			String name = JOptionPane.showInputDialog("Name of the wagon: ");
			new Wagon(name,wagonType);
			noOfWagon--;
			wagon.add(noOfWagon);

			int profit = 0;
		}
	}

	public void trainProfit() {

	}

}