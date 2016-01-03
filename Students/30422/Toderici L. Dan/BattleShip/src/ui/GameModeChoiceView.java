package ui;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GameModeChoiceView extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	private JLabel gameModeLabel;
	private JLabel playerVsPlayerOptionLabel;
	private JLabel playerVsAiOptionLabel;
	private JLabel aiVsAiOptionLabel;
	private JTextField choosenOptionField;
	private JButton submitButton;

	public GameModeChoiceView() 
	{
		super();
		setLocationRelativeTo(null);
		
		setBackground(Color.lightGray);
		setLayout(null);
		setSize (400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gameModeLabel = new JLabel("Choose Game Mode:");
		gameModeLabel.setBounds(100, 50, 120,30);
		gameModeLabel.setForeground(Color.blue);

		playerVsPlayerOptionLabel = new JLabel("1.Player vs Player");
		playerVsPlayerOptionLabel.setBounds(100, 85, 120,30);

		playerVsAiOptionLabel = new JLabel("2.Player vs Ai");
		playerVsAiOptionLabel.setBounds(100, 120, 80,30);

		aiVsAiOptionLabel = new JLabel("3. Ai vs Ai");
		aiVsAiOptionLabel.setBounds(100, 155, 80,30);

		choosenOptionField = new JTextField();
		choosenOptionField.setBounds(100,190,80,30);

		submitButton = new JButton("Submit");
		submitButton.setBounds(100, 225, 80, 30);


		add(gameModeLabel);
		add(playerVsPlayerOptionLabel);
		add(playerVsAiOptionLabel);
		add(aiVsAiOptionLabel);
		add(choosenOptionField);
		add(submitButton);
	

	}

	public void setChoice(String message)
	{
		choosenOptionField.setText(message);
	}

	public int getChoice()
	{
		return Integer.parseInt(choosenOptionField.getText());
	}

	public void addChoiceListener(ActionListener e) 
	{

		submitButton.addActionListener(e);

	}

	public void displayErrorMessage(String errorMessage)
	{
		JOptionPane.showMessageDialog(this, errorMessage);
	}

	public void displayGoodInputMessage(int index)
	{
		String message = null;
		switch(index)
		{
		case 1:
			message = "Player vs Player Mode";
			break;
		case 2:
			message ="Player vs Ai Mode";
			break;
		case 3:
			message = "Ai vs Ai Mode";
			break;
		}
		JOptionPane.showMessageDialog(this, message);
	}

}
