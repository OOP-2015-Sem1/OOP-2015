package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;



public class TetrisFrame extends JFrame {

	private static final long serialVersionUID = 8642950339011443044L;

	private static final int FRAME_HEIGHT = 600;

	private static final int FRAME_WIDTH = 300;

	private GamePanel gamePanel;
	private JLabel scoreLabel;

	public TetrisFrame() {

		super("Tetris");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		gamePanel = new GamePanel();
		scoreLabel = new JLabel("score: " + gamePanel.getScore());

		updateBoard();
		add(gamePanel, BorderLayout.CENTER);
		add(scoreLabel, BorderLayout.SOUTH);
		setFocusable(true);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void moveTetromino(String direction) {
		gamePanel.shape.move(direction);
		updateBoard();
		requestFocus();
	}

	private void updateBoard() {
		// TODO Auto-generated method stub
		scoreLabel.setText("score: " + gamePanel.getScore());
		gamePanel.updateBoard();
	}

}
