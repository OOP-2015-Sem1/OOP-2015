package GameMechanics;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tile extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4099169228070786668L;
	public JPanel tokenArea= new JPanel();
	public JPanel[] tokenPanel= new JPanel[4];
	public JPanel houseArea= new JPanel();
	public int[] playersOnTile= new int[4]; 
	
	public Tile(){
	setLayout(new BorderLayout());
		
	}
	
	public void addToken(ImageIcon token, int playerIndex){
		JLabel tokenContainer= new JLabel();
		tokenContainer.setIcon(token);
		
	}
}
