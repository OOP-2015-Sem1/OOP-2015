package anoyingame.ui;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Menu extends JFrame{
	
	private JPanel blankPanel = new JPanel();
	private JButton timeModeEasy = new JButton("EASY");
	private JButton timeModeMedium = new JButton("MEDIUM");
	private JButton timeModeHard = new JButton("HARD");
	private JLabel imageLabel;
	private ImageIcon image;
	private int mode;
	
	public Menu(){
		this.setLayout(new GridLayout(2,1));
		image = new ImageIcon(getClass().getResource("ColorGame.png"));
		imageLabel = new JLabel(image);
		imageLabel.setBackground(Color.WHITE);
		this.setBackground(Color.WHITE);
		this.add(imageLabel);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5,1));
		
		
		blankPanel = new JPanel();
		buttonPanel.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		
		timeModeEasy.setBackground(new Color(0,178,192));
		timeModeEasy.setForeground(new Color(251,233,163));
		timeModeMedium.setBackground(new Color(0,178,192));
		timeModeMedium.setForeground(new Color(251,233,163));
		timeModeHard.setBackground(new Color(0,178,192));
		timeModeHard.setForeground(new Color(251,233,163));
		
		buttonPanel.add(timeModeEasy);
		buttonPanel.add(timeModeMedium);
		buttonPanel.add(timeModeHard);

		blankPanel = new JPanel();
		buttonPanel.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		
		this.add(buttonPanel);
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
