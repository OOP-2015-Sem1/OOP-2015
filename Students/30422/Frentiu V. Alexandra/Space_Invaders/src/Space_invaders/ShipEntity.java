package Space_invaders;
public class ShipEntity extends Entity {
private Game g;
public ShipEntity(Game g,String ref, int x,int y)
{
	super(ref,x,y);
	this.g = g;
	
}
public void move(long delta)
{//not to go out of screen
	if(dx<0 && x<10)
		return ;
	if((dx>0) && (x>750))
		return;
	super.move(delta);
}//if player ship collided with something
	public void collidedWith(Entity other) {
	
		//if colission with alien  then notify player is dead
		if(other instanceof AlienEntity)
			g.notifyDeath();
	}
	

}
