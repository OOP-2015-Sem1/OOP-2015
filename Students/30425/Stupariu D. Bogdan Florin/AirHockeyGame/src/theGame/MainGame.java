package theGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class MainGame extends Canvas implements Runnable {

	private static final long serialVersionUID = -4040703231116492000L;

	// Dimensions of the window
	public static final int WIDTH = 980;
	public static final int HEIGHT = 640;

	private Thread myThread;
	boolean ifRunning = false;
	private Handler handler;
	private Score score;

	GameObjects player = new Player(50, HEIGHT / 2 - 70, ObjectID.Player);
	Ball ball = new Ball(WIDTH / 2 - 50, HEIGHT / 2 - 50, ObjectID.Ball);

	public MainGame() {
		new Window(WIDTH, HEIGHT, "Air Hockey", this);
		
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		score = new Score();
			
		handler.addObject(new Ball(WIDTH / 2 - 50, HEIGHT / 2 - 50, ObjectID.Ball));
		handler.addObject(new Player(50, HEIGHT / 2 - 70, ObjectID.Player));
		handler.addObject(new Player2(WIDTH - 170, HEIGHT / 2 - 70, ObjectID.Player2));
		
		

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
		handler.tick();
		score.tick();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics graph = bs.getDrawGraphics();

		graph.setColor(Color.white);
		graph.fillRect(0, 0, WIDTH, HEIGHT);

		
		graph.setColor(Color.LIGHT_GRAY);
		graph.drawLine(WIDTH / 2 - 20, 0, WIDTH / 2 - 20, HEIGHT);
		graph.setColor(Color.LIGHT_GRAY);
		graph.fillRoundRect(WIDTH / 2 - 121, HEIGHT / 2 - 130, 200, 200, 200, 200);
	
		graph.fillRoundRect(WIDTH / 2 - 101, HEIGHT / 2 - 110, 160, 160, 160, 160);
	
		handler.render(graph);
		
		score.render(graph);

		graph.dispose();
		bs.show();
	}

	public static int posRelToMargins(int var, int min, int max) {
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
