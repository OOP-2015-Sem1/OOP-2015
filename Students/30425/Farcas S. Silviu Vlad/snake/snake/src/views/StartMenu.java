package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import models.*;

public class StartMenu extends JPanel implements KeyListener{
	
	public static int small=0, medium=1, large=0, easy=0, normal=1, hard=0;
	public static boolean goThroughWalls=false;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 800, 700);
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Century Schoolbook", Font.BOLD, 100));
		g.drawString("SnakeyBooBoo", 5, 100);
		g.setFont(new Font("Arial",Font.PLAIN, 12));
		g.setColor(Color.GRAY);
		g.fillRect(150, 150, 200, 150);
		g.setColor(Color.YELLOW);
		g.drawString("Press Space to start", 200, 220);
		g.setColor(Color.RED);
		if(small==1){
			g.fillRect(440, 150, 220, 50);
		}
		if(medium==1){
			g.fillRect(440, 200, 220, 50);
		}
		if(large==1){
			g.fillRect(440, 250, 220, 50);
		}
		if(goThroughWalls){
			g.fillRect(140, 390, 220, 50);
		}
		if(easy==1){
			g.fillRect(440, 390, 220, 50);
		}
		if(normal==1){
			g.fillRect(440, 440, 220, 50);
		}
		if(hard==1){
			g.fillRect(440, 490, 220, 50);
		}
		g.setColor(Color.GRAY);
		g.fillRect(450, 160, 200, 30);
		g.fillRect(450, 210, 200, 30);
		g.fillRect(450, 260, 200, 30);
		g.fillRect(150, 400, 200, 30);
		g.fillRect(450, 400, 200, 30);
		g.fillRect(450, 450, 200, 30);
		g.fillRect(450, 500, 200, 30);
		g.setColor(Color.YELLOW);
		g.drawString("Small (S)", 530, 180 );
		g.drawString("Medium (M)", 525, 230);
		g.drawString("Large (L)", 530, 280);
		g.drawString("Go Through Walls (G)", 200, 420);
		g.drawString("Easy (E)", 530, 420);
		g.drawString("Normal (N)", 525,470 );
		g.drawString("Hard (H)", 530, 520 );
		g.setColor(Color.ORANGE);
		g.drawString("\u00a9 Vlad Silviu Farcas, UTCN 2015", 100, 600);
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		int i = e.getKeyCode();
		if(i == KeyEvent.VK_SPACE){
			Snake snake=new Snake();
			GameManagement game = new GameManagement(snake);
			game.startGame();
			
		}
		if(i==KeyEvent.VK_S){
			small=1;
			medium=0;
			large=0;
			this.repaint();
		}
		if(i==KeyEvent.VK_M){
			small=0;
			medium=1;
			large=0;
			this.repaint();
		}
		if(i==KeyEvent.VK_L){
			small=0;
			medium=0;
			large=1;
			this.repaint();
		}
		if(i==KeyEvent.VK_G){
			goThroughWalls=!goThroughWalls;
			this.repaint();
		}
		if(i==KeyEvent.VK_E){
			easy=1;
			normal=0;
			hard=0;
			this.repaint();
		}
		if(i==KeyEvent.VK_N){
			easy=0;
			normal=1;
			hard=0;
			this.repaint();
		}
		if(i==KeyEvent.VK_H){
			easy=0;
			normal=0;
			hard=1;
			this.repaint();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
