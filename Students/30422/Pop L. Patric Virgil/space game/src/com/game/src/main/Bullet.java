package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Bullet extends GameObject implements EntityA{
	
	
	private double BulletSpeed=8;
	private Game game;
	Controller controller;
	
	private Textures tex;
	
	public Bullet(double x,double y,Textures tex,Game game,Controller controller){
		super(x,y);
		this.tex=tex;
		this.game=game;
		this.controller=controller;
		
		
	
		
	}
	public void tick(){
		y-=BulletSpeed;
		
		
	
	//if(Physics.Collision(this, (EntityB) game.eb )){
	//	System.out.println("collision detected");	
		//}
	}
	public void render(Graphics g){
		g.drawImage(tex.bullet,(int)x,(int)y,null);
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,32,32);
	}
	public double getY(){
		return y;
	}
	public double getX(){
		return x;
	}

}
