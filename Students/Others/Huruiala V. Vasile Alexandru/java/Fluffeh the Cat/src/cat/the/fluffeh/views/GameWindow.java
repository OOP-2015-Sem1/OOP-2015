package cat.the.fluffeh.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import cat.the.fluffeh.game.GameState;

/**
 * 
 * @author alexh This class represents the controller, containing all the
 *         listeners; it communicates with gameState and gamePanel Contains view
 *         elements: the menuBar and gamePanel(which is a view itself).
 */
public class GameWindow extends JFrame {
	private static final long serialVersionUID = 8912626258678758352L;

	private static final char UP = 'w';
	private static final char LEFT = 'a';
	private static final char DOWN = 's';
	private static final char RIGHT = 'd';
	private static final String HELP_TITLE = "Help Fluffeh find her hat";
	private static final String HELP = "Navigate through the labyrinth and search for the hat. Use the keys W, A, S and D to move in the four directions.";
	private static final String GAMEOVER_TITLE = "Fluffeh has found her hat!";
	private static final String GAMEOVER = "Congratulations! Fluffeh gets in her hat and goes to sleep.";

	private JMenuBar menuBar;

	private JMenu file;
	private JMenuItem newGame;
	private JMenuItem saveGame;
	private JMenuItem loadGame;
	private JMenuItem saveAndExit;

	private JMenu move;
	private JMenuItem moveUp;
	private JMenuItem moveLeft;
	private JMenuItem moveDown;
	private JMenuItem moveRight;

	private JMenu help;
	private JMenuItem instructions;

	private GamePanel gamePanel;
	private GameState gameState;

	public GameWindow(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.gameState = new GameState();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.gamePanel = new GamePanel(this.gameState, d.width, d.height);

		this.setSize(d.width, d.height);
		this.setLayout(new BorderLayout());

		// Menu bar
		this.menuBar = new JMenuBar();

		// File menu
		this.file = new JMenu("File");
		this.file.setMnemonic(KeyEvent.VK_F);
		// New game item
		this.newGame = new JMenuItem("New game");
		this.newGame.setMnemonic(KeyEvent.VK_N);
		this.newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		this.newGame.addActionListener(new NewGameListener());
		this.file.add(this.newGame);
		this.file.addSeparator();
		// Save game item
		this.saveGame = new JMenuItem("Save game");
		this.saveGame.setMnemonic(KeyEvent.VK_S);
		this.saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		this.saveGame.addActionListener(new SaveGameListener());
		this.file.add(this.saveGame);
		// Load game item
		this.loadGame = new JMenuItem("Load game");
		this.loadGame.setMnemonic(KeyEvent.VK_L);
		this.loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		this.loadGame.addActionListener(new LoadGameListener());
		this.file.add(this.loadGame);
		this.file.addSeparator();
		// Save and exit item
		this.saveAndExit = new JMenuItem("Save and exit");
		this.saveAndExit.setMnemonic(KeyEvent.VK_X);
		this.saveAndExit.addActionListener(new SaveAndExitListener());
		this.saveAndExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK));
		this.file.add(this.saveAndExit);
		this.menuBar.add(this.file);

		// Move menu
		this.move = new JMenu("Move");
		this.move.setMnemonic(KeyEvent.VK_M);
		// Move up item
		this.moveUp = new JMenuItem("Up");
		this.moveUp.setMnemonic(KeyEvent.VK_W);
		this.moveUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Event.CTRL_MASK));
		this.moveUp.addActionListener(new MoveUpListener());
		this.move.add(moveUp);
		this.move.addSeparator();
		// Move left item
		this.moveLeft = new JMenuItem("Left");
		this.moveLeft.setMnemonic(KeyEvent.VK_A);
		this.moveLeft.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		this.moveLeft.addActionListener(new MoveLeftListener());
		this.move.add(moveLeft);
		this.move.addSeparator();
		// Move down item
		this.moveDown = new JMenuItem("Down");
		this.moveDown.setMnemonic(KeyEvent.VK_S);
		this.moveDown.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		this.moveDown.addActionListener(new MoveDownListener());
		this.move.add(moveDown);
		this.move.addSeparator();
		// Move right item
		this.moveRight = new JMenuItem("Right");
		this.moveRight.setMnemonic(KeyEvent.VK_D);
		this.moveRight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		this.moveRight.addActionListener(new MoveRightListener());
		this.move.add(moveRight);
		this.menuBar.add(this.move);

		// Help menu
		this.help = new JMenu("Help");
		this.help.setMnemonic(KeyEvent.VK_H);
		// Key bindings item
		this.instructions = new JMenuItem("Instructions");
		this.instructions.setMnemonic(KeyEvent.VK_I);
		this.instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		this.instructions.addActionListener(new InstructionsListener());
		this.help.add(this.instructions);
		this.menuBar.add(this.help);

		this.add(menuBar, BorderLayout.NORTH);

		// Game panel
		this.add(this.gamePanel, BorderLayout.CENTER);

		this.setResizable(false);
		this.addKeyListener(new InputListener());
		this.setFocusable(true);
		this.setVisible(true);

		this.instructionsDialog();
	}

	private void endGame() {
		System.exit(0);
	}

	private void instructionsDialog() {
		JOptionPane.showMessageDialog(GameWindow.this, GameWindow.HELP, GameWindow.HELP_TITLE,
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void gameOverDialog() {
		JOptionPane.showMessageDialog(GameWindow.this, GameWindow.GAMEOVER, GameWindow.GAMEOVER_TITLE,
				JOptionPane.WARNING_MESSAGE);
	}

	// Check if the game is over or the user requested a restart
	private void update() {
		if (this.gameState.isGameOver()) {
			this.gameOverDialog();
			this.gameState.newGame();
			this.gameState.setGameOver(false);
		}
		if (this.gameState.isDisplayInstructions()) {
			this.instructionsDialog();
			this.gameState.setDisplayInstructions(false);
		}
		this.gamePanel.printSight(GameWindow.this.gameState);
	}

	private abstract class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.update();
		}
	}

	private class NewGameListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.newGame();
			super.actionPerformed(e);
		}
	}

	private class SaveGameListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.quickSave();
			super.actionPerformed(e);
		}
	}

	private class LoadGameListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.quickLoad();
			super.actionPerformed(e);
		}
	}

	private class SaveAndExitListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.quickSave();
			super.actionPerformed(e);
			GameWindow.this.endGame();
		}
	}

	private class MoveUpListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.move(UP);
			super.actionPerformed(e);
		}
	}

	private class MoveLeftListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.move(LEFT);
			super.actionPerformed(e);
		}
	}

	private class MoveDownListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.move(DOWN);
			super.actionPerformed(e);
		}
	}

	private class MoveRightListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.gameState.move(RIGHT);
			super.actionPerformed(e);
		}
	}

	private class InstructionsListener extends MenuListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameWindow.this.instructionsDialog();
		}
	}

	// Takes and interprets keyboard input
	private class InputListener extends MenuListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			GameWindow.this.gameState.move(e.getKeyChar());
			GameWindow.this.update();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
}
