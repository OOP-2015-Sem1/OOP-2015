package prototypes;

import java.awt.Color;
import javax.swing.JButton;

public class ModifiedButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1572132690639149990L;
	
	private Color currentColour;
	private Color previousColour;
	
	public void changeColour(Color colour){
		previousColour=currentColour;
		currentColour=colour;
		this.setBackground(currentColour);
	}
	
	public void revertColour(){
		currentColour=previousColour;
		this.setBackground(currentColour);
	}

}
