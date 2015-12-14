import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameEngine extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int WIDTH = 320;
	public final int HEIGHT = 240;
	public final int SCALE = 2;
	public final String TITLE = "Pac-Man!";
	
	private boolean Running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private Pacman p;
	public static Map map;
	private Graphics g;
	private int imageIndex = 0;
	private int count;
	private int index = 0;
	private int mode = 0;
	
	public void animationSpeed(int speed){
		count++;
		if(count == speed){
			count = 0;
			nextFrame(mode);
		}
	}
	
	public void nextFrame(int mode){
		if(mode == 0){
		if(index == 0) imageIndex = 0;
		if(index == 1) imageIndex = 1;
		if(index == 2) imageIndex = 2;
		if(index == 3) imageIndex = 3;
		if(index == 4) imageIndex = 4;
		if(index == 5) imageIndex = 5;
		
		index ++;
		
		if(index > 6) index = 0;
		}
		
		if(mode == 1){
			if(index == 6) imageIndex = 6;
			if(index == 7) imageIndex = 7;
			if(index == 8) imageIndex = 8;
			if(index == 9) imageIndex = 9;
			if(index == 10) imageIndex = 10;
			if(index == 11) imageIndex = 11;
			
			index ++;
			
			if(index > 12) index = 6;
		}
		
		if(mode == 2){
			
			if(index == 12) imageIndex = 12;
			if(index == 13) imageIndex = 13;
			if(index == 14) imageIndex = 14;
			if(index == 15) imageIndex = 15;
			if(index == 16) imageIndex = 16;
			if(index == 17) imageIndex = 17;

			
			index ++;
			
			if(index > 18) index = 12;
		}
		
		if(mode == 3){
			
			if(index == 18) imageIndex = 18;
			if(index == 19) imageIndex = 19;
			if(index == 20) imageIndex = 20;
			if(index == 21) imageIndex = 21;
			if(index == 22) imageIndex = 22;
			if(index == 23) imageIndex = 23;
			
			index ++;
			
			if(index > 24) index = 18;
		}
		
		
	}
	
	public void init(){
		requestFocus();
		addKeyListener(new Input(this));
		p = new Pacman(288,320);
		map = new Map("Map/map.png");
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			mode = 0;
			index = 0;
			p.setVelY(0);
			p.setVelX(2.4);

		}else if(key == KeyEvent.VK_LEFT){
			mode = 1;
			index = 6;
			p.setVelY(0);
			p.setVelX(-2.4);
		}else if(key == KeyEvent.VK_UP){
			mode = 2;
			index = 12;
			p.setVelX(0);
			p.setVelY(-2.4);
		}else if(key == KeyEvent.VK_DOWN){
			mode = 3;
			index = 18;
			p.setVelX(0);
			p.setVelY(2.4);
		}
	}
	
	public void keyReleased(KeyEvent e){
		

	}

	public synchronized void start(){
		if(Running) return;
		
		Running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!Running) return;
		
		Running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
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
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(Running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1){
				tick();
				updates++;
				delta--;
			}	
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >1000){
				timer+= 1000;
				System.out.println(updates + " Ticks, FPS " +frames);
				updates=0;
				frames=0;
			}
		}
		stop();
		
	}
	
	private void tick(){
		p.tick();
		animationSpeed(3);
		
	}
	
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		//////////////////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		p.render(g,p.pacman[imageIndex]);
		map.render(g);
		
		///////////////////////////////////
		g.dispose();
		bs.show();
		
	}
}
