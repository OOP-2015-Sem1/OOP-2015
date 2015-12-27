import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Menu {
	
	BufferedImage cover,newGame,exit,loading,overCover,menu;
	BufferedImageLoader load = new BufferedImageLoader();
	
	public Menu(){
		
	cover = load.loadImage("/Menu/cover.png");
	newGame = load.loadImage("/Menu/new_game.png");
	exit = load.loadImage("/Menu/exit.png");
	
	overCover = load.loadImage("/Menu/game_over.png");
	menu = load.loadImage("Menu/main_menu.png");
			
	}
	

	public void renderMain(Graphics g){

		g.drawImage(cover,0,0,null);
		g.drawImage(newGame,170,220,null);
		g.drawImage(exit,170,300,null);
		
	}
	
	public void renderOver(Graphics g){
		g.drawImage(overCover,0,0,null);
  		Font f = new Font("Dialog",Font.BOLD,35);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString(Pacman.endScore.toString(), 325, 246);
		g.drawImage(menu, 170, 300, null);
		g.drawImage(exit,170,400,null);
	}
	
}
