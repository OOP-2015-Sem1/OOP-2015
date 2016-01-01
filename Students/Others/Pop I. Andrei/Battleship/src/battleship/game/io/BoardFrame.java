package battleship.game.io;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import battleship.components.BoardPanel;
import battleship.components.GlassBoardPanel;
import battleship.components.ShipsPanel;
import battleship.game.BoardConfiguration;
import battleship.game.Game;
import battleship.models.Ship;

public class BoardFrame extends JFrame implements ActionListener{

	private final int MAX_ROW;
	private final int MAX_COL;
	public final int BOARD_HEIGHT = 600;
	private JButton[][] myBoardCells, computerBoardCells;
	private JPanel boardPanel, playerBoardPanel, computerBoardPanel, leftPanel, shipsPanel, statusPanel;
	private CardLayout card;
	private ArrayList<Ship> ships;
	private final BoardConfiguration theBoard;
	private final Game myGame;
	private final int WATER = 0;
	private JMenuBar menuBar;
	private JMenu options;
	private JMenuItem save, restore, exit;
	private HandleFire handleFireAction;
	private HandleShipHighlight shipHighlight;
	private JLabel statusLabel, playerLabel;
	private JTextArea infoLabel;

	public BoardFrame(int MAX_ROW, int MAX_COL, BoardConfiguration board, Game myGame) {
		super("BATTLESHIP");
		setLayout(new BorderLayout());
		this.MAX_ROW = MAX_ROW;
		this.MAX_COL = MAX_COL;
		this.theBoard = board;
		this.ships =  board.getMyShips();
		this.myGame = myGame;
		myBoardCells = board.getMyBoardCells();
		computerBoardCells = board.getComputerBoardCells();
		statusLabel = new JLabel();
		playerLabel = new JLabel();
		infoLabel = new JTextArea();
		shipHighlight = new HandleShipHighlight(myBoardCells, theBoard);//  de ce aici pot sa ii trimit boardCells chiar daca in momentul asta nu au fost initializate si el totusi sa le vada ca si cum ar fi initializate
		handleFireAction = new HandleFire(theBoard, this, myGame);
		//createTheMenu();
		createPanels();
		createMyInitialBoard();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(860, 600);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void createPanels() {
		playerBoardPanel = new BoardPanel(MAX_ROW, MAX_COL);
		computerBoardPanel = new BoardPanel(MAX_ROW, MAX_COL);
		boardPanel = new JPanel();
		card = new CardLayout();
		boardPanel.setLayout(card);
		createComputerInitialBoard();
		
		boardPanel.add(computerBoardPanel);
		boardPanel.add(playerBoardPanel);
		boardPanel.setEnabled(false);
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		
		shipsPanel = new ShipsPanel(ships, shipHighlight);
		
		statusPanel = new JPanel();
		
		statusLabel.setFont(new Font("Serif", Font.BOLD, 16));
		playerLabel.setFont(new Font("Serif", Font.BOLD, 16));
		infoLabel.setFont(new Font("Serif", Font.BOLD, 16));
		
		statusPanel.add(statusLabel);
		statusPanel.add(playerLabel);
		statusPanel.add(infoLabel);
		statusPanel.setMaximumSize(new Dimension(150, 150));
		statusPanel.setSize(new Dimension(150, 150));
		
		//optionsPanel.add(difficultyPanel);
		leftPanel.add(statusPanel);
		leftPanel.add(shipsPanel);
		
		leftPanel.setPreferredSize(new Dimension(260, 600));
		add(leftPanel, BorderLayout.WEST);
		add(boardPanel, BorderLayout.CENTER);
	}

	public JButton createNewCell(int backgroundType, String who) {
		JButton cell = new JButton("");
		if (backgroundType == 0)
			cell.setBackground(Color.BLACK);
		else
			cell.setBackground(Color.RED);
		if(who.equals("computer"))
			cell.addActionListener(handleFireAction);
		return cell;
	}

	private void createMyInitialBoard() {
		
		for (int i = 0; i < MAX_ROW; i++)
			for (int j = 0; j < MAX_COL; j++) {
				myBoardCells[i][j] = createNewCell(WATER, "me");
				myBoardCells[i][j].addMouseListener(shipHighlight);
				computerBoardPanel.add(myBoardCells[i][j]);
			}
	}
	
	public void createComputerInitialBoard() {
		for (int i = 0; i < MAX_ROW; i++)
			for (int j = 0; j < MAX_COL; j++) {
				computerBoardCells[i][j] = createNewCell(WATER, "computer");
				playerBoardPanel.add(computerBoardCells[i][j]); // the computer cells will be added on the player's board and viceversa
			}
	}

	public void setNextPlayer() {
		card.next(boardPanel);
	}
	
	public JButton[][] getMyBoardCells() {
		return myBoardCells;
	}
	
	public JButton[][] getComputerBoardCells() {
		return computerBoardCells;
	}
	
	public void setStatusInfo(String info) {
		statusLabel.setText(info);
	}
	
	public void setPlayerInfo(String info) {
		playerLabel.setText("Player: " + info);
	}
	
	public void setGameInfo(String info) {
		infoLabel.setText(info);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			JOptionPane.showMessageDialog(null, "Thank you for playing");
			System.exit(0);
		}
		else if(e.getSource() == save) {
		}
		else {
			
		}
	}

}
