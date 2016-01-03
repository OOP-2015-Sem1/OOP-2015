import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Ghosts {

	public static int[] column = new int[3];
	public static int[] row = new int[3];
	public int STEP=2;
	int columns,rows;
	File file = new File("res/Map/map.txt");
	
	private int right = 0, left = 1, up = 2, down = 3;
	
	private int smart = 0, next = 1;
	private int state = smart;
	
	private int lastDir = -1;
	
	private int dirBlue = 0;
	private int dirRed = 0;
	private int dirPurp = 0;
	
	Random randomGen = new Random();
	
	public BufferedImage ghost;
	public BufferedImage red;
	public BufferedImage purp;
	
	BufferedImageLoader load;
	
	ArrayList<String> lines = new ArrayList<String>();
	
	public Ghosts(){
		load = new BufferedImageLoader();
		try {
			Scanner s = new Scanner(file);
			int r = 0;
				while(s.hasNextLine()){
					String line = s.nextLine();
					lines.add(line);
					if(line.contains("4")){
						row[0] = r;
						column[0] = line.indexOf("4");
					}
					if (line.contains("6")){
						row[1] = r;
						column[1] = line.indexOf("6");
					}if(line.contains("7")){
						row[2] = r;
						column[2] = line.indexOf("7");
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
			ghost = load.loadImage("Characters/blue.png");
			red = load.loadImage("/Characters/red.png");
			purp = load.loadImage("/Characters/purp.png");
	}

	public void render(Graphics g,BufferedImage ghost,BufferedImage red,BufferedImage purp){
	//DRAW GHOST
  		g.drawImage(ghost, column[0]*STEP-14, row[0]*STEP-14, null);
 
  		g.drawImage(red, column[1]*STEP-14, row[1]*STEP-14,null);
  		
  		g.drawImage(purp, column[2]*STEP-14, row[2]*STEP-14, null);
	}
	
	private void randomMove(){
		//BLUE

		/*
		if(dirBlue == right){
			
			if(charAt(row[0],column[0]+1) != '0'){
				column[0]+= 1;
			}else{
				dirBlue = randomGen.nextInt(4);
			}
			
		}else if(dirBlue == left){
			
			if(charAt(row[0],column[0]-1) != '0'){
				column[0] -= 1;
			}else{
				dirBlue = randomGen.nextInt(4);
			}
			
		}else if(dirBlue == up){
			
			if(charAt(row[0]-1,column[0]) != '0'){
				row[0] -= 1;
			}else{
				dirBlue = randomGen.nextInt(4);
			}
			
		}else if(dirBlue == down){
			
			if(charAt(row[0]+1,column[0]) != '0'){
				row[0] += 1;
			}else{
				dirBlue = randomGen.nextInt(4);
			}
		
		}
		
		*/
		
		//RED
		
		if(dirRed == right){
			
			if(charAt(row[1],column[1]+1) != '0'){
				column[1]+= 1;
			}else{
				dirRed = randomGen.nextInt(4);
			}
			
		}else if(dirRed == left){
			
			if(charAt(row[1],column[1]-1) != '0'){
				column[1] -= 1;
			}else{
				dirRed = randomGen.nextInt(4);
			}
			
		}else if(dirRed == up){
			
			if(charAt(row[1]-1,column[1]) != '0'){
				row[1] -= 1;
			}else{
				dirRed = randomGen.nextInt(4);
			}
			
		}else if(dirRed == down){
			
			if(charAt(row[1]+1,column[1]) != '0'){
				row[1] += 1;
			}else{
				dirRed = randomGen.nextInt(4);
			}
		
		}
		
		//PURPLE
		
		if(dirPurp == right){
			
			if(charAt(row[2],column[2]+1) != '0'){
				column[2]+= 1;
			}else{
				dirPurp = randomGen.nextInt(4);
			}
			
		}else if(dirPurp == left){
			
			if(charAt(row[2],column[2]-1) != '0'){
				column[2] -= 1;
			}else{
				dirPurp = randomGen.nextInt(4);
			}
			
		}else if(dirPurp == up){
			
			if(charAt(row[2]-1,column[2]) != '0'){
				row[2] -= 1;
			}else{
				dirPurp = randomGen.nextInt(4);
			}
			
		}else if(dirPurp == down){
			
			if(charAt(row[2]+1,column[2]) != '0'){
				row[2] += 1;
			}else{
				dirPurp = randomGen.nextInt(4);
			}
		
		}
	}
	
	private void followPac(){
		
		boolean move = false;
		
		if(state == smart){
			if(column[0] < Pacman.column){
				if(charAt(row[0],column[0]+1) != '0'){
				column[0]+= 1;
				move = true;
				lastDir = right;
				}
			}
			
			if(column[0] > Pacman.column){
				if(charAt(row[0],column[0]-1) != '0'){
				column[0] -= 1;
				move = true;
				lastDir = left;
				}
			}
			
			if(row[0] < Pacman.row){
			if(charAt(row[0]+1,column[0]) != '0'){
				row[0] += 1;
				move = true;
				lastDir = down;
				}
			}

			if(row[0] > Pacman.row){
			if(charAt(row[0]-1,column[0]) != '0'){
				row[0] -= 1;
				move = true;
				lastDir = up;
				}
			}
			
			if(!move){
				
				state = next;
			}
			
		}else if(state == next){
				
				if(lastDir == right){
					
					if(row[0] < Pacman.row){
						if(charAt(row[0]+1,column[0]) != '0'){
							row[0] += 1;
							state = smart;
							}
						}else{
								if(charAt(row[0]-1,column[0]) != '0'){
									row[0] -= 1;
									state = smart;
									}
						}
					
					if(charAt(row[0],column[0]+1) != '0'){
						column[0]+= 1;
					}else if(charAt(row[0]-1,column[0]) != '0'){
						lastDir = up;
					}else if(charAt(row[0]+1,column[0]) != '0'){
						lastDir = down;
					}
					
					
				}else if(lastDir == left){
					
					if(row[0] < Pacman.row){
						if(charAt(row[0]+1,column[0]) != '0'){
							row[0] += 1;
							state = smart;
							}
						}else{
								if(charAt(row[0]-1,column[0]) != '0'){
									row[0] -= 1;
									state = smart;
									}
						}
					
					if(charAt(row[0],column[0]-1) != '0'){
						column[0]-= 1;
					}else if(charAt(row[0]-1,column[0]) != '0'){
						lastDir = up;
					}else if(charAt(row[0]+1,column[0]) != '0'){
						lastDir = down;
					}

					
				}else if(lastDir == down){
					
					
					if(column[0] < Pacman.column){
						if(charAt(row[0],column[0]+1) != '0'){
							column[0] += 1;
							state = smart;
							}
						}else if(column[0] > Pacman.column){
								if(charAt(row[0],column[0]-1) != '0'){
									column[0] -= 1;
									state = smart;
									}
						}
					
					if(charAt(row[0]+1,column[0]) != '0'){
						row[0]+= 1;
					}else if(charAt(row[0],column[0]+1) != '0'){
						lastDir = right;
					}else if(charAt(row[0],column[0]-1) != '0'){
						lastDir = left;
					}
					
				}else if(lastDir == up){
					
					if(column[0] < Pacman.column){
						if(charAt(row[0],column[0]+1) != '0'){
							column[0] += 1;
							state = smart;
							}
						}else{
								if(charAt(row[0],column[0]-1) != '0'){
									column[0] -= 1;
									state = smart;
									}
						}
					
					if(charAt(row[0]-1,column[0]) != '0'){
						row[0]-= 1;
					}else if(charAt(row[0],column[0]+1) != '0'){
						lastDir = right;
					}else if(charAt(row[0],column[0]-1) != '0'){
						lastDir = left;
					}
					
				}
			}
		}
		
	
	public void tick(){

		randomMove();
		followPac();

	}

	public char charAt (int row, int column){
		
		return lines.get(row).charAt(column);
		
	}
	
	
}
