import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Pacman extends Rectangle {
	
	private static final long serialVersionUID = 1L;
	public double x;
	private double y;
	public double velX=2.4;
	private double velY=0;
	
	public BufferedImage[] pacman = new BufferedImage[24];
	BufferedImageLoader load;
	
	public Pacman(double x, double y){
		this.x = x;
		this.y = y;
		load = new BufferedImageLoader();
		try {
			pacman[0] = load.loadImage("/RIGHT/RIGHT.png");
			pacman[1] = load.loadImage("/RIGHT/RIGHT1.png");
			pacman[2] = load.loadImage("/RIGHT/RIGHT2.png");
			pacman[3] = load.loadImage("/RIGHT/RIGHT3.png");
			pacman[4] = load.loadImage("/RIGHT/RIGHT4.png");
			pacman[5] = load.loadImage("/RIGHT/RIGHT5.png");
			pacman[6] = load.loadImage("/LEFT/LEFT.png");
			pacman[7] = load.loadImage("/LEFT/LEFT1.png");
			pacman[8] = load.loadImage("/LEFT/LEFT2.png");
			pacman[9] = load.loadImage("/LEFT/LEFT3.png");
			pacman[10] = load.loadImage("/LEFT/LEFT4.png");
			pacman[11] = load.loadImage("/LEFT/LEFT5.png");
			pacman[12] = load.loadImage("/UP/UP.png");
			pacman[13] = load.loadImage("/UP/UP1.png");
			pacman[14] = load.loadImage("/UP/UP2.png");
			pacman[15] = load.loadImage("/UP/UP3.png");
			pacman[16] = load.loadImage("/UP/UP4.png");
			pacman[17] = load.loadImage("/UP/UP5.png");
			pacman[18] = load.loadImage("/DOWN/DOWN.png");
			pacman[19] = load.loadImage("/DOWN/DOWN1.png");
			pacman[20] = load.loadImage("/DOWN/DOWN2.png");
			pacman[21] = load.loadImage("/DOWN/DOWN3.png");
			pacman[22] = load.loadImage("/DOWN/DOWN4.png");
			pacman[23] = load.loadImage("/DOWN/DOWN5.png");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g,BufferedImage p){
  		g.drawImage(p, (int)x, (int)y, null);
	}
	
	public void tick(){
		if(canMove(x+velX,y) == true) {
			x+=velX;
		}else{
			velX=0;
		}
		
		if(canMove(x,y+velY) == true) {
			y+=velY;
		}else{
			velY=0;
		}
		
		
		if(x <= 0) x = 0;
		if(x >= 640-32) x=640-32;
		
		if(y <= 0) y = 0;
		if(y >= 480-32) y=480-32;
		

	}
	
	public boolean canMove(double nextX, double nextY){
		
		Rectangle bounds =  new Rectangle((int)nextX,(int)nextY,29,29);
		Map map = GameEngine.map;
		
		for (int a = 0; a<map.tiles.length;a++){
			for(int b = 0; b<map.tiles[0].length;b++){
				if(map.tiles[a][b] != null){
					if(bounds.intersects(map.tiles[a][b])){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	
	
}
