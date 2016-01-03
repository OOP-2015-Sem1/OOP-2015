package asd;

import java.awt.*;
import javax.swing.*;

public class Gui {
	Gamble gamb = new Gamble();

	String name = JOptionPane.showInputDialog("Enter your name");
	public JTextField playerName = new JTextField(name);

	String players = JOptionPane.showInputDialog("Enter number of players (max 3)");
	int nrplayers = Integer.parseInt(players);

	ImageIcon face; // this are used for the first dealer card
	JLabel cardDown = new JLabel(); // which is face down

	public JLabel statusLabel = new JLabel();
	public JLabel dealerLabel = new JLabel("DEALER");

	JPanel dealerPanel = new JPanel();
	JPanel buttonsPanel = new JPanel();
	JPanel statusPanel = new JPanel();
	JPanel betPanlel = new JPanel();
	JPanel playerPanel = new JPanel();
	JPanel player1Panel = new JPanel();
	JPanel player2Panel = new JPanel();

	public JButton hit = new JButton("Hit");
	public JButton stay = new JButton("Stay");
	public JButton deal = new JButton("Deal");

	public JButton bet1 = new JButton("50");
	public JButton bet2 = new JButton("100");
	public JButton bet3 = new JButton("200");
	JFrame gameFrame = new JFrame("BlackJack");
	public JTextField moneytxt = new JTextField("You have" + gamb.getMoney() + " $");
	public JTextField bettxt = new JTextField("BET: " + gamb.getBet() + " $");

	Gui() {

		moneytxt.setEditable(false);
		bettxt.setEditable(false);
		playerName.setEditable(false);
		hit.setEnabled(false);
		stay.setEnabled(false);
		deal.setEnabled(true);
		bet1.setEnabled(false);
		bet2.setEnabled(false);
		bet3.setEnabled(false);

		dealerPanel.setBackground(Color.RED);
		playerPanel.setBackground(Color.RED);
		buttonsPanel.setBackground(Color.GREEN);
		statusPanel.setBackground(Color.GREEN);
		betPanlel.setBackground(Color.GREEN);
		playerName.setBackground(Color.GREEN);

		gameFrame.setLayout(null);
		dealerPanel.setBounds(0, 50, 300, 450);
		playerPanel.setBounds(300, 50, 300, 450);
		buttonsPanel.setBounds(0, 500, 300, 100);
		statusPanel.setBounds(0, 0, 600, 50);
		betPanlel.setBounds(300, 500, 300, 100);

		dealerPanel.setLayout(new FlowLayout());
		playerPanel.setLayout(new FlowLayout());
		buttonsPanel.setLayout(new FlowLayout());
		statusPanel.setLayout(new GridLayout(1, 3));

		buttonsPanel.add(hit);
		buttonsPanel.add(stay);
		buttonsPanel.add(deal);

		statusPanel.add(dealerLabel);
		statusPanel.add(statusLabel);
		statusPanel.add(playerName);

		betPanlel.add(bet1);
		betPanlel.add(bet2);
		betPanlel.add(bet3);
		betPanlel.add(moneytxt);
		betPanlel.add(bettxt);

		gameFrame.add(dealerPanel);
		gameFrame.add(playerPanel);
		gameFrame.add(buttonsPanel);
		gameFrame.add(statusPanel);
		gameFrame.add(betPanlel);

		nrplayers = setplayers(nrplayers);
		updatePanel(nrplayers);

		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public int setplayers(int x) {
		if (x == 2 || x == 3)
			return --x;
		else
			return 0;
	}

	public void updatePanel(int x) {
		if (x == 1 || x == 2) {
			gameFrame.setSize(915, 602);
			JTextField player1Name= new JTextField("Player 1");
			player1Name.setEditable(false);
			player1Name.setBackground(Color.GREEN);
			player1Panel.setBackground(Color.RED);
			player1Panel.setBounds(600, 50, 300, 450);
			gameFrame.add(player1Panel);
			JPanel namePanel1 = new JPanel();
			namePanel1.setBounds(600,0, 300, 50);
			namePanel1.setBackground(Color.GREEN);
			namePanel1.add(player1Name);
			gameFrame.add(namePanel1);
			gameFrame.getContentPane().setBackground( Color.GREEN );
			if (x == 2) {
				gameFrame.setSize(1215, 602);
				JTextField player2Name= new JTextField("Player 2");
				player2Name.setBackground(Color.GREEN);
				player2Name.setEditable(false);
				player2Panel.setBackground(Color.RED);
				player2Panel.setBounds(900, 50, 300, 450);
				gameFrame.add(player2Panel);
				JPanel namePanel2 = new JPanel();
				namePanel2.setBounds(900,0, 300, 50);
				namePanel2.setBackground(Color.GREEN);
				namePanel2.add(player2Name);
				gameFrame.add(namePanel2);
			
			}
		} else if (x == 0)
			gameFrame.setSize(615, 602);
	}

	public void updateMoney(int a, int b) {
		moneytxt.setText("You have" + a + " $");
		bettxt.setText("BET: " + b + " $");
	}

}
