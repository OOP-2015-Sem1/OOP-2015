package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Pong implements ActionListener,KeyListener
{
	
	public static Pong pong;
	
	public Renderer renderer;
	
	public Paddle player1;
	
	public Paddle player2;
	
	public Ball ball;
	
	public int width = 600, height = 500;
	
	public boolean bot = false, selectingDifficulty = false;
	
	public boolean w,s,up,down;
	
	public int gameStatus = 0, scoreLimit = 5, playerWon;
	
	public int botDifficulty, botMoves, botCooldown = 0;
	
	public Random random;
	
	public Pong()
	{
		Timer timer = new Timer(20,this);
		JFrame jFrame = new JFrame("Pong");
		random = new Random();
		
		renderer = new Renderer();
		
		jFrame.setSize(width+15,height+39);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(renderer);
		jFrame.setLocationRelativeTo(null);
		jFrame.addKeyListener(this);
		
		timer.start();
	}
	
	public void start()
	{
		gameStatus = 2;
		player1 = new Paddle(this, 1);
		player2 = new Paddle(this, 2);
		
		ball = new Ball(this);
	}
	
	public void update()
	{
		
		if(player1.score == scoreLimit)
		{
			playerWon = 1;
			gameStatus = 3;
		}
		if(player2.score == scoreLimit)
		{
			playerWon = 2;
			gameStatus = 3;
		}
		
		if(w)
		{
			player1.move(true);
		}
		if(s)
		{
			player1.move(false);
		}
		if(!bot)
		
		{
			if(up)
			{
			player2.move(true);
			}
			if(down)
			{
			player2.move(false);
			}
		}
		else 
		{
		
			if(botCooldown > 0)
			{
				botCooldown--;
				if(botCooldown == 0)
				{
					botMoves = 0;
				}
			}
			if(botMoves < 10)
			{
			if(player2.y + player2.height / 2 < ball.y)
			{
				player2.move(false);
				botMoves++;
			}
			if(player2.y + player2.height / 2  > ball.y)
			{
				player2.move(true);
				botMoves++;
			}
			if (botDifficulty == 0)
			{
			botCooldown = 20;
			}
			if (botDifficulty == 1)
			{
			botCooldown = 15;
			}
			if (botDifficulty == 2)
			{
			botCooldown = 10;
			}
			}
		
		}
		
		ball.update(player1, player2);
		
		
	}
	
	public void render(Graphics2D g)
	{
		g.setColor(new Color(51, 102, 0));
		g.fillRect(0, 0, width, height);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(gameStatus == 0)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PONG", width / 2 - 75, 50);
			
			if (!selectingDifficulty)
			{
			g.setFont(new Font("Arial", 1, 28));
			g.drawString("Press SPACE to Play 1 vs 1", width / 2 - 180, height/2 - 50);
			g.drawString("Press SHIFT to Play against Computer", width / 2 - 250, height/2 - 10);
			g.drawString("Score Limit (0-100) => "+ scoreLimit, width / 2 - 160, height/2 +30);
			g.drawString("(use arrows)", width / 2 - 140, height/2 +55);
			
			g.drawString("In game settings:", width / 2 - 200, height/2 + 110);
			g.drawString("SPACE: Play-Pause", width / 2 - 200, height/2 + 145);
			g.drawString("ESCAPE: Menu", width / 2 - 200, height/2 + 180);
			}
		}
		
		if (selectingDifficulty == true)
		{
			String string = botDifficulty == 0 ? "Easy" : (botDifficulty == 1 ? "Medium " : "Hard");
			g.setFont(new Font("Arial", 1, 28));
			g.drawString("Select Difficulty => " + string, width / 2 - 160, height/2 - 50);
			g.drawString("(use arrows)", width / 2 - 135, height/2 - 25);
			g.drawString("Press SPACE to Play", width / 2 - 140, height/2 +10);
						
		}
		
		if(gameStatus == 2 || gameStatus == 1)
		{
		g.setColor(new Color(224, 224, 224));
		g.fillRect(0, 0, width, 5);
		g.fillRect(width-5, 0, 5, height);
		g.fillRect(0, height-5, width, 5);
		g.fillRect(0, 0, 5, height);
		g.setStroke(new BasicStroke(3));
		g.drawLine(0, height/2, width, height/2);
		g.setStroke(new BasicStroke(5));
		g.drawLine(width/2, 0, width/2, height);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", 1, 50));
		g.drawString(String.valueOf(player1.score), width / 2 - 55, 50);
		g.drawString(String.valueOf(player2.score), width / 2 + 25, 50);
		
		
		player1.render(g);
		player2.render1(g);
		
		ball.render(g);
		}
		
		if(gameStatus == 3)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PONG", width / 2 - 75, 50);
			
			if(bot && player2.score == scoreLimit)
			{
				g.drawString("The Computer Wins!", width / 2 - 220, 120);
			}else
			{
			g.drawString("Player " + playerWon + " Wins!", width / 2 - 160, 120);
			}
			
			if (!selectingDifficulty)
			{
			g.setFont(new Font("Arial", 1, 28));
			
			g.drawString("Press ESC for MENU", width / 2 - 130, height/2 - 10);
		
			}
		}
		
		
		if(gameStatus == 1)
		{
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PAUSED", width / 2 - 103, height / 2 - 125);
		}
	}
	@Override
	
	public void actionPerformed(ActionEvent e)
	{
		if(gameStatus == 2)
		{
			update();
		}
		
		
		renderer.repaint();
		
	}

	public static void main(String[] args)
	{
		pong = new Pong();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int id = e.getKeyCode();
		if (id == KeyEvent.VK_W)
		{
			 w = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			 s = true;
		}
		else if (id == KeyEvent.VK_UP)
		{
			 up = true;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			 down = true;
		}
		else if (id == KeyEvent.VK_RIGHT && scoreLimit < 100)
		{
			if(selectingDifficulty)
			{
			 if (botDifficulty < 2)
			 {
				 botDifficulty++;
			 }else
			 {
				 botDifficulty = 0;
			 }
			}
			 else if(gameStatus == 0)
				{
				 scoreLimit++;
				}
			
		}
		else if (id == KeyEvent.VK_LEFT )
		{
			if(selectingDifficulty)
			{	
			 if (botDifficulty > 0)
			 {
				 botDifficulty--;
			 }else
			 {
				 botDifficulty = 2;
			 }
			}else if(gameStatus == 0 && scoreLimit > 1)
			{
				scoreLimit--;
			}
		}
		else if (id == KeyEvent.VK_ESCAPE && (gameStatus == 2 || gameStatus == 3))
		{
			 gameStatus = 0;
		}
		
		else if (id == KeyEvent.VK_SHIFT && gameStatus == 0 )
		{
			bot = true;
			selectingDifficulty = true;
		}
		else if (id == KeyEvent.VK_SPACE)
		{
			if(gameStatus == 0)
			{
				if(!selectingDifficulty)
				{
					bot = false;
				}else
				{
					selectingDifficulty = false;
				}
				
				start();
				
				
				
			}
			else if(gameStatus == 1)
			{
				gameStatus = 2;
			}
			else if(gameStatus == 2)
			{
				gameStatus = 1;
			}
		
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();
		if (id == KeyEvent.VK_W)
		{
			 w = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			 s = false;
		}
		else if (id == KeyEvent.VK_UP)
		{
			 up = false;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			 down = false;
		}
		
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
		
	}
	
	
}
