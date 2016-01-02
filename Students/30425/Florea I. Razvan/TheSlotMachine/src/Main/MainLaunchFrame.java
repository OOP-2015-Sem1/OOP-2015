package Main;

import UserInterface.CustomizedFrame;
import static Main.ValuesToWorkWith.credit;
import Functionality.UserDialog;

public class MainLaunchFrame {
	
	static CustomizedFrame frame = new CustomizedFrame("Slot Machine -- OOP Project --");
	
	public static void main(String[] args) {
		
		credit=UserDialog.getInitialCredit();
		frame.getMainFrame();
		
	}
	
	
}
