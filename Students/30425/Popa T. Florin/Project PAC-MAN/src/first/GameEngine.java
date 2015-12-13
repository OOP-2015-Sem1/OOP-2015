package first;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class GameEngine extends Canvas implements Runnable  {

	
	public static final int WIDTH = 640, HEIGHT = 480;
	private Thread thread;
	private boolean running = false;
	
	public GameEngine(){
		new Pacman(WIDTH,HEIGHT,"PACMAN",this);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop(){
		try{
		thread.join();
		running = false;
	}catch(Exception e){
		e.printStackTrace();
	 }
	}
	
	private void tick(){
		
	}
	
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.dispose();
		bs.show();
	}

	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();		
		
	}
	
}


