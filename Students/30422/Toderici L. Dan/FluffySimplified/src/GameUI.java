import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameUI extends JFrame implements KeyEventDispatcher {
	private static JLabel[][] gameMatrix;
	private static Fluffy fluffy;
	private static final int MAX_VIEW = 1;
	private static JPanel myPanel = new JPanel();

	public GameUI() {
		super("The game frame");
		fluffy = new Fluffy(0, 0);
		createMatrixLabel();
		int rows = FluffyFileReader.getRows();
		int colums = FluffyFileReader.getCols();
		System.out.println("Rows: "+rows+" Cols:"+colums);
		myPanel.setLayout(new GridLayout(FluffyFileReader.getRows(),FluffyFileReader.getCols()));
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


				myPanel.add(new JLabel(gameMatrix[i][j].getText()));
			}

		// Responding to user events UP,DOWW,LEFT,RIGHT //
		this.setLayout(new BorderLayout());
		this.add(myPanel, BorderLayout.CENTER);
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(this);
		// myPanel.setVisible(false);
		this.setVisible(true);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		move(fluffy, 0, 1);
		move(fluffy, 0, -1);
	}

	private static void createMatrixLabel() {
		int maxRows = FluffyFileReader.getRows();
		int maxCols = FluffyFileReader.getCols();
		gameMatrix = new JLabel[maxRows][maxCols];
		FluffyFileReader.readBoard();
		char[][] temporary = FluffyFileReader.getBoard();
		for (int i = 0; i < maxRows; i++)
			for (int j = 0; j < maxCols; j++) {
				gameMatrix[i][j] = new JLabel();
				gameMatrix[i][j].setText(String.valueOf(temporary[i][j]));
				if (temporary[i][j] == 'F') {
					fluffy.setFluffyX(i);
					fluffy.setFluffyY(j);
				}
			}

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
		if (gameMatrix[4][17].getText() == " ")
		{	
			gameMatrix[4][17].setText("H");
		    gameMatrix[4][17].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[5][17].getText() == " ")
		{
			gameMatrix[5][17].setText("H");
			gameMatrix[5][17].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[6][17].getText() == " ")
		{
			gameMatrix[6][17].setText("H");
			gameMatrix[6][17].setIcon(new ImageIcon("wall2.png"));
		
		}
		if (gameMatrix[4][19].getText() == " ")
		{
			gameMatrix[4][19].setText("H");
			gameMatrix[4][19].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[5][19].getText() == " ")
		{
			gameMatrix[5][19].setText("H");
			gameMatrix[5][19].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[6][19].getText() == " ")
		{
			gameMatrix[6][19].setText("H");
			gameMatrix[6][19].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[4][33].getText() == " ")
		{
			gameMatrix[4][33].setText("H");
			gameMatrix[4][33].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[5][33].getText() == " ")
		{
			gameMatrix[5][33].setText("H");
			gameMatrix[5][33].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[6][33].getText() == " ")
		{
			gameMatrix[6][33].setText("H");
			gameMatrix[6][33].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[4][35].getText() == " ")
		{
			gameMatrix[4][35].setText("H");
			gameMatrix[4][35].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[5][35].getText() == " ")
		{
			gameMatrix[5][35].setText("H");
			gameMatrix[5][35].setIcon(new ImageIcon("wall2.png"));
		}
		if (gameMatrix[6][35].getText() == " ")
		{
			gameMatrix[6][35].setText("H");
			gameMatrix[6][35].setIcon(new ImageIcon("wall2.png"));
		}
	}

	public static void setFluffyVision() {
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

	public static void gameOver() {
		JOptionPane.showMessageDialog(null, "You won!");
		System.exit(0);
	}

	public static void move(Fluffy fluffy, int xOffset, int yOffset) {
		int maxRows = FluffyFileReader.getRows();
		int maxCols = FluffyFileReader.getCols();
		int x = fluffy.getFluffyX();
		int y = fluffy.getFluffyY();
		switch (gameMatrix[x + xOffset][y + yOffset].getText()) {
		case " ":
			gameMatrix[x][y].setText(" ");
			gameMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameMatrix[x][y].setText("F");
			gameMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			myPanel.removeAll();

			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					gameMatrix[i][j].setVisible(false);
					myPanel.add(gameMatrix[i][j]);
				}

			break;
		case "H":
			gameMatrix[x][y].setText(" ");
			gameMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameMatrix[x][y].setText("F");
			gameMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			myPanel.removeAll();

			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					gameMatrix[i][j].setVisible(false);
					myPanel.add(gameMatrix[i][j]);
				}
			break;
		case "W":
			gameMatrix[x][y].setText(" ");
			gameMatrix[x][y].setIcon(new ImageIcon("lane.png"));
			x += xOffset;
			y += yOffset;
			fluffy.setFluffyX(x);
			fluffy.setFluffyY(y);
			gameMatrix[x][y].setText("F");
			gameMatrix[x][y].setIcon(new ImageIcon("cat.png"));
			myPanel.removeAll();

			for (int i = 0; i < maxRows; i++)
				for (int j = 0; j < maxCols; j++) {
					gameMatrix[i][j].setVisible(false);
					myPanel.add(gameMatrix[i][j]);
				}
			setFluffyVision();
			gameOver();
			break;
		}
		setFluffyVision();
	}

}
