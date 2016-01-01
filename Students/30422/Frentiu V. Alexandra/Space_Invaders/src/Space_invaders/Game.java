package Space_invaders;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;






public class Game extends Canvas  {

	private BufferStrategy strategy;
	private boolean gameRunning = true;
	private ArrayList entities = new ArrayList();
	private ArrayList removeList = new ArrayList();
	private Entity ship;
	private double moveSpeed = 300;//speed of ship of player
	private long lastFire = 0;//time when alien last fired a shot 
	private long lastAlienFire =0;//same like lastFire only in differnt method
	private long firingInterval =500;
	private int alienLeftCount;
	private long alienLastTimeFired = 0;//for random aliens they must fire only after 2 seconds specified time
	private String message = " ";
	private boolean waitingForKeyPress = true;//waiting for game until key pressed at beginning
	protected boolean leftPressed = false;
	protected boolean rightPressed  = false;
	protected boolean firePressed = false;//true is firing
	private int count =0;
	private boolean logicRequiredThisLoop = false;
	private ArrayList ArrayAlien;
	
	public Game()
	{
		JFrame f = new JFrame("Space invaders");
		ArrayAlien = new ArrayList();
		JPanel panel = (JPanel) f.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		setBounds(0,0,800,600);
		panel.add(this);
		f.pack();
		f.setResizable(false);
		f.setVisible(true);
		setIgnoreRepaint(true);
	//it works without but it's better to have
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}
	);
	
	
		addKeyListener( new KeyInputHandler());//we can respond to key pressed commands
	requestFocus();
	// create the buffering strategy which will allow AWT
	// to manage our accelerated graphics
	createBufferStrategy(2);
	strategy = getBufferStrategy();
	initEntities();
	}
	protected void startGame(){
		entities.clear();
		initEntities();
		leftPressed = false;
		rightPressed = false;
		firePressed= false;//if currently running they should be off
	}
	private void initEntities()
	{
		ship = new ShipEntity(this,"ship.gif",370,550);
		entities.add(ship);//enties is arrayList so we add ships and aliens and all sprites basically inside
	
		//create aliens row blocks and spaced //currently 12x5
		alienLeftCount = 0;
		for(int i = 0;i<5;i++)
		{
			for(int j = 0;j<12;j++)
			{//make those aliens stand next to each other
				 AlienEntity aliens= new AlienEntity(this,"alien.gif",100+50*j,i*30+50);
				
			entities.add(aliens);
			ArrayAlien.add(aliens);
			alienLeftCount++;
			
			}
		}
	}
	//kind of recommended
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	public void removeEntity(Entity entity)
	{
		removeList.add(entity);
	}
	
	

	
	public void notifyDeath()
	{
		message = "oh no.You failed. Do you want to try again?";
		waitingForKeyPress = true;
		count = 0;
	}
	
	public void notifyWin() 
	{
		message = "Well done! You win!";
		waitingForKeyPress = true;
	}
	
	public void notifyAlienKilled() 
	{
		alienLeftCount--;
		if(alienLeftCount == 0)
			notifyWin();
			
			
		//speed up aliens like in real games// when one dies others speed up
		for(int i = 0;i<entities.size();i++)
		{
			Entity entity = (Entity)entities.get(i);
			if(entity instanceof AlienEntity)//speed up only aliens by 2%
				entity.setHorizontalMovement(entity.getHorizontalMovement() *1.02);
		}
	}
	public void shipTryFire()
	{
		// we can fire after we waited certain time not consecutive shots, like in games
		if(System.currentTimeMillis() - lastFire <firingInterval)
			return;
		//after having waited long enough we create shot entity and record time
		lastFire = System.currentTimeMillis();
	
		ShotEntity shot = new ShotEntity(this,"shot.gif",ship.getX()+10,ship.getY()-30);
		entities.add(shot);
		
	}
	public void alienTryFire() 
	{
		// we can fire after we waited certain time not consecutive shots...exactly like in real  like in games
				if(System.currentTimeMillis() - lastAlienFire <firingInterval)
					return;
				//after having waited long enough we create shot entity and record time
				lastAlienFire = System.currentTimeMillis();
				Iterator i3 = ArrayAlien.iterator();
				//generated  shots from different aliens from ArrayAlien
		        Random generator = new Random();
		    count++;

					while (i3.hasNext()) {
					
			             AlienEntity a =  (AlienEntity) i3.next();		
			          
	           int  shot = generator.nextInt(170);
	           
	         
	        
	           
	           if(count == shot) //I honestly do not understand why it works like this but this way it randomizes how i want only
	           { if(System.currentTimeMillis()- alienLastTimeFired >2000)
	            	//create enemy shot and random alien can shoot only at 2 seconds interval
	            	 {
	            	EnemyShot es = new EnemyShot(this,"shot.gif",a.getX(),a.getY()+30);
	         		entities.add(es);
	         		alienLastTimeFired = System.currentTimeMillis();
	            	 }    	
	            }
	            						}
	        
			 	
		   	}
		
		
		//game loop running 
	//all game moving entities - drawing them and checking input
	public void gameLoop() 
	{//keep looping until game ends
		long lastLoopTime = System.currentTimeMillis();
		while(gameRunning)
			// work out how long it s been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
		{
			long deltaT =System.currentTimeMillis() - lastLoopTime;
		lastLoopTime = System.currentTimeMillis();
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,800,600);
		// cycle asking each entity to move itself
		if(!waitingForKeyPress)
		{
			for(int i = 0;i<entities.size();i++)
			{
				Entity entity = (Entity) entities.get(i);
				entity.move(deltaT);
			}
			
		}
		//draw all entities in game
		for(int i = 0;i<entities.size();i++){
			Entity entity = (Entity) entities.get(i);
			entity.draw(g);
		}
		//collision detection in entities
		//noitfy both if collision detected
		for(int i = 0;i<entities.size();i++)
		{for(int j = i+1;j< entities.size();j++)
		{
			Entity e1 = (Entity) entities.get(i);
			Entity e2 = (Entity) entities.get(j);
			if(e1.collidesWith(e2))
			{
				e1.collidedWith(e2);
				e2.collidedWith(e1);
			}
		}
		}
		//remove entities marked for clear up
		entities.removeAll(removeList);
		removeList.clear();
		// if a game event has indicated that game logic should
					// be resolved, cycle round every entity requesting that
					// their personal logic should be considered.
					if (logicRequiredThisLoop) {
						for (int i=0;i<entities.size();i++) {
							Entity entity = (Entity) entities.get(i);
							entity.doLogic();
						}
						
						logicRequiredThisLoop = false;
					}
					//if waiting for any key press then draw the current message
					if(waitingForKeyPress)
					{
						g.setColor(Color.WHITE);
						g.drawString(message, (800-g.getFontMetrics().stringWidth(message))/2, 250);
						g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
					}
					// completed drawing so clear graphics for better maintenance
					g.dispose();
					strategy.show();
					//update ship movement
					ship.setHorizontalMovement(0);
					if((leftPressed) && (!rightPressed))
						ship.setHorizontalMovement(-moveSpeed);
						else if((rightPressed) && (!leftPressed))
								ship.setHorizontalMovement(moveSpeed);
						//for shooting fire into enemy
						if(firePressed )
						{
							shipTryFire();
						}
						
							alienTryFire();
							
		}
		
	}
	
	
