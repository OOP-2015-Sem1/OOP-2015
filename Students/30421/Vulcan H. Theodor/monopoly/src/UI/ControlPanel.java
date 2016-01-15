package UI;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;



public class ControlPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969280129122570471L;
	private static int OPTION_PANEL_HEIGHT= 500+240/mainGame.numberOfPlayers-20*mainGame.numberOfPlayers;
	private static int PLAYER_PANEL_HEIGHT= 50*mainGame.numberOfPlayers;

	public ControlPanel(){
		
		Dimension playerPanelComponentSize= new Dimension(500, PLAYER_PANEL_HEIGHT);
		Dimension propertyPanelComponentSize= new Dimension(500, 500);
		Dimension optionPanelComponentSize= new Dimension(500, OPTION_PANEL_HEIGHT);
		
		PlayerPanel playerPanelComponent= new PlayerPanel();
		PropertyPanel propertyPanelComponent= new PropertyPanel();
		OptionPanel optionPanelComponent= new OptionPanel();
		
		playerPanelComponent.setPreferredSize(playerPanelComponentSize);
		propertyPanelComponent.setPreferredSize(propertyPanelComponentSize);
		optionPanelComponent.setPreferredSize(optionPanelComponentSize);
		
		setLayout(new BorderLayout());
		
		setBackground(Board.UIColor);
		
		add(playerPanelComponent, BorderLayout.NORTH);
		add(propertyPanelComponent, BorderLayout.CENTER);
		add(optionPanelComponent, BorderLayout.SOUTH);
			
		
	}
	
}
