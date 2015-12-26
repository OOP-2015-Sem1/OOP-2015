package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import source.User;

public class WinnersFrame implements ActionListener {

	private JFrame winFrame;
	private ArrayList<String> winners;
	private ArrayList<String> playersInformation;
	private ArrayList<JLabel> win;
	private ArrayList<JLabel> info;
	private JPanel infoPanel;
	private JPanel winPanel;
	private JButton newGame;
	private User user;

	public WinnersFrame(ArrayList<String> winners,
			ArrayList<String> playersInformation, User user) {
		
		this.winners = winners;
		this.user = user;
		this.playersInformation = playersInformation;

		this.newGame = new JButton("New Game");
		this.win = new ArrayList<>();
		this.info = new ArrayList<>();
		this.winFrame = new JFrame();
		this.infoPanel = new JPanel();
		this.winPanel = new JPanel();

		this.winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.winFrame.setSize(500, 700);
		this.winFrame.setVisible(true);
		this.winFrame.setLocationRelativeTo(null);
		this.winFrame.setLayout(new GridLayout(2, 1));

		displayResults();
		addInFrame();

		this.winFrame.add(this.infoPanel);
		this.winFrame.add(this.winPanel);

		addActionListeners();

	}

	private void addInFrame() {
		this.infoPanel.setLayout(new GridLayout(14, 1, 5, 5));
		for (JLabel infoPlayer : this.info) {
			this.infoPanel.add(infoPlayer);
		}
		
		this.winPanel.setLayout(new GridLayout(14, 1, 5, 5));
		for (JLabel winnerPlayer : this.win) {
			this.winPanel.add(winnerPlayer);
		}
		
		JLabel moneyValue = new JLabel("You have in you account : "
				+ this.user.getMoney() + "$");
		
		this.winPanel.add(moneyValue);
		this.winPanel.add(this.newGame);
	}

	private void displayResults() {
		for (int i = 0; i < this.playersInformation.size(); i++) {
			JLabel aux = new JLabel(this.playersInformation.get(i));
			this.info.add(aux);
		}
		this.winPanel.add(new JLabel("   The Winners are: "));
		
		for (int i = 0; i < this.winners.size(); i++) {
			JLabel aux = new JLabel("   " + this.winners.get(i));
			this.win.add(aux);
		}

	}

	private void addActionListeners() {
		this.newGame.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.newGame) {
			this.winFrame.setVisible(false);
			new SelectNumberOfPlayersFrame(this.user);
		}

	}

}
