package trains;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class TrainStation {

	public TrainStation() {

		Set<Integer> train = new HashSet<Integer>(); // here we have the number
														// of the train
		List<String> trainName = new LinkedList<String>(); // here we have the
															// name of the train

		String trains = JOptionPane.showInputDialog("How many trains fo you want to add?");
		int noOfTrain = Integer.parseInt(trains); // number of train but we will
													// use this to assign a
													// number to every train

		while (noOfTrain != 0) {
			String name = JOptionPane.showInputDialog("Name of the train: ");
			trainName.add(name);
			train.add(noOfTrain);
			new Train(noOfTrain);
			noOfTrain--;
		}

	}

	public boolean isTrainInTheStation() {
		boolean state = true;
		return state;
	}
}
