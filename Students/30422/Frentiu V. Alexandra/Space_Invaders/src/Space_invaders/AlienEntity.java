package Space_invaders;

public class AlienEntity extends Entity{
	//speed at which alien moves
	private double moveSpeed =75;
	private Game g;
	
	public AlienEntity(Game g, String ref, int x, int y)
	{
		super(ref,x,y);
		this.g= g;
		dx = -moveSpeed;
	}
	public void move(long delta)
	{
		// if we have reached the left hand side of the screen and
				// are moving left then request a logic update 
		if (dx<0 && x<10)
			g.updateLogic();
		if(dx>0 && x>750)
			g.updateLogic();
		super.move(delta);
	}
	//swap and move down gives better gaming look
	public void doLogic()
	{
		dx = -dx;
		y+=10;
		//if reached bottom  screen than player dies
		if(y>570)
			g.notifyDeath();
	}
	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub
		
	}
}
