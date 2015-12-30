package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Constants;
import logic.BackgammonGame;
import logic.Dice;
import logic.Piece;
import logic.SettingsDialogs;

public class BackgammonGui extends JPanel {

	private static final long serialVersionUID = 3951307773685425235L;

	private BackgammonGame backgammonGame;
	private List<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

	private Image imgBackground;
	private JLabel imageDie1 = new JLabel();
	private JLabel imageDie2 = new JLabel();

	private Dice dice = new Dice();
	public int noOfDie1, noOfDie2;
	public int noOfDie3=0, noOfDie4=0; // in case our dice are equals, we have four
									// moves, so we need four variables
	private URL urlDie1, urlDie2;

	SettingsDialogs settings;
	String player1, player2;

	private JButton btnTurn; // this button shows us the current player
	private JButton btnStart; // press the button to start the game

	public BackgammonGui() {
		this.setLayout(null);
		this.backgammonGame = new BackgammonGame();

		//URL urlBackgroundImg = this.getClass().getClassLoader().getResource("/res/gameboard.png");
		
		//imgBackground = new ImageIcon(urlBackgroundImg).getImage();
		
		imgBackground = new ImageIcon(this.getClass().getResource("/logic/res/gameboard.png")).getImage();

		// wrap game pieces into their graphical representation
		for (Piece piece : this.backgammonGame.getPieces()) {
			createAndAddGuiPiece(piece);
		}

		PiecesDragAndDropListener listener = new PiecesDragAndDropListener(this.guiPieces, this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		settings = new SettingsDialogs();
		player1 = settings.queryForPlayer1();
		player2 = settings.queryForPlayer2();

		// button to start the game
		btnStart = new JButton("go");
		btnStart.addActionListener(new StartTheGameActionListener(this));
		btnStart.setBounds(22, 22, 48, 30);
		this.add(btnStart);

		// button to roll the dice
		JButton btnRollDice = new JButton("Roll");
		btnRollDice.addActionListener(new RollDiceActionListener(this));
		btnRollDice.setBounds(715, 280, 70, 30);
		this.add(btnRollDice);

		// label to display game state
		String labelText = this.getGameStateAsText();
		this.btnTurn = new JButton(labelText);
		btnTurn.setBounds(450, 280, 95, 30);
		btnTurn.setForeground(Color.RED);
		this.add(btnTurn);
		btnTurn.setBackground(Color.WHITE);
		btnTurn.setEnabled(false);

		// create application frame and set visible
		//
		JFrame f = new JFrame();
		f.setSize(80, 80);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.setSize(imgBackground.getWidth(null), imgBackground.getHeight(null));
		f.setResizable(false);

	}

	public void decideWhoBeginsTheGame() {
		JOptionPane.showMessageDialog(null, player1 + ", roll the die!", null, JOptionPane.PLAIN_MESSAGE);
		noOfDie1 = dice.generateNewDice();
		urlDie1 = dice.getURLForDice(noOfDie1);
		ImageIcon icon1 = new ImageIcon(urlDie1);
		imageDie1.setBounds(180, 280, 35, 35);
		this.add(imageDie1);
		imageDie1.setIcon(icon1);

		JOptionPane.showMessageDialog(null, player2 + ", roll the die!", null, JOptionPane.PLAIN_MESSAGE);
		noOfDie2 = dice.generateNewDice();
		urlDie2 = dice.getURLForDice(noOfDie2);
		ImageIcon icon2 = new ImageIcon(urlDie2);
		imageDie2.setBounds(220, 290, 35, 35);
		this.add(imageDie2);
		imageDie2.setIcon(icon2);

		if (noOfDie1 == noOfDie2)
			decideWhoBeginsTheGame();
		else if (noOfDie1 > noOfDie2) {
			JOptionPane.showMessageDialog(null, player1 + ", you begin the game!", null, JOptionPane.PLAIN_MESSAGE);
		} else if (noOfDie1 < noOfDie2) {
			JOptionPane.showMessageDialog(null, player2 + ", you begin the game!", null, JOptionPane.PLAIN_MESSAGE);
			String player = player1;
			player1 = player2;
			player2 = player;
		}
	}

	public void rollDice() {

		noOfDie1 = dice.generateNewDice();
		noOfDie2 = dice.generateNewDice();
		urlDie1 = dice.getURLForDice(noOfDie1);
		urlDie2 = dice.getURLForDice(noOfDie2);
		ImageIcon icon1 = new ImageIcon(urlDie1);
		ImageIcon icon2 = new ImageIcon(urlDie2);

		imageDie1.setBounds(580, 280, 35, 35);
		this.add(imageDie1);
		imageDie1.setIcon(icon1);

		imageDie2.setBounds(620, 293, 35, 35);
		this.add(imageDie2);
		imageDie2.setIcon(icon2);

		if (noOfDie1 == noOfDie2) {
			noOfDie3 = noOfDie1;
			noOfDie4 = noOfDie1;
		}

	}

	private void createAndAddGuiPiece(Piece piece) {
		Image img = this.getImageForPiece(piece.getColor());
		GuiPiece guiPiece = new GuiPiece(img, piece);
		this.guiPieces.add(guiPiece);
	}

	public Image getImageForPiece(int color) { // load image for given color
		String filename = "";

		switch (color) {
		case Constants.COLOR_WHITE:
			filename += "/logic/res/whitePiece";
			break;
		case Constants.COLOR_RED:
			filename += "/logic/res/redPiece";
			break;
		}
		filename += ".jpg";

		URL urlPieceImg = getClass().getResource(filename);
		return new ImageIcon(urlPieceImg).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.imgBackground, 0, 0, null);

		for (GuiPiece guiPiece : this.guiPieces) {
			if (!guiPiece.isCaptured()) {
				g.drawImage(guiPiece.getImage(), guiPiece.getX(), guiPiece.getY(), null);
			}
		}
	}

