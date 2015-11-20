package fluffy.the.cat.io.frame;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import fluffly.the.cat.game.BoardConfiguration;
import fluffly.the.cat.game.Game;
import fluffy.the.cat.io.AbstractBoardPrinter;

public class BoardFramePrinter extends AbstractBoardPrinter implements KeyListener {
	
	private final int screenScalingFactor = 40;
	private JFrame boardFrame;
	private boolean boardSetUp;
	private int width;
	private int height;
	private Game game;
	
	public BoardFramePrinter(Game game) {
		boardFrame = new JFrame();
		boardSetUp = false;
		this.game = game;
		boardFrame.addKeyListener(this);
	}
	
	@Override
	public void print(BoardConfiguration boardConfiguration) {
		char[][] board = boardConfiguration.getBoard();
		
		if (!boardSetUp) {
			width = board[0].length;
			height = board.length;
			
			boardFrame.setLayout(new GridLayout(0, width));
			boardFrame.setSize(screenScalingFactor*width, screenScalingFactor*height);
			boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			boardFrame.setResizable(false);
			boardSetUp = true;
		}
		
		boardFrame.getContentPane().removeAll();
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (withinRangeOfFluffy(i, j, boardConfiguration)) {
					switch(board[i][j]) {
					case 'F':
						boardFrame.add(new Fluffy());
						break;
					case ' ':
						boardFrame.add(new Floor());
						break;
					case '*':
						boardFrame.add(new Wall());
						break;
					case 'H':
						boardFrame.add(new Mouse());
						break;
					case 'W':
						boardFrame.add(new Hat());
						break;
					default:
						boardFrame.add(new Wall());
						break;
					}
				} else {
					boardFrame.add(new UnknownTeritory());
				}
			}
		}
		boardFrame.pack();
		boardFrame.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		game.verifyInput(String.valueOf(arg0.getKeyChar()));
		game.printBoard();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
}
