import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	Pacman p;
	public int width;
	public int height;
	public Tile[][] tiles;
	int pi;
	int po;
	
	public Map(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			this.width = map.getWidth();
			this.height = map.getHeight();
			int pixel[] = new int[width*height];
			tiles = new Tile[width][height];
			map.getRGB(0, 0, width, height, pixel, 0, width);
			
			for(int a = 0; a<width; a++){
				for(int b = 0; b<height; b++){
					
					int val = pixel[a + (b*width)];
					
					if(val == 0xFF000000){
						
						tiles[a][b] = new Tile(a*32,b*32);
					
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g){
		
		for(int x = 0; x<width; x++){
			for(int y =0; y<height; y++){
				if(tiles[x][y]!= null){
				tiles[x][y].render(g);
				}
			}
		}
	}	
}
