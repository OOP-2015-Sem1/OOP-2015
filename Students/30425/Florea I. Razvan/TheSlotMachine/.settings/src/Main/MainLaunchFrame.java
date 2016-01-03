package Main;

import UserInterface.CustomizedFrame;
import static Main.ValuesToWorkWith.credit;
import Functionality.UserDialog;

public class MainLaunchFrame {

	public static void main(String[] args) {

		credit = UserDialog.getInitialCredit();
		new CustomizedFrame("OOP Project  --Slot Machine--");
	}

}
