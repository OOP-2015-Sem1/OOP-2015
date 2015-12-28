package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.Random;

public class Ball {
	
	public int x,y,width = 20,height = 20;
	
	public int motionX,motionY;
	
	public Random random;
	
	private Pong pong;
	
	public int amountofhits;
	
	public static File paddleHit1 = new File("paddle1.wav");
	
	public static File paddleHit2 = new File("paddle2.wav");
	
	public static File lose = new File("loser.wav");
	
	
	public Ball(Pong pong)
	{
		this.random = new Random();
		this.pong = pong;
		
		spawn();
	
	}
	
	public void update(Paddle paddle1, Paddle paddle2)
	{
		int speed = 5;
		
		this.x += motionX * speed;
		this.y += motionY * speed;
		
	
		
		if (this.y + height - motionY > pong.height || this.y + motionY  < 0)
		{
			if (this.motionY < 0)
			{
				this.y = 0;
				this.motionY = random.nextInt(4);
				if(motionY == 0)
				{
					motionY = 1;
				}
			}
			else
			{
				
				this.motionY = -random.nextInt(4);
				this.y = pong.height - height;
				if(motionY == 0)
				{
					motionY = -1;
				}
				
			}
			
		}
		
		
		if(CheckCollision(paddle1) == 1)
		{
			Sound.PlaySound(paddleHit1);
			
			this.motionX = 1 + (amountofhits / 5);
			this.motionY = -2 + random.nextInt(4);
			
			if(motionY == 0)
			{
				motionY = 1;
			}
			amountofhits++;
			
		}
		else if(CheckCollision(paddle2) == 1)
		{
			Sound.PlaySound(paddleHit2);
			this.motionX = - 1 - (amountofhits / 5);
			this.motionY = -2 + random.nextInt(4);
			
			if(motionY == 0)
			{
				motionY = 1;
			}
			amountofhits++;
			
			
		}
		
		if(CheckCollision(paddle1) == 2)
		{
			paddle2.score++;
			spawn();
		}
		else if(CheckCollision(paddle2) == 2)
		{
			paddle1.score++;
			spawn();
		}
		
		
	}
	
	public void spawn()
	{
		
		this.amountofhits = 0;
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;
		
		
		
		this.motionY = -2 + random.nextInt(4);
		if(motionY == 0)
		{
			motionY = 1;
		}
		
		if(random.nextBoolean())
		{
			motionX = 1;
		}
		else
		{
			motionX = -1;
		}
	}
	
	public int CheckCollision(Paddle paddle)
	{
		
		if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
		{
			
			return 1;
		}
		else if ((paddle.x > x  && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2))
		{
			Sound.PlaySound(lose);
			return 2;
			
		}
		
		return 0;
	}
	
	public void render(Graphics g)
	{
		g.setColor(new Color(255, 153, 51));
		g.fillOval(x, y, width, height);
	}

}
