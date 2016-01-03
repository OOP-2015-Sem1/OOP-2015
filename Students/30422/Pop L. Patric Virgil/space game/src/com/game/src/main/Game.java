package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;


public class Game extends Canvas implements Runnable {

	private static final int SPEED = 3;
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	private static final int VELOCITY = 5;
	public static int HEALTH = 100*2; 
	public final String TITLE = "2D Space Game";

	private boolean running = false;
	private Thread thread;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
	private BufferedImage spriteSheet = null;
	
	private BufferedImage background=null;
	
	private boolean is_shooting = false;
	
	private int enemy_count=4;
	private int enemy_killed=0;
	

	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}

	private Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	// temp
	private BufferedImage player;
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	
	
	public static enum STATE{
		MENU,
		GAME
		
	};
	public static STATE State=STATE.MENU;
	

	
	
	
	public void init() {
		requestFocus();
		BufferImageLoader loader = new BufferImageLoader();
		try {

			spriteSheet = loader.loadImage("/sprite_sheet1.png");
			background=loader.loadImage("/background1.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		addKeyListener(new KeyImput(this));
		
		tex = new Textures(this);

		
		c=new Controller(this,tex);
		p = new Player(200, 200,tex,this,c);
		menu=new Menu();
		this.addMouseListener(new MouseImput());
		
		ea=c.getEntityA();
		eb=c.getEntityB();
		
		c.createEnemy(enemy_count);
		// SpriteSheet ss = new SpriteSheet(spriteSheet);
		// player= ss.grabImage(1, 1, 32, 32);
	}

	private synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;
		running = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(1);

	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int update = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				update++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(update + " Ticks, Fps " + frames);
				update = 0;
				frames = 0;
			}

			// System.out.println("WORKING");

		}
		stop();
	}

	private void tick() {
		
		if(State==STATE.GAME){
			
		
		p.tick();
		c.tick();
		}
		if(enemy_killed ==enemy_count){
			enemy_count+=2;
			enemy_killed=0;
		c.createEnemy(enemy_count);
		}

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {

			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		
		g.drawImage(background, 0, 0,null);
		if(State==STATE.GAME){
			
		
		p.render(g);
		c.render(g);
		
		g.setColor(Color.gray);
		g.fillRect(5, 5, 200, 50);
		g.setColor(Color.green);
		g.fillRect(5, 5, HEALTH, 50);
		g.setColor(Color.white);
		g.drawRect(5, 5, 200, 50);
		
		}else if(State==STATE.MENU){
			menu.render(g);
			
		}
		//this.isOpaque();
		//this.setBackground(Color.BLUE);
		// g.drawImage(player,100, 100,this);
		g.dispose();
		bs.show();

	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if(State==STATE.GAME){
		if (key == KeyEvent.VK_RIGHT) {
		 
				p.setVelX(SPEED);
		

		} else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(-SPEED);

		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(SPEED);

		} else if (key == KeyEvent.VK_UP) {

			p.setVelY(-SPEED);
		} else if(key==KeyEvent.VK_SPACE && !is_shooting){
			is_shooting=true;
			c.addEntity(new Bullet(p.getX(),p.getY(),tex,this,c));
		}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			 
			p.setVelX(0);
	

	} else if (key == KeyEvent.VK_LEFT) {
		p.setVelX(0);

	} else if (key == KeyEvent.VK_DOWN) {
		p.setVelY(0);

	} else if (key == KeyEvent.VK_UP) {

		p.setVelY(0);
	} else if(key == KeyEvent.VK_SPACE){
		is_shooting=false;
	}

	}

	public static void main(String args[]) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		Color col=Color.BLUE;
		frame.setBackground(col);
		
		frame.setBackground(Color.BLUE);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		

		game.start();

	}
	

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

}
