package theGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import display.Menu;
import display.Score;
import display.Window;
import inputDevices.KeyInput;
import objects.Ball;
import objects.ObjectID;
import objects.Player;

public class MainGame extends Canvas implements Runnable {

	private static final long serialVersionUID = -4040703231116492000L;

	// Dimensions of the window
	public static final int WIDTH = 980;
	public static final int HEIGHT = 640;
	public static boolean paused = false;
	public static boolean soundMute = false;

	private Thread myThread;
	private Handler handler;
	private Score score;
	private Menu menu;

	boolean ifRunning = false;

	public enum STATE {
		Menu, Game, Multiplayer
	};

	public STATE gameState = STATE.Menu;

	public MainGame() {
		new Window(WIDTH, HEIGHT, "Air Hockey", this);

		handler = new Handler();
		menu = new Menu(this);

		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(menu);

		score = new Score();

		handler.addObject(new Ball(MainGame.WIDTH / 2 - 50, MainGame.HEIGHT / 2 - 50, ObjectID.Ball));
		handler.addObject(new Player(50, MainGame.HEIGHT / 2 - 70, ObjectID.Player));
		handler.addObject(new Player(MainGame.WIDTH - 170, MainGame.HEIGHT / 2 - 70, ObjectID.Player2));

	}

	public synchronized void startGame() {
		myThread = new Thread(this);
		myThread.start();
		ifRunning = true;
	}

	public synchronized void stopGame() {
		try {
			myThread.join();
			ifRunning = false;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// GameLoop took from a tutorial
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		// int frames = 0;
		while (ifRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (ifRunning)
				render();
			// frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: " + frames);
				// frames=0;
			}
		}
		stopGame();
	}

	private void tick() {
		if (gameState == STATE.Game || gameState == STATE.Multiplayer) {
			if (!paused) {
				handler.tick();
				score.tick();
			}

		} else if (gameState == STATE.Menu) {
			menu.tick();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics graph = bs.getDrawGraphics();
		if (gameState == STATE.Game || gameState == STATE.Multiplayer) {
			graph.setColor(Color.white);
		} else {
			graph.setColor(Color.black);
		}
		graph.fillRect(0, 0, WIDTH, HEIGHT);

		if (gameState == STATE.Game || gameState == STATE.Multiplayer) {
			drawCenterCircle(graph);
			if (soundMute) {
				drawMuteText(graph);
			}
			handler.render(graph);
			score.render(graph);
		}

		if (paused) {
			drawPauseText(graph);
		}

		else if (gameState == STATE.Menu) {
			menu.render(graph);
			drawControlIndications(graph);
		}

		graph.dispose();
		bs.show();
	}

	private void drawCenterCircle(Graphics graph) {
		// TODO Auto-generated method stub
		graph.setColor(Color.LIGHT_GRAY);
		graph.drawLine(WIDTH / 2 - 20, 0, WIDTH / 2 - 20, HEIGHT);
		graph.setColor(Color.LIGHT_GRAY);
		graph.fillRoundRect(WIDTH / 2 - 121, HEIGHT / 2 - 130, 200, 200, 200, 200);
	}

	private void drawControlIndications(Graphics graph) {
		// TODO Auto-generated method stub
		Font indicationsfnt = new Font("Arial", 1, 10);
		graph.setColor(Color.WHITE);
		graph.setFont(indicationsfnt);
		graph.drawString("Player1:  W - up, A - left, S - down, D - right", 30, 30);
		graph.drawString("Player2:  UP - up, LEFT - left, DOWN - down, RIGHT - right", 30, 60);
		graph.drawString("Pause game:  press P", 30, 90);
		graph.drawString("Exit game:  press Exit", 30, 120);
		graph.drawString("Mute sound:  press M", 30, 150);
	}

	private void drawPauseText(Graphics graph) {
		// TODO Auto-generated method stub
		Font pausefnt = new Font("Arial", 1, 70);
		graph.setColor(Color.BLACK);
		graph.setFont(pausefnt);
		graph.drawString("PAUSED", 320, 310);
	}

	private void drawMuteText(Graphics graph) {
		// TODO Auto-generated method stub
		// Font pausefnt = new Font("Arial", 1, 15);
		graph.setColor(Color.BLACK);
		// graph.setFont(pausefnt);
		graph.drawString("MUTE", 20, 550);
	}

	public static float posRelToMargins(float var, float min, float max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainGame();
	}

}