//inner class implementation 
	public class KeyInputHandler extends KeyAdapter{
		//key presses while waiting at beginning for key pressed
		private int pressCount = 1;
		
		public void keyPressed(KeyEvent e)
		{//we do not want to do anything with key pressed
			if(waitingForKeyPress)
				return;
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = true;
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)
	rightPressed = true;

	if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		firePressed = true;
		}

		}
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}
			//if (e.getKeyCode() == KeyEvent.VK_A ){
			//	Afire = false;
			//}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
			
		}
		//notification when key typed
	public void keyTyped(KeyEvent e) {
		// if we're waiting for a "any key" type then
		// check if we've recieved any recently. We may
		// have had a keyType() event from the user releasing
		// the shoot or move keys, hence the use of the "pressCount"
		// counter.
		if (waitingForKeyPress) {
			if (pressCount == 1) {
				// since we've now recieved our key typed
				// event we can mark it as such and start 
				// our new game
				waitingForKeyPress = false;
				startGame();
				pressCount = 0;
			} else {
				pressCount++;
			}
		}
		
		// if we hit escape, then quit the game
		if (e.getKeyChar() == 27) {
			System.exit(0);
		}
	}



	}

	
	public static void main(String args[]) throws InterruptedException
	{
		Game g = new Game();
		g.gameLoop();
	
	}

}
