package GameShapes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ScoreTable extends JFrame{

	public ScoreTable(){
	   super("Status");
	   
	}
	
	
	public void paintScore(Graphics g, Color scoreTablecolor, Ball ball) {

		Graphics2D gdr = (Graphics2D) g;
		gdr.setColor(Color.orange);
		gdr.setFont(new Font("",Font.BOLD,20));
		gdr.fillOval(920, 310, 150, 145);
		gdr.setColor(Color.gray);
		gdr.drawString("Your Score", 950, 355);
		gdr.drawString(ball.getScore(), 950, 380);
		gdr.drawLine(950,400,1030,400);
		
		gdr.setColor(Color.blue);
		gdr.setFont(new Font("",Font.BOLD,19));
		gdr.fillOval(920, 410, 130, 125);
		gdr.setColor(Color.black);
		gdr.drawString(String.valueOf(ball.getBrickCount()), 950, 455);
		gdr.setFont(new Font("",Font.BOLD,14));
		gdr.drawString("brick(s) destroyed", 920, 480);
		
		gdr.setColor(Color.pink);
		gdr.setFont(new Font("",Font.BOLD,14));
		gdr.fillOval(960, 480, 105, 105);
		gdr.setColor(Color.white);
		gdr.drawString(String.valueOf(80-ball.getBrickCount()), 990, 525);
		gdr.drawString("brick(s) to go", 970, 550);
		gdr.setColor(Color.gray);
		gdr.drawLine(915, 0, 915, 850);
		
	}
}
