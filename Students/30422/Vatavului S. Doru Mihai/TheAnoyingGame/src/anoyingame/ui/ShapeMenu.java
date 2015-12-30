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


	
public class ShapeMenu extends JFrame{
	
	private int mode;
	private JPanel blankPanel = new JPanel();
	private JTextArea greeting = new JTextArea("                                     Welcome to The Anoying Memory Game. \n                                        What would you like to play?");
	private JButton easyMode = new JButton("Easy mode ");
	private JButton mediumMode = new JButton("Medium mode ");
	private JButton hardMode = new JButton("Hard mode ");
	private JLabel imageLabel;
	private ImageIcon image;
		
	public ShapeMenu(){
		this.setLayout(new GridLayout(2,1));
		image = new ImageIcon(getClass().getResource("MemoryGame.png"));
		imageLabel = new JLabel(image);
		imageLabel.setBackground(Color.WHITE);
		this.setBackground(Color.WHITE);
		this.add(imageLabel);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5,1));
		
		
		blankPanel = new JPanel();
		buttonPanel.add(blankPanel);
		blankPanel.setBackground(Color.WHITE);
		
		easyMode.setBackground(new Color(0,178,192));
		easyMode.setForeground(new Color(251,233,163));
		mediumMode.setBackground(new Color(0,178,192));
		mediumMode.setForeground(new Color(251,233,163));
		hardMode.setBackground(new Color(0,178,192));
		hardMode.setForeground(new Color(251,233,163));
		
		buttonPanel.add(easyMode);
		buttonPanel.add(mediumMode);
		buttonPanel.add(hardMode);

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
			return easyMode;
		else
			if(level.equals("hard"))
				return hardMode;
			else
				return mediumMode;
		}

	public int getMode(){
		return mode;
	}
	
	
}


