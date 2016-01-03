package Space_invaders;
//shot fired by player ship
public class ShotEntity extends Entity{
	
		//basically vertical speed
	
		private double moveSpeed = -300;
	private Game g;
	//false means it did not hit anything... true if hits alien
	private boolean used = false;
	
	
	
	public ShotEntity(Game g, String sprite, int x,int y)
	{
		super(sprite,x,y);
		
		this.g = g;
		dy = moveSpeed;
	}
	public void move(long delta)
	{
		super.move(delta);
		if(y<-100)//shooting off screen
			g.removeEntity(this);
		
		
	}
//if shot collied with another entity/alien
	public void collidedWith(Entity other)  {
		if(used)//if we already hit we do not want double hits
			return;
		//kill alien if hit
		if(other instanceof AlienEntity)
		{
					g.removeEntity(this);
		      g.removeEntity(other);
		
		g.notifyAlienKilled();
		used = true;
	}
	}
	

}
