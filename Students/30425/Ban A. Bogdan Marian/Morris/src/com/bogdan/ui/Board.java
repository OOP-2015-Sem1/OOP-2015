package com.bogdan.ui;

import java.util.List;

import javax.swing.JFrame;

import com.bogdan.model.Mill;
import com.bogdan.model.Piece;
import com.bogdan.model.Player;
import com.bogdan.model.PlayerType;
import com.bogdan.ui.utils.ImageUtils;

@SuppressWarnings("serial")
public class Board extends JFrame {
	private LayeredPanel panel;
	private PlayerType currentPlayer = PlayerType.None;
	private Player playerOne = new Player();
	private Player playerTwo = new Player();

	public Board() {
		this.setTitle("Nine Men Morris");
		this.setIconImage(ImageUtils.createImageIcon("/com/bogdan/ui/resources/icon.png").getImage());
		this.setSize(650, 720);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MorrisMenuBar menuBar = new MorrisMenuBar(this);
		this.setJMenuBar(menuBar);
		MorrisMouseListener listener = new MorrisMouseListener(this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		panel = new LayeredPanel();
		this.setContentPane(panel);
		this.setVisible(true);
	}

	public List<Piece> getPieces() {
		return panel.getPieces();
	}

	public List<Mill> getMills() {
		return panel.getMills();
	}

	public void updateStatusMessage(String text) {
		panel.updateStatusMessage(text);
	}

	public PlayerType getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PlayerType player) {
		this.currentPlayer = player;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(Player playerOne) {
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo) {
		this.playerTwo = playerTwo;
	}

}
