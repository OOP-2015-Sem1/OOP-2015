package io;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import game.BoardConfiguration;

public class OthelloFrame {

	private static final int LINE_BORDER_THICKNESS = 3;

	private JFrame frame;
	private JPanel gamePanel;
	private JPanel scoreTurnPanel;
	private final JLabel labelForScore1;
	private JLabel labelForScore2;
	private JLabel labelForTurn;

	public JButton[][] buttonsForGame;

	public OthelloFrame() {

		frame = new JFrame("Othello");
		gamePanel = new JPanel();
		scoreTurnPanel = new JPanel();
		buttonsForGame = new JButton[BoardConfiguration.ROWS][BoardConfiguration.COLS];

		labelForScore1 = new JLabel("WHITE:BLACK", JLabel.CENTER);
		labelForScore2 = new JLabel();
		labelForScore2.setHorizontalAlignment(JLabel.CENTER);
		labelForScore2.setFont(labelForScore2.getFont().deriveFont(64.0f));
		labelForTurn = new JLabel();
		labelForTurn.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				buttonsForGame[i][j] = new JButton();
				buttonsForGame[i][j].setBorder(new LineBorder(Color.DARK_GRAY, LINE_BORDER_THICKNESS));
				buttonsForGame[i][j].setPreferredSize(new Dimension(50, 50));

				// buttonsForGame[i][j].addActionListener((ActionListener)
				// this);
				gamePanel.add(buttonsForGame[i][j]);
			}
		}

		gamePanel.setPreferredSize(new Dimension(500, 500));
		gamePanel.setLayout(new GridLayout(BoardConfiguration.ROWS, BoardConfiguration.COLS));
		scoreTurnPanel.add(labelForScore1);
		scoreTurnPanel.add(labelForScore2);
		scoreTurnPanel.add(labelForTurn);
		scoreTurnPanel.setLayout(new GridLayout(3, 1));
		scoreTurnPanel.setPreferredSize(new Dimension(100, 500));

		frame.setLayout(new BorderLayout());
		frame.add(gamePanel, BorderLayout.WEST);
		frame.add(scoreTurnPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		frame.setVisible(true);
	}

	public void updateOthelloBoard(int[][] boardConfiguration, int scoreWhite, int scoreBlack, int currentTurn) {
		setColorsOnGameButtons(boardConfiguration);
		setScoreOnPanel(scoreWhite, scoreBlack);
		setLabelForTurn(currentTurn);
	}

	public void setColorsOnGameButtons(int[][] boardConfiguration) {
		for (int i = 0; i < BoardConfiguration.ROWS; i++) {
			for (int j = 0; j < BoardConfiguration.COLS; j++) {
				if (boardConfiguration[i][j] == 0)
					buttonsForGame[i][j].setBackground(Color.green);
				else if (boardConfiguration[i][j] == BoardConfiguration.WHITE)
					buttonsForGame[i][j].setBackground(Color.white);
				else
					buttonsForGame[i][j].setBackground(Color.black);
			}
		}
	}

	public void setScoreOnPanel(int scoreWhite, int scoreBlack) {
		labelForScore2.setText(scoreWhite + ":" + scoreBlack);
	}

	public void setLabelForTurn(int currentTurn) {
		if (currentTurn == BoardConfiguration.WHITE)
			labelForTurn.setText("WHITE's TURN");
		else if (currentTurn == BoardConfiguration.BLACK)
			labelForTurn.setText("BLACK's TURN");
		else if (currentTurn == BoardConfiguration.GAME_OVER)
			labelForTurn.setText("GAME OVER");
	}

	public void writeTheResultOnFrame(int nrGreen, int scoreWhite, int scoreBlack) {
		setLabelForTurn(BoardConfiguration.GAME_OVER);
		if (nrGreen == 0) {
			if (scoreWhite > scoreBlack) {
				buttonsForGame[3][1].setText("W");
				buttonsForGame[3][2].setText("H");
				buttonsForGame[3][3].setText("I");
				buttonsForGame[3][4].setText("T");
				buttonsForGame[3][5].setText("E");

				buttonsForGame[4][4].setText("W");
				buttonsForGame[4][5].setText("O");
				buttonsForGame[4][6].setText("N");
			} else if (scoreWhite < scoreBlack) {
				buttonsForGame[3][1].setText("B");
				buttonsForGame[3][2].setText("L");
				buttonsForGame[3][3].setText("A");
				buttonsForGame[3][4].setText("C");
				buttonsForGame[3][5].setText("K");

				buttonsForGame[4][4].setText("W");
				buttonsForGame[4][5].setText("O");
				buttonsForGame[4][6].setText("N");
			} else {
				buttonsForGame[4][3].setText("D");
				buttonsForGame[4][4].setText("R");
				buttonsForGame[4][5].setText("A");
				buttonsForGame[4][6].setText("W");
			}
		} else {
			buttonsForGame[3][2].setText("B");
			buttonsForGame[3][3].setText("O");
			buttonsForGame[3][4].setText("T");
			buttonsForGame[3][5].setText("H");

			buttonsForGame[4][1].setText("B");
			buttonsForGame[4][2].setText("L");
			buttonsForGame[4][3].setText("O");
			buttonsForGame[4][4].setText("C");
			buttonsForGame[4][5].setText("K");
			buttonsForGame[4][6].setText("E");
			buttonsForGame[4][7].setText("D");
		}
	}
}
/*
 * ACTION LISTENER FOR EVERY BUTTON button.addActionListener(new
 * ActionListener() { public void actionPerformed(ActionEvent e) { //button is
 * pressed System.out.println("You clicked the button"); }});
 */

/**
 * very important!!!!!!!!!!!!!!!!!!!
 * 
 * public class DontExtendJFrame implements ActionListener {
 * 
 * private enum Actions { HELLO, GOODBYE }
 * 
 * public static void main(String[] args) {
 * 
 * DontExtendJFrame instance = new DontExtendJFrame();
 * 
 * JFrame frame = new JFrame("Test"); frame.setLayout(new FlowLayout());
 * frame.setSize(200, 100);
 * 
 * JButton hello = new JButton("Hello");
 * hello.setActionCommand(Actions.HELLO.name());
 * hello.addActionListener(instance); frame.add(hello);
 * 
 * JButton goodbye = new JButton("Goodbye");
 * goodbye.setActionCommand(Actions.GOODBYE.name());
 * goodbye.addActionListener(instance); frame.add(goodbye);
 * 
 * frame.setVisible(true); }
 * 
 * @Override public void actionPerformed(ActionEvent evt) { if
 *           (evt.getActionCommand() == Actions.HELLO.name()) {
 *           JOptionPane.showMessageDialog(null, "Hello"); } else if
 *           (evt.getActionCommand() == Actions.GOODBYE.name()) {
 *           JOptionPane.showMessageDialog(null, "Goodbye"); } } }
 */