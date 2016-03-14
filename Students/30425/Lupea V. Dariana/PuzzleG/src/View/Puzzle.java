package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Puzzle extends JFrame implements ActionListener {

	private static final long serialVersionUID = -2190508731945033595L;

	JPanel mainPanel;
	JPanel[][] puzzlePanel;
	List<PuzzlePiece> puzzlePieces;
	private boolean isGameFinished = false;
	private String imagePath;
	private int score;
	private String name;

	public Puzzle(String imagePath) {

		super("Puzzle");
		this.imagePath = imagePath;
		score = 250;
		getContentPane().setLayout(new BorderLayout());
		mainPanel = new JPanel(new GridLayout(3, 3));

		GameButtons gameButtons = new GameButtons();
		add(gameButtons, BorderLayout.NORTH);

		gameButtons.getNewButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				new Puzzle("Images/deer");
			}
		});

		puzzlePanel = new JPanel[3][3];
		puzzlePieces = new ArrayList<PuzzlePiece>();

		initializePuzzlePieces();

		add(mainPanel, BorderLayout.CENTER);
		pack();

		shufflePieces();

		setSize(500, 1200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		name = JOptionPane.showInputDialog(this, "Welcome! What's your name?");

	}

	/* Put each puzzle piece at its right place by initializing them */
	private void initializePuzzlePieces() {
		int imgNr = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (i == 2 && j == 2) {// last piece
					break;
				}

				imgNr++;
				String imagePath = this.imagePath + "/img" + imgNr + ".jpg";
				initializePuzzlePiece(imagePath, imgNr, i, j, false);
			}
		}
		imgNr++;
		String imagePath = "Images/imgEmpty.jpeg";// last piece will be black empty
		initializePuzzlePiece(imagePath, imgNr, 2, 2, true);

	}

	private void initializePuzzlePiece(String imagePath, int imagePosition, int positionX, int positionY,
			boolean isEmptySlot) {

		PuzzlePiece puzzlePiece = new PuzzlePiece(new ImageIcon(imagePath), positionX, positionY, isEmptySlot,
				imagePosition);//

		puzzlePiece.getPuzzlePieceButton().addActionListener(this);
		puzzlePieces.add(puzzlePiece);// add to the ArrayList of puzzle pieces

		puzzlePanel[positionX][positionY] = new JPanel(new BorderLayout());
		puzzlePanel[positionX][positionY].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		mainPanel.add(puzzlePanel[positionX][positionY]);
		puzzlePanel[positionX][positionY].add(puzzlePiece.getPuzzlePieceButton());

	}

	/*
	 * Shuffle puzzle pieces in order to obtain a game target piece -> the
	 * current puzzle piece destination piece -> where the target piece is to be
	 * moved; it is chosen randomly
	 */
	private void shufflePieces() {
		Random random = new Random();
		for (int i = 0; i < puzzlePieces.size(); i++) {
			int positionX = random.nextInt(3);// get a random position for X
												// coordinate < 3
			int positionY = random.nextInt(3);// get a random position for Y
												// coordinate <3
			PuzzlePiece targetPiece = puzzlePieces.get(i);
			PuzzlePiece destinationPiece = getSpecificPieceAfterAddress(positionX, positionY);

			if (destinationPiece == null) {
				continue;
			}
			reverseImages(targetPiece, destinationPiece);
		}
	}

	public void actionPerformed(ActionEvent event) {

		if (isGameFinished) {
			return;
		}

		JButton pushedButton = (JButton) event.getSource();
		score = score - 2;
		String action = pushedButton.getText();
		String[] splitActionCommand = action.split("_");// array [x,y] positions

		if (splitActionCommand.length != 2) {
			return;
		}

		/*
		 * virtual = current position; 
		 * clicked = position where to make the move
		 */
		int virtualX = Integer.parseInt(splitActionCommand[0]);
		int virtualY = Integer.parseInt(splitActionCommand[1]);
		PuzzlePiece clickedPiece = getSpecificPieceAfterAddress(virtualX, virtualY);

		if (clickedPiece == null) {
			return;
		}

		if (clickedPiece.isEmptySlot()) {
			return;
		}
		int i = clickedPiece.getPositionX();
		int j = clickedPiece.getPositionY();

		// the puzzle matrix of positions looks like below
		/*
		 * i-1,j-1    i-1,j      i+1, j+1              0_0   0_1   0_2  
		 * i,j-1       i,j        i, j+1               1_0   1_1   1_2
		 * i+1,j-1    i+1,j       i+1,j+1              2_0   2_1   2_2
		 */

		/* 
		 * Moves are allowed only up, down, left, right, so only those cases are
		 * tested below. A move can be made only by interchanging a normal
		 * puzzle piece with the empty puzzle piece.
		 */

		int pi = 0;
		int pj = 0;

		// i-1, j
		if (i - 1 >= 0) {
			pi = i - 1;
			pj = j;
			interchange(pi, pj, clickedPiece);
		}
		// i,j-1
		if (j - 1 >= 0) {
			pi = i;
			pj = j - 1;
			interchange(pi, pj, clickedPiece);
		}
		// i,j+1
		if (j + 1 <= 3) {
			pi = i;
			pj = j + 1;
			interchange(pi, pj, clickedPiece);
		}
		// i+1,j
		if (i + 1 <= 3) {
			pi = i + 1;
			pj = j;
			interchange(pi, pj, clickedPiece);
		}

		if (isPuzzleFinished()) {
			JOptionPane.showMessageDialog(this, "Puzzle is finished. Your score is: " + score, "Congratulations! ",
					JOptionPane.INFORMATION_MESSAGE);
			System.out.println(name + ", you finished the puzzle. Congratulations!\nYour score is:" + score);

			for (int poz = 0; poz < puzzlePieces.size(); poz++) {
				for (int p = 0; p < puzzlePieces.size(); p++) {
					PuzzlePiece puzzlePiece = puzzlePieces.get(p);
					puzzlePiece.getPuzzlePieceButton().setEnabled(false);
				}
			}
			isGameFinished = true;
		}

		else if (score > 0) {
			System.out.println("Puzzle is NOT finished.");
		} else {
			JOptionPane.showMessageDialog(this, name + ", the puzzle is NOT finished. Please, try again!", "Sorry! ",
					JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Sorry, " + name + "! You did not manage to solve the puzzle. Try again!");
			new Puzzle("Images/bear");

		}
	}

	private void interchange(int puzzleI, int puzzleJ, PuzzlePiece clickedPiece) {

		PuzzlePiece pp = getSpecificPieceAfterAddress(puzzleI, puzzleJ);

		if (pp != null) {

			if (pp.isEmptySlot()) {
				reverseImages(clickedPiece, pp);
			}
		}
	}

	/* Puzzle is finished when each piece is at its right place in the array */
	private boolean isPuzzleFinished() {
		boolean isFinished = true;

		for (int i = 0; i < puzzlePieces.size() - 1; i++) {
			PuzzlePiece p1 = puzzlePieces.get(i);
			PuzzlePiece p2 = puzzlePieces.get(i + 1);

			if (p1.getPieceOrder() > p2.getPieceOrder()) {
				isFinished = false;
				break;
			}
		}

		return isFinished;
	}

	/*
	 * target piece = piece to move; 
	 * destination piece = where to move the piece
	 */
	private void reverseImages(PuzzlePiece targetPiece, PuzzlePiece destinationPiece) {

		// use an auxiliary piece to interchange the pieces => make a move
		ImageIcon auxImageIcon = targetPiece.getPuzzlePieceIcon();
		int auxPieceOrder = targetPiece.getPieceOrder();
		boolean auxIsEmptySlot = targetPiece.isEmptySlot();

		// target piece is put in the field of destination piece
		targetPiece.setPuzzlePieceIcon(destinationPiece.getPuzzlePieceIcon());
		targetPiece.setPieceOrder(destinationPiece.getPieceOrder());
		targetPiece.getPuzzlePieceButton().setIcon(destinationPiece.getPuzzlePieceIcon());
		targetPiece.setPuzzlePieceButtonText();
		targetPiece.setEmptySlot(destinationPiece.isEmptySlot());

		// use the auxiliary piece created above
		destinationPiece.setPuzzlePieceIcon(auxImageIcon);
		destinationPiece.setPieceOrder(auxPieceOrder);
		destinationPiece.getPuzzlePieceButton().setIcon(auxImageIcon);
		destinationPiece.setPuzzlePieceButtonText();
		destinationPiece.setEmptySlot(auxIsEmptySlot);
	}

	/* Find a piece using its coordinates */
	private PuzzlePiece getSpecificPieceAfterAddress(int positionX, int positionY) {
		PuzzlePiece foundPuzzlePiece = null;

		for (int i = 0; i < puzzlePieces.size(); i++) {

			PuzzlePiece puzzlePiece = puzzlePieces.get(i);

			if (puzzlePiece.getPositionX() == positionX && puzzlePiece.getPositionY() == positionY) {
				foundPuzzlePiece = puzzlePiece;
				break;
			}
		}
		return foundPuzzlePiece;
	}

}
