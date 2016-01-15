package UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OptionPanel extends JPanel {
	
	private static final long serialVersionUID = -1425993411776065694L;

		public OptionPanel(){
			
			JButton menuButton = new JButton("Menu");
			setButtonColor(menuButton);
			JButton tradeButton = new JButton("Trade");
			setButtonColor(tradeButton);
			JButton mortgageButton = new JButton("Mortgage");
			setButtonColor(mortgageButton);
			JButton unmortgageButton = new JButton("Unmortgage");
			setButtonColor(unmortgageButton);
			JButton buildButton = new JButton("Build");
			setButtonColor(buildButton);
			JButton sellButton = new JButton("Sell");
			setButtonColor(sellButton);
			JButton rollDiceButton = new JButton("Roll Dice");
			setButtonColor(rollDiceButton);
			JButton endTurnButton = new JButton("End Turn");
			setButtonColor(endTurnButton);
			Dimension topContainerSize= new Dimension(100, 180);
			Dimension bottomContainerSize= new Dimension(100, 60);
			
			JPanel topContainer= new JPanel();
			topContainer.setPreferredSize(topContainerSize);
			JPanel bottomContainer= new JPanel();
			bottomContainer.setPreferredSize(bottomContainerSize);
			
			setLayout(new BorderLayout());
			topContainer.setLayout(new GridLayout(3, 2, 7, 7));
			bottomContainer.setLayout(new GridLayout(1, 2, 7, 7));
			setBackground(Board.UIColor);
			topContainer.setBackground(Board.UIColor);
			bottomContainer.setBackground(Board.UIColor);
			topContainer.add(buildButton);
			topContainer.add(sellButton);
			topContainer.add(mortgageButton);
			topContainer.add(unmortgageButton);
			topContainer.add(tradeButton);
			topContainer.add(menuButton);
			bottomContainer.add(rollDiceButton);
			bottomContainer.add(endTurnButton);
			
			add(topContainer, BorderLayout.NORTH);
			add(bottomContainer, BorderLayout.SOUTH);
			
		}
		
		private void setButtonColor(JButton targetButton){
			
			targetButton.setBackground(new Color(132, 198, 100));
			targetButton.setForeground(new Color(51, 88, 33));
		}
}
