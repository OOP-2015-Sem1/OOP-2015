package anoyingame.ui;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Menu extends JFrame{
	
	private JPanel blankPanel = new JPanel();
	private JTextArea greeting = new JTextArea("                                     Welcome to The Anoying Game. \n                                        What would you like to play?");
	private JButton timeModeEasy = new JButton("Time mode (easy)");
	private JButton timeModeMedium = new JButton("Time mode (medium)");
	private JButton timeModeHard = new JButton("Time mode (hard)");
	private int mode;
	
	public Menu(){
		this.setLayout(new GridLayout(10,1));
		blankPanel.setBackground(Color.WHITE);
		this.add(blankPanel);
		
		
		blankPanel = new JPanel();
		this.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
	
		greeting.setAlignmentX(CENTER_ALIGNMENT);
		this.add(greeting);
		
		
		blankPanel = new JPanel();
		this.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		

		blankPanel = new JPanel();
		this.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		
		this.add(timeModeEasy);
		this.add(timeModeMedium);
		this.add(timeModeHard);

		blankPanel = new JPanel();
		this.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		

		blankPanel = new JPanel();
		this.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		
		this.setSize(400,500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}

	public JButton getButton(String level){
		if (level.equals("easy"))
			return timeModeEasy;
		else
			if(level.equals("hard"))
				return timeModeHard;
			else
				return timeModeMedium;
	}

	public int getMode(){
		return mode;
	}
}
