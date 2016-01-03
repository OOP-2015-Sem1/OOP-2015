package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SetPlayerNameView extends JFrame 
{
	private JLabel setNameLabel;
	private JTextField introducedNameField;
	private JButton submitButton;
	private JTextArea instructionsTextArea;

	public SetPlayerNameView(ActionListener actionListener) 
	{
		super("SettupView");
		
		setLocationRelativeTo(null);

		setBackground(Color.lightGray);
		setLayout(null);
		setSize (400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setNameLabel = new JLabel("Enter You Name or Alias:");
		setNameLabel.setForeground(Color.blue);
		setNameLabel.setBounds(20, 20, 200, 30);
		add(setNameLabel);
		
		introducedNameField = new JTextField("");
		introducedNameField.setBounds(20,50,200,30);
		add(introducedNameField);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(actionListener);
		submitButton.setBounds(65,80,80,30);
		add(submitButton);
		
		instructionsTextArea = new JTextArea(12,40);
		instructionsTextArea.setText("Game Instructions:"+'\n'
				+ "You have 5 ships:"+'\n'
				+ "5 spot-size SealShip"+'\n'
				+ "4 spot-size Battleship"+'\n'
				+ "3 spot-size Cruiser"+'\n'
				+ "2 spot-size Destroyer"+'\n'
				+ "1 spot-size Submarine"+'\n'
				+ "First you will place them "+'\n'
				+ "Aftewards the game begins"+'\n'
				+ "During hunt if you hit a ship part "+'\n'+"you have another chance");
		//instructionsTextArea.setFont();
		instructionsTextArea.setBounds(20,120,350,200);
		instructionsTextArea.setFont(new Font("Courier New", Font.ITALIC, 12));
		add(instructionsTextArea);

	}
	
	public String getPlayerName()
	{
		return introducedNameField.getText();
	}

}
