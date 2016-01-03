package Space_invaders;

public class EnemyShot extends Entity {
	private double moveSpeed = 300;
	private Game g;
	private boolean used = false;
	
	
	
	public EnemyShot(Game g, String sprite, int x,int y)
	{
		super(sprite,x,y);
		this.g = g;
		dy = moveSpeed;
	
	}
	public void move(long delta)
	{
		super.move(delta);
		if(y>600)//shooting off screen
			g.removeEntity(this);
		

}
	@Override
	public void collidedWith(Entity other) {
		if(other instanceof ShipEntity)
		{
			g.removeEntity(this);
			g.removeEntity(other);
		
		g.notifyDeath();
		
		}
	}
	
	}
	


