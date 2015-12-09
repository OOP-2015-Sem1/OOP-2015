package theseal.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import theseal.game.Game;
import theseal.game.GameState;

public class GameFrame extends JFrame implements KeyListener, MouseListener {
	private static final long serialVersionUID = 8747250271505267311L;

	private int screenWidth;
	private int screenHeight;
	private int frameWidth;
	private int frameHeight;

	private Game game;
	private GamePanel gamePanel;

	public GameFrame(String title) {
		super(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = getToolkit().getScreenSize();
		this.screenWidth = dim.width;
		this.screenHeight = dim.height;

		this.frameWidth = this.screenWidth;
		this.frameHeight = this.screenHeight;
		setSize(frameWidth, frameHeight);

		setLocation((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2);

		this.game = new Game();

		this.setLayout(new FlowLayout());
		this.gamePanel = new GamePanel(this.game, this.frameWidth, this.frameHeight);
		setContentPane(this.gamePanel);
		this.gamePanel.repaint();

		addKeyListener(this);
		setVisible(true);
	}

	private void runTick() {
		GameState gameState = this.game.run();
		
		this.gamePanel.updateEntityPanels(this.game);
		this.gamePanel.setState(gameState);
		this.gamePanel.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		} else {
			this.runTick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.runTick();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
