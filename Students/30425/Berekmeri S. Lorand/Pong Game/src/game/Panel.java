package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import entities.Ball;
import entities.Block;
import entities.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player1 = new Player(0, Game.gui_HEIGHT / 2 - 50);
	Player player2 = new Player(Game.gui_WIDTH - 20, Game.gui_HEIGHT / 2 - 50);
	Block block1 = new Block(360, 160,1);
	Block block2 = new Block(460, 320,-1);
	Block block3=new Block(260, 320, 1);
	Block block4=new Block(540,160, -1);
	Ball ball;
	private final int SPEED = 6;
	private boolean gameRunning = false;
	private boolean startScreen = true;
	private int level = 1;
	int winner;

	public Panel() {
		Timer time = new Timer(10, this);
		time.start();
		ball = new Ball(3);
		player1 = new Player(0, Game.gui_HEIGHT / 2 - 50);
		player2 = new Player(Game.gui_WIDTH - 20, Game.gui_HEIGHT / 2 - 50);
		if (level == 2) {
			ball = new Ball(9);

		}
		if (level == 3) {
			ball = new Ball(15);
		}
		this.addKeyListener(this);
		this.setFocusable(true);
	}

	public void update() {
		checkGameOver();
		player2.update();
		player1.update();
		ball.update();
		ball.checkCollisionwith(player1);
		ball.checkCollisionwith(player2);
		if (level != 1) {
			ball.checkCollisionwith(block1);
			ball.checkCollisionwith(block2);
		}
		if(level==3){
			ball.checkCollisionwith(block3);
			ball.checkCollisionwith(block4);
		//	block1.update();
		//	block2.update();
		}

	}

	private void checkGameOver() {
		if (Math.abs(player1.getScore() - player2.getScore()) > 2) {
			gameRunning = false;
		}
		if (player1.getScore() > player2.getScore()) {
			winner = 1;
		} else {
			winner = 2;
		}
	}

	private void drawScore(Graphics g) {
		String s;
		Font myfont = new Font("Arial", Font.ITALIC, 18);
		g.setFont(myfont);
		g.setColor(new Color(255, 255, 255));
		s = player1.getName() + ": " + player1.getScore();
		g.drawString(s, 17, 30);
		s = player2.getName() + ": " + player2.getScore();
		g.drawString(s, Game.gui_WIDTH / 2, 30);
	}

	public void paintComponent(Graphics g) {

		if (startScreen && !gameRunning) {
			drawStartScreen(g);
		} else {
			if (gameRunning) {
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, Game.gui_WIDTH, Game.gui_HEIGHT);
				if (level == 1) {
					drawScore(g);
					player1.paint(g);
					player2.paint(g);
					ball.paint(g);
				}
				if (level == 2) {
					drawScore(g);
					player1.paint(g);
					player2.paint(g);
					ball.paint(g);
					block1.paint(g);
					block2.paint(g);
				}
				if (level == 3) {
					drawScore(g);
					player1.paint(g);
					player2.paint(g);
					ball.paint(g);
					block1.paint(g);
					block2.paint(g);
					block3.paint(g);
					block4.paint(g);
				}
			} else {
				drawExitScreen(g);
			}
		}
	}

	public void drawStartScreen(Graphics g) {
		String s = "DEW THE PONG";
		Font myFont = new Font("Arial", Font.BOLD, 48);
		g.setColor(new Color(0, 0, 68));
		g.fillRect(0, 0, Game.gui_WIDTH, Game.gui_HEIGHT);
		
		ImageIcon ii = new ImageIcon("final.png");
		Image image = ii.getImage();
		g.drawImage(image, Game.gui_WIDTH - image.getWidth(null), Game.gui_HEIGHT - image.getHeight(null), this);
		
		g.setFont(myFont);
		g.setColor(Color.WHITE);
		g.drawString(s, 50, 100);
		myFont = new Font("Arial", Font.ITALIC, 30);
		g.setFont(myFont);
		
		s = "Start - S";
		g.drawString(s, 170, 170);
		s = "Choose Level : Easy - E";
		g.drawString(s, 170, 200);
		s = "Medium - M";
		g.drawString(s, 375, 230);
		s = "Hard - H";
		g.drawString(s, 375, 260);
		s = "Players - P";
		g.drawString(s, 170, 290);
		s = "Exit - Esc";
		g.drawString(s, 170, 325);
		
		String s1 = "Instructions: For left: W-UP, S-DOWN. For right: UP ARROW, DOWN ARROW";
		Font s11 = new Font("Arial", Font.BOLD, 15);
		g.setFont(s11);
		g.setColor(Color.WHITE);
		g.drawString(s1, 30, 540);
		s1= "Player who reach to the difference >2 wins the game!";
		g.drawString(s1, 30, 560);

	}

	public void setPlayersNames() {
		String s = (String) JOptionPane.showInputDialog(null, "Choose Player 1's name", "Names",
				JOptionPane.PLAIN_MESSAGE, null, null, null);
		player1.setName(s);
		s = (String) JOptionPane.showInputDialog(null, "Choose Player 2's name", "Names", JOptionPane.PLAIN_MESSAGE,
				null, null, null);
		player2.setName(s);
	}

	public void actionPerformed(ActionEvent e) {
		update();
		if (ball.getUpdate1()) {
			ball.setBallColor(Color.BLACK);
			repaint();
			updateScore(player1);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			ball.setBallColor(Color.RED);
			ball.setUpdate1(false);
		}
		if (ball.getUpdate2()) {
			ball.setBallColor(Color.BLACK);
			repaint();
			updateScore(player2);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
			ball.setBallColor(Color.RED);
			ball.setUpdate2(false);
		}
		repaint();
	}

	private void drawExitScreen(Graphics g) {

		g.setColor(new Color(0, 0, 68));
		g.fillRect(0, 0, Game.gui_WIDTH, Game.gui_HEIGHT);
		ImageIcon ii = new ImageIcon("final.png");
		Image image = ii.getImage();
		g.drawImage(image, Game.gui_WIDTH - image.getWidth(null), Game.gui_HEIGHT - image.getHeight(null), this);

		String s = "Press E to exit.";
		Font small = new Font("Arial", Font.ITALIC, 40);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(s, 30, 550);
		if (winner == 1) {
			s = "The DEW winner is: " + player1.getName();
		} else {
			s = "The DEW winner is: " + player2.getName();
		}
		g.drawString(s, 30, 350);
	}

	public void keyPressed(KeyEvent e) {
		if (!gameRunning && !startScreen) {
			if (e.getKeyCode() == KeyEvent.VK_E) {
				System.exit(0);
			}
		}
		if (!gameRunning && startScreen) {
			if (e.getKeyCode() == KeyEvent.VK_S) {
				gameRunning = true;
				startScreen = false;
				player1.setScore(0);
				player2.setScore(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_E) {
				level = 1;
			}
			if (e.getKeyCode() == KeyEvent.VK_M) {
				level = 2;
			}
			if (e.getKeyCode() == KeyEvent.VK_H) {
				level = 3;
			}
			if (e.getKeyCode() == KeyEvent.VK_P) {
				setPlayersNames();
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player1.sety_speed(-SPEED);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player1.sety_speed(SPEED);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player2.sety_speed(-SPEED);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player2.sety_speed(SPEED);
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
			player1.sety_speed(0);
		}
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
			player2.sety_speed(0);
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	public static void updateScore(Player player) {
		player.setScore(player.getScore() + 1);
	}

}