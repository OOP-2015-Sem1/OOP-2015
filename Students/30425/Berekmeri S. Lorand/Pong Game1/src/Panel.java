import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel implements ActionListener, KeyListener {
	Player player1 = new Player(0, Pong.gui_HEIGHT /2 - 50);
	Player player2 = new Player(Pong.gui_WIDTH - 20, Pong.gui_HEIGHT / 2 - 50);
	Ball ball = new Ball();
	private int w = -7;
	private int z = 7;
	
	public Panel() {
		Timer time = new Timer(50, this);
		time.start();
		this.addKeyListener(this);
		this.setFocusable(true);
	}

	public void update() {
		player1.update();
		player2.update();
		ball.update();
		ball.checkCollisionwith(player1);
		ball.checkCollisionwith(player2);
	}
	private void drawScore(Graphics g) {
		String s;
		Font myfont = new Font("Helvetica", Font.BOLD, 14);
		g.setFont(myfont);
		g.setColor(new Color(235, 29, 77));
		s = "Score: ";
		g.drawString(s, Pong.gui_WIDTH / 2, Pong.gui_HEIGHT);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Pong.gui_WIDTH , Pong.gui_HEIGHT);
		//drawScore(g);
		player1.paint(g);
		player2.paint(g);
		ball.paint(g);
	
	}

	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player1.sety_speed(w);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player1.sety_speed(z);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player2.sety_speed(w);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player2.sety_speed(z);
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
			player1.sety_speed(0);
		}
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
			player2.sety_speed(0);
		}
	} 

	public void keyTyped(KeyEvent e) {

	}
	
}