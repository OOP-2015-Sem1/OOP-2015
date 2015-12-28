import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GameEngine extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public final String TITLE = "Pac-Man!";
	
	private boolean Running = false;
	private Thread thread;
	BufferedImageLoader load = new BufferedImageLoader();
	private BufferedImage map = load.loadImage("/Map/map.png");
	
	private Pacman p;
	private Ghosts ghost;
	private Menu menu;
	
	private Graphics g;
	
	private int frame=0;
	private int frameGhost = 0;
	
	private int count;
	private int countGhost;
	
	public enum STATE {
		MENU,
		GAME,
		OVER
	};
	
	public static STATE State = STATE.MENU;

	public void init(){
		requestFocus();
		addKeyListener(new Input(this));
		addMouseListener(new MouseInput());
		p = new Pacman();
		ghost = new Ghosts();
		menu = new Menu();
		
	}
	
	public void keyPressed(KeyEvent e){
		
		p.setNextDir(e.getKeyCode());
		
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
			
			try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
	
	private void PacSpeed(int speed){
		count++;
		if(count==speed){
			frame++;
			if(frame>5){
				frame = 0;
			}
			count =0;
			
		}
	}
	
	private void GhostSpeed(int speed){
		countGhost++;
		if(countGhost == speed){
			frameGhost++;
			if(frameGhost>2){
				frameGhost = 0;
			}
			countGhost = 0;
		}
	}
	
	private void tick(){
		
		if(State == STATE.GAME){
		PacSpeed(2);
		GhostSpeed(20);
		p.tick();
		ghost.tick();
		}
	}
	
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
////////////////////////////////////////////
		if(State == STATE.GAME){
			
		g.drawImage(map, 0, 0, 576, 508, this);
		p.render(g,p.pacman.getSubimage(frame*32, (p.getCurDir()-37)*32, 32, 32),p.pill);
		ghost.render(g, ghost.ghost.getSubimage(frameGhost*32, 0, 32, 32),ghost.red.getSubimage(frameGhost*32, 0, 32, 32),ghost.purp.getSubimage(frameGhost*32, 0, 32, 32));
		
		}else if(State == STATE.MENU){
			menu.renderMain(g);
		}else if(State == STATE.OVER){
			menu.renderOver(g);
		}
////////////////////////////////////////////
		g.dispose();
		bs.show();
		
	}
}
