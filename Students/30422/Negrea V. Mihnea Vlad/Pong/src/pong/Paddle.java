package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Paddle 
{
	public int paddleNumber;
	
	public int x,y,width = 20,height = 150;
	
	public int score;
	
	public Paddle(Pong pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;
		
		if (paddleNumber == 1)
		{
			this.x = 0;
		}
		if (paddleNumber == 2)
		{
			this.x = pong.width-width;
		}
		this.y = pong.height / 2 - this.height / 2;
		
		
		
		
	}

	public void render(Graphics g) 
	{
		g.setColor(new Color(32, 32, 32));
		g.fillRect(x, y, width, height);
		
	}
	public void render1(Graphics g)
	{
		g.setColor(new Color(153, 0, 0));
		g.fillRect(x, y, width, height);
		
	}

	

	public void move(boolean up)
	{
		int speed = 15;
		if(up)
		{
			if(y - speed > 0)
			{
				y -= speed;
			}
			else
			{
				y = 0;
			}
		}
		else 
		{
			if(y + height + speed < Pong.pong.height)
			{
				y += speed;
			}
			else
			{
				y = Pong.pong.height - height;
			}
		}
		
	}



}
