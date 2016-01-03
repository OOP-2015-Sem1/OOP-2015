package Functionality;

import static Main.Constants.MAX_CREDIT;
import static Main.Constants.MIN_CREDIT;
import static Main.Constants.DEFAULT_CREDIT;

import javax.swing.JOptionPane;

public class UserDialog {

	public static int getInitialCredit() {
		String input = JOptionPane.showInputDialog("Initial Credit\n(default credit is 500) :");
		int valueOfCredit;

		try {
			valueOfCredit = Integer.parseInt(input);
			if (Integer.parseInt(input) % 10 != 0) {
				JOptionPane
						.showMessageDialog(null,
								"Please insert a valid Credit: \n " + "	- multiples of 10, 100, 1000 \n "
										+ " - default credit is 500",
								"Invalid credit input !", JOptionPane.WARNING_MESSAGE);

				valueOfCredit = getInitialCredit();
			}

			if (valueOfCredit < MIN_CREDIT || valueOfCredit > MAX_CREDIT) {
				valueOfCredit = DEFAULT_CREDIT;
			}
		} catch (NumberFormatException e) {

			valueOfCredit = DEFAULT_CREDIT;
		}
		return valueOfCredit;
	}

}