	private String getGameStateAsText() {
		return (this.backgammonGame.getGameState() == Constants.GAME_STATE_RED ? player1 : player2);
	}

	public void changeGameState() {
		this.backgammonGame.changeGameState();
		this.btnTurn.setText(this.getGameStateAsText());
	}

	public int getGameState() { // return current game state
		return this.backgammonGame.getGameState();
	}

	public static int convertColumnToX(int column) { // convert logical column
														// into x coordinate
		return Constants.PIECES_START_X + Constants.SQUARE_WIDTH * column;
	}

	public static int convertRowToY(int row) { // convert logical row into y
												// coordinate
		return Constants.PIECES_START_Y + Constants.SQUARE_HEIGHT * (Piece.ROW_23 - row);
	}

	public static int convertXToColumn(int x) { // convert logical row into y
												// coordinate
		return (x - Constants.DRAG_TARGET_SQUARE_START_X) / Constants.SQUARE_WIDTH;
	}

	public static int convertYToRow(int y) { // convert y coordinate into
												// logical row
		return Piece.ROW_23 - (y - Constants.DRAG_TARGET_SQUARE_START_Y) / Constants.SQUARE_HEIGHT;
	}

	/**
	 * change location of given piece, if the location is valid. If the location
	 * is not valid, move the piece back to its original position.
	 * 
	 */
	public void setNewPieceLocation(GuiPiece dragPiece, int x, int y) {
		int targetRow = BackgammonGui.convertYToRow(y);
		int targetColumn = BackgammonGui.convertXToColumn(x);

		if (targetRow < Piece.ROW_1 || targetRow > Piece.ROW_23 || targetColumn < Piece.COLUMN_1
				|| targetColumn > Piece.COLUMN_16) {
			// reset piece position if move is not valid
			dragPiece.resetToUnderlyingPiecePosition();

		} else {
			// change model and update gui piece afterwards
			System.out.println("moving piece to " + targetRow + "/" + (targetColumn + 1));

			boolean move1 = backgammonGame.movePiece(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(),
					targetRow, targetColumn, noOfDie1);
			if (move1 == true)
				noOfDie1 = 0;

			boolean move2 = backgammonGame.movePiece(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(),
					targetRow, targetColumn, noOfDie2);
			if (move2 == true)
				noOfDie2 = 0;
			
			boolean move3 = backgammonGame.movePiece(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(),
					targetRow, targetColumn, noOfDie3);
			if (move3 == true)
				noOfDie3 = 0;

			boolean move4 = backgammonGame.movePiece(dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn(),
					targetRow, targetColumn, noOfDie4);
			if (move4 == true)
				noOfDie4 = 0;

			dragPiece.resetToUnderlyingPiecePosition();
		}
	}

}
