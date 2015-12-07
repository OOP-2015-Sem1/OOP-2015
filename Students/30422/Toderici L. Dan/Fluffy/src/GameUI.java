import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameUI extends JFrame implements KeyEventDispatcher {
	private JLabel[][] gameMatrix;
	private Fluffy fluffy;
	private final int MAX_VIEW = 1;
	private JPanel gamePanel = new JPanel();
	private int steps=0;
	private char[][] cluesMatrix; 

	public GameUI() {
		super("The game frame");
		this.setLayout(new BorderLayout());
		fluffy = new Fluffy(0, 0);
		createMatrixLabel();
		int rows = FluffyFileReader.getRows();
		int colums = FluffyFileReader.getCols();
		System.out.println("Rows: "+rows+" Cols:"+colums);

		gamePanel.setLayout(new GridLayout(FluffyFileReader.getRows(),FluffyFileReader.getCols()));
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < colums; j++) {

				if(gameMatrix[i][j].getText().equals("*"))
				{
					gameMatrix[i][j].setIcon(new ImageIcon("wall.png"));
				}
				if(gameMatrix[i][j].getText().equals(" "))
				{
					gameMatrix[i][j].setIcon(new ImageIcon("lane.png"));
				}
				if(gameMatrix[i][j].getText().equals("F"))
				{
					gameMatrix[i][j].setIcon(new ImageIcon("cat.png"));
				}
				if(gameMatrix[i][j].getText().equals("H"))
				{
					gameMatrix[i][j].setIcon(new ImageIcon("wall2.png"));
				}
				if(gameMatrix[i][j].getText().equals("W"))
				{
					gameMatrix[i][j].setIcon(new ImageIcon("hat.png"));
				}


				gamePanel.add(new JLabel(gameMatrix[i][j].getText()));
			}

		// Responding to user events UP,DOWW,LEFT,RIGHT //
		this.setLayout(new BorderLayout());
		this.add(gamePanel, BorderLayout.CENTER);
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(this);
		this.setVisible(true);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		move(fluffy, 0, 1);
		move(fluffy, 0, -1);
	}

	private void createMatrixLabel() {
		int maxRows = FluffyFileReader.getRows();
		int maxCols = FluffyFileReader.getCols();

		gameMatrix = new JLabel[maxRows][maxCols];
		cluesMatrix = new char[maxRows][maxCols];


		FluffyFileReader.readBoard();
		char[][] temporary = FluffyFileReader.getGameBoard();
		for (int i = 0; i < maxRows; i++)
			for (int j = 0; j < maxCols; j++) {
				gameMatrix[i][j] = new JLabel();
				gameMatrix[i][j].setText(String.valueOf(temporary[i][j]));
					
				if (temporary[i][j] == 'F') {
					fluffy.setFluffyX(i);
					fluffy.setFluffyY(j);
				}
			
			}

		cluesMatrix = FluffyFileReader.getCluesBoard();

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {

		if (e.getID() == KeyEvent.KEY_PRESSED) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_UP:
				move(fluffy, -1, 0);
				break;
			case KeyEvent.VK_DOWN:
				move(fluffy, 1, 0);
				break;
			case KeyEvent.VK_LEFT:
				move(fluffy, 0, -1);
				break;
			case KeyEvent.VK_RIGHT:
				move(fluffy, 0, 1);
				break;
			}
		}
		this.keepYellowClues();

		repaint();
		revalidate();
		return false;
	}


	public void keepYellowClues()
	{
		int nbOfRows = FluffyFileReader.getRows();
		int nbOfCols = FluffyFileReader.getCols();

		for(int i = 0; i < nbOfRows; i++)
		{
			for(int j = 0 ; j < nbOfCols; j++)
			{
				if((!gameMatrix[i][j].getText().equals(cluesMatrix[i][j]))&&(cluesMatrix[i][j]=='H')&&(!gameMatrix[i][j].getText().equals("F")))
				{
					gameMatrix[i][j].setText("H");
					gameMatrix[i][j].setIcon(new ImageIcon("wall2.png"));
				}
			}
		}
		
	}

	public void setFluffyVision() {
		if (fluffy.getFluffyX() == 1 && fluffy.getFluffyY() == 0) {
			for (int i = -MAX_VIEW; i <= MAX_VIEW; i++) {
				for (int j = -MAX_VIEW + 1; j <= MAX_VIEW; j++) {
					gameMatrix[fluffy.getFluffyX() + i][fluffy.getFluffyY() + j].setVisible(true);

				}
			}
		} else {
			for (int i = -MAX_VIEW; i <= MAX_VIEW; i++) {
				for (int j = -MAX_VIEW; j <= MAX_VIEW; j++) {
					gameMatrix[fluffy.getFluffyX() + i][fluffy.getFluffyY() + j].setVisible(true);

				}
			}
		}
	}

	public void gameOver() {
		JOptionPane.showMessageDialog(null, "You won! Number of Steps:"+steps);
		System.exit(0);
	}

	public void incrementSteps()
	{

		this.steps++;
	}

	public void move(Fluffy fluffy, int xOffset, int yOffset) {
		int maxRows = FluffyFileReader.getRows();
		int maxCols = FluffyFileReader.getCols();
		int x = fluffy.getFluffyX();
		int y = fluffy.getFluffyY();
		switch (gameMatrix[x + xOffset][y + yOffset].getText()) {
		case " ":
			this.incrementSteps();
			gameMatrix[x][y].setText(" ");
			gameMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameMatrix[x][y].setText("F");
			gameMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			gamePanel.removeAll();

			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					gameMatrix[i][j].setVisible(false);
					gamePanel.add(gameMatrix[i][j]);
				}

			break;
		case "H":
			this.incrementSteps();
			gameMatrix[x][y].setText(" ");
			gameMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameMatrix[x][y].setText("F");
			gameMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			gamePanel.removeAll();

			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					gameMatrix[i][j].setVisible(false);
					gamePanel.add(gameMatrix[i][j]);
				}
			break;
		case "W":
			this.incrementSteps();
			gameMatrix[x][y].setText(" ");
			gameMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameMatrix[x][y].setText("F");
			gameMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			gamePanel.removeAll();

			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					gameMatrix[i][j].setVisible(false);
					gamePanel.add(gameMatrix[i][j]);
				}
			setFluffyVision();
			gameOver();
			break;
		}
		setFluffyVision();
	}

}
