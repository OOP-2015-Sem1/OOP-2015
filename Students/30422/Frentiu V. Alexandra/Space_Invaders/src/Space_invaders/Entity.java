package Space_invaders;
import java.awt.Graphics;
import java.awt.Rectangle;

// entity = all elements in game
// i used double for accuracy, as I read it's better than int
public abstract class Entity {
	//x,y coordinates of entity
	protected double x;
	protected double y;
	protected Sprite sprite;
	//current speed in pix/sec
	protected double dx,dy;
	//for current entity in collisions resolution
	private Rectangle me = new Rectangle();
//for other entites in colissions
	private Rectangle him = new Rectangle();
	
	public Entity(String ref,int x,int y)
{
		
	this.sprite = SpriteStore.get().getSprite(ref);
	this.x = x;
	this.y = y;
}
	public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (delta * dx) / 1000;
	y += (delta * dy) / 1000;
	}
	//set horizontal,vertical  speed 
	public void setHorizontalMovement(double dx)
	{
		this.dx = dx;
	}
	public void setVerticalMovement(double dy)
	{
		this.dy = dy;
	}
	// get horizontal and vertical speed
	public double getHorizontalMovement()
	{
		return dx;
	}
	public double getVericalMovement()
	{
		return dy;
	}
	
	public void draw(Graphics g)
	{//draw entity
		sprite.draw(g,(int)x, (int) y);
	}
	public void doLogic()
	{
		
	}
	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}
	//check collision with each other
	//true if collision
	public boolean collidesWith(Entity other)
	{
		me.setBounds((int) x, (int)y, sprite.getWidth(),sprite.getHeight());
		him.setBounds((int)other.x, (int)other.y, other.sprite.getWidth(), other.sprite.getWidth());
		return me.intersects(him);
	}
	//entity with which collided
	public abstract void collidedWith(Entity other);

}
