import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pacman {
	
	public int column;
	private int row;
	public int STEP=2;
	int columns,rows;
	int nextDir,curDir = KeyEvent.VK_RIGHT;
	File file = new File("res/Map/map.txt");
	
	public BufferedImage pacman;
	BufferedImageLoader load;
	
	static int SUCCESS = 1, FAIL = 0;
	
	ArrayList<String> lines = new ArrayList<String>();
	
	public Pacman(){

		load = new BufferedImageLoader();
		try {
			Scanner s = new Scanner(file);
			int r = 0;
				while(s.hasNextLine()){
					String line = s.nextLine();
					lines.add(line);
					if(line.contains("5")){
						row = r;
						column = line.indexOf("5");
					}
					r++;	
				}
				
			s.close();
			
			rows = lines.size();
			columns = lines.get(0).length();
			
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't EXIST !!!");
			e.printStackTrace();
		}
			pacman = load.loadImage("Characters/Pacman.png");
	}
	
	public void render(Graphics g,BufferedImage p){
	
  		g.drawImage(p, column*STEP-14, row*STEP-14, null);
	}
	
	public void render1(Graphics g){ // JUST A TEST, TO SEE PACMAN'S PATH
		
		for(int r = 0;r<rows;r++){
			for(int c = 0; c<columns;c++){
				if(charAt(r,c) != '0'){
					g.fillRect(c*STEP, r*STEP, STEP, STEP);
				}
			}
		}
		
	}
	
	public void tick(){
		
			if(move(nextDir) == SUCCESS){
				curDir = nextDir;
			}else{
				move(curDir);
			}
	}
			
			private int move(int nextDir){
			
			switch(nextDir){
			case KeyEvent.VK_LEFT:
				if(column > 0 && charAt(row,column-1) != '0'){
					column -= 1;
				return SUCCESS;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(column < columns-1 && charAt(row,column+1) != '0'){
					column += 1;
					return SUCCESS;
				}
				break;
			case KeyEvent.VK_UP:
				if(row > 0 && charAt(row-1,column) != '0'){
					row -= 1;
					return SUCCESS;
				}
				break;
			case KeyEvent.VK_DOWN:
				if(row < rows-1 && charAt(row+1,column) != '0'){
					row += 1;
				return SUCCESS;
				}
				break;
			}	
			return FAIL;
	}
	
	public char charAt (int row, int column){
		
		return lines.get(row).charAt(column);
		
	}
	
	public void setNextDir(int nextDir) {
		this.nextDir = nextDir;
	}
	
	public int getCurDir() {
		return curDir;
	}
}

