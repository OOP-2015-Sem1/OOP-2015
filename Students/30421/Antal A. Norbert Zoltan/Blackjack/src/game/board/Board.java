package game.board;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import game.cards.Card;
import game.control.Controller;

public class Board extends JFrame {
	private static final long serialVersionUID = 2195379760914381351L;

	private JPanel dealer = new JPanel();
	private JPanel player = new JPanel();
	private JPanel controls = new JPanel();

	private ArrayList<JPanel> players = new ArrayList<JPanel>();
	private ArrayList<JLabel> playerInfos = new ArrayList<JLabel>();

	private JButton newButton = new JButton("New Game");
	private JButton hitButton = new JButton("Hit");
	private JButton standButton = new JButton("Stand");
	public JMenuItem displayScore = new JMenuItem("Display score");

	private JLabel back = new JLabel(new ImageIcon("CardImages/back.png"));

	private Card hole;

	private int nrPlayers;

	public Board(Controller controller) {
		setLayout(new GridLayout(3, 1));
		setSize(1200, 700);
		this.add(dealer);
		this.add(player);
		newButton.addActionListener(controller);
		hitButton.addActionListener(controller);
		controls.add(hitButton);
		standButton.addActionListener(controller);
		controls.add(standButton);
		this.add(controls);
		JMenuBar menuBar = new JMenuBar();
		JMenu accMenu = new JMenu("Accounts");
		menuBar.add(accMenu);
		JMenuItem login = new JMenuItem("Log-in or create account");
		login.addActionListener(controller);
		JMenuItem guest = new JMenuItem("Play as guest");
		guest.addActionListener(controller);
		displayScore.addActionListener(controller);
		displayScore.setEnabled(false);
		accMenu.add(login);
		accMenu.add(guest);
		accMenu.add(displayScore);
		JMenu playerMenu = new JMenu("Players");
		JMenuItem playerMenuI = new JMenuItem("Adjust number of players");
		playerMenuI.addActionListener(controller);
		playerMenu.add(playerMenuI);
		menuBar.add(accMenu);
		menuBar.add(playerMenu);
		setJMenuBar(menuBar);
		newButton.setVisible(false);
		setButtons(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void clear() {
		dealer.removeAll();
		dealer.repaint();
		adjustNrPlayers(nrPlayers);
	}

	public void drawPlayer(Card pCard, int playerNr) {
		JPanel playerPanel = players.get(playerNr);
		playerPanel.add(new JLabel(pCard.getImg()));
		playerPanel.repaint();
		player.repaint();
	}

	public void drawDealer(Card hole) {
		if (back.getParent() == dealer) {
			dealer.remove(back);
			dealer.add(new JLabel(this.hole.getImg()));
			dealer.add(back);
		} else {
			dealer.add(back);
		}
		this.hole = hole;
		dealer.repaint();
	}

	public void showDealer() {
		dealer.remove(back);
		dealer.add(new JLabel(hole.getImg()));
	}

	public void setInfo(String text, int playerNr) {
		playerInfos.get(playerNr).setText(text);
	}

	public void setButtons(boolean visible) {
		hitButton.setVisible(visible);
		standButton.setVisible(visible);
	}

	public void adjustNrPlayers(int nr) {
		nrPlayers = nr;
		controls.removeAll();
		controls.setLayout(new GridLayout(1, nr + 3));
		playerInfos.clear();
		if (nr == 2 || nr == 3) {
			JLabel infoLabel = new JLabel();
			playerInfos.add(infoLabel);
			controls.add(infoLabel);
		}
		controls.add(newButton);
		controls.add(hitButton);
		controls.add(standButton);
		playerInfos.add(new JLabel());
		controls.add(playerInfos.get(playerInfos.size() - 1));
		if (nr == 3) {
			JLabel infoLabel = new JLabel();
			playerInfos.add(infoLabel);
			controls.add(infoLabel);
		}
		player.removeAll();
		players.clear();
		player.setLayout(new GridLayout(1, nr));
		for (int i = 0; i < nr; i++) {
			JPanel pPanel = new JPanel();
			players.add(pPanel);
			player.add(pPanel);
		}
	}

	public void showStart(int nrPlayers) {
		adjustNrPlayers(nrPlayers);
		newButton.setVisible(true);
	}
}
