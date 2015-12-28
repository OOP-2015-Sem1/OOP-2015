package logic;
import java.net.URL;
import java.util.Random;

import javax.swing.JComponent;

public class Dice extends JComponent {
	
	private static final long serialVersionUID = 1L;
	

	public int generateNewDice() {

		Random random = new Random();
		int dice;
		dice = random.nextInt(6) + 1;
		return dice;
	}

	public URL getURLForDice(int diceFace) { // load image for given color
		String filename = "";

		switch (diceFace) {
		case 1:
			filename += "face1";
			break;
		case 2:
			filename += "face2";
			break;
		case 3:
			filename += "face3";
			break;
		case 4:
			filename += "face4";
			break;
		case 5:
			filename += "face5";
			break;
		case 6:
			filename += "face6";
			break;
		}
		filename += ".png";

		URL urlDiceImg = getClass().getResource(filename);
		return urlDiceImg;
	}
	
	}

