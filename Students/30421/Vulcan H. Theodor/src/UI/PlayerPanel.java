package UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


import GameMechanics.Player;

public class PlayerPanel extends JPanel{

	private static final long serialVersionUID = 5166709536998822945L;
	Player[] player= mainGame.getPlayer();
	public JLabel playerPanelArray[];
	
	public PlayerPanel(){	
		
		playerPanelArray= new JLabel[mainGame.numberOfPlayers];
		
		setMaximumSize(new Dimension (100, 100*mainGame.numberOfPlayers));
		setLayout(new GridLayout(mainGame.numberOfPlayers, 1));
		
		for(int i=0;i<mainGame.numberOfPlayers;i++){
			playerPanelArray[i]= new JLabel();
			playerPanelArray[i].setLayout(new BorderLayout());
			playerPanelArray[i].setText("     "+player[i].name +":          "+player[i].wallet+"$");
			playerPanelArray[i].setIcon(player[i].token);
			Border border= BorderFactory.createLineBorder(Color.BLACK, 1);
			playerPanelArray[i].setBorder(border);
			playerPanelArray[i].setOpaque(true);
			playerPanelArray[i].setBackground(new Color(154,192,205));
			
			playerPanelArray[i].setSize(100, 100);
			
			add(playerPanelArray[i]);
			
			
			
		}
		
	}
}
