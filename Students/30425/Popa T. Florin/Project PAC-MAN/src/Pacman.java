import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pacman {
	
	public static int column,row;
	public int STEP=2;
	int columns,rows;
	int nextDir,curDir = KeyEvent.VK_RIGHT;
	File file = new File("res/Map/map.txt");
	
	GameEngine game = new GameEngine();
	
	public BufferedImage pacman;
	public BufferedImage blue;
	public BufferedImage pill;
	BufferedImageLoader load;
	
	private Integer score = 0;
	public static Integer endScore;
	
	char[][] cells;// copy of chars
	
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
			cells = new char[rows][columns];
			
			for(int u = 0; u<rows;u++){
				System.arraycopy(lines.get(u).toCharArray(), 0, cells[u], 0, columns);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File doesn't EXIST !!!");
			e.printStackTrace();
		}
			pacman = load.loadImage("Characters/pacman.png");
			pill = load.loadImage("/Map/pill.png");
	}

	public void render(Graphics g,BufferedImage pacman,BufferedImage pill){
	//DRAW PACMAN
  		g.drawImage(pacman, column*STEP-14, row*STEP-14, null);
  		
  	//DRAW HIGHSCORE
  		Font f = new Font("Dialog",Font.BOLD,20);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(score.toString(), 300, 500);
  	//DRAW PILLS	
  		for(int r = 0;r<rows;r++){
			for(int c = 0; c<columns;c++){
				if(cells[r][c] == '2'){
					
					g.drawImage(pill,c*STEP-10, r*STEP-10, null);
					
				}
			}
		}
	}
	
	private boolean GhostIntersect(){
		
		Rectangle pacShadow = new Rectangle(column*STEP-10, row*STEP-10,24,24);
		Rectangle blueShadow = new Rectangle(Ghosts.column[0]*STEP-12, Ghosts.row[0]*STEP-12,26,28);
		Rectangle redShadow = new Rectangle(Ghosts.column[1]*STEP-12, Ghosts.row[1]*STEP-12,26,28);
		Rectangle purpShadow = new Rectangle(Ghosts.column[2]*STEP-12, Ghosts.row[2]*STEP-12,26,28);
		
		if(pacShadow.intersects(blueShadow) || pacShadow.intersects(redShadow) || pacShadow.intersects(purpShadow)){
			return true;
		}
		return false;
	}
	
	public void tick(){
		
		if(GhostIntersect()){
			System.out.println("GAME OVER!!");
			endScore = score;
			GameEngine.State = GameEngine.STATE.OVER;
			game.init();
			score = 0;
			for(int u = 0; u<rows;u++){
				System.arraycopy(lines.get(u).toCharArray(), 0, cells[u], 0, columns);
			}
			nextDir = KeyEvent.VK_RIGHT;
			curDir = KeyEvent.VK_RIGHT;

		}
		
			if(move(nextDir) == SUCCESS){
				curDir = nextDir;
			}else{
				move(curDir);
		}
			//updating pills
			
			if(cells[row][column] == '2'){
				score+=10;
				if(score == 2780){
					System.out.println("YOU WIN!!");
					endScore = score;
					GameEngine.State = GameEngine.STATE.OVER;
					game.init();
					score = 0;
					for(int u = 0; u<rows;u++){
						System.arraycopy(lines.get(u).toCharArray(), 0, cells[u], 0, columns);
					}
					nextDir = KeyEvent.VK_RIGHT;
					curDir = KeyEvent.VK_RIGHT;
				}
				//eat the pill
				cells[row][column] = '1';
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
	
	public Integer getScore() {
		return score;
	}
}

