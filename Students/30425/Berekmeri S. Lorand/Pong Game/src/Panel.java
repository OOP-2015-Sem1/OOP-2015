import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel implements ActionListener, KeyListener {
	Player1 player1 = new Player1();
	Player2 player2 = new Player2();
	Ball ball = new Ball();

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
		//ball.checkCollisionwith(player2);
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Pong.gui_WIDTH , Pong.gui_HEIGHT);
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
			player1.sety_speed(-7);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player1.sety_speed(7);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			player2.sety_speed(-7);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			player2.sety_speed(7);
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