package asd;

import java.awt.*;
import javax.swing.*;


public class Gui {

	public JLabel statusLabel = new JLabel();
	public JLabel playerLabel = new JLabel("PLAYER");
	public JLabel dealerLabel = new JLabel("DEALER");

	JPanel playerPanel = new JPanel();
	JPanel dealerPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JPanel statusPanel = new JPanel();
	
	public JButton hit = new JButton("Hit");
	public JButton stay = new JButton("Stay");
	public JButton deal = new JButton("Deal");

	Gui() {
		JFrame gameFrame = new JFrame("BlackJack");

		hit.setEnabled(false);
		stay.setEnabled(false);

		dealerPanel.setBackground(Color.RED);
		playerPanel.setBackground(Color.RED);
		buttonsPanel.setBackground(Color.GREEN);
		statusPanel.setBackground(Color.GREEN);

		gameFrame.setLayout(null);
		dealerPanel.setBounds(0, 50, 300, 450);
		playerPanel.setBounds(300, 50, 300, 450);
		buttonsPanel.setBounds(0, 500, 600, 100);
		statusPanel.setBounds(0, 0, 600, 50);
		
		dealerPanel.setLayout(new FlowLayout());
		playerPanel.setLayout(new FlowLayout());
		buttonsPanel.setLayout(new FlowLayout());
		statusPanel.setLayout(new GridLayout(1,3));
		
		buttonsPanel.add(hit);
		buttonsPanel.add(stay);
		buttonsPanel.add(deal);
		
		statusPanel.add(dealerLabel);
		statusPanel.add(statusLabel);
		statusPanel.add(playerLabel);
		
		gameFrame.add(dealerPanel);
		gameFrame.add(playerPanel);
		gameFrame.add(buttonsPanel);
		gameFrame.add(statusPanel);

		gameFrame.setSize(615, 602);
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
