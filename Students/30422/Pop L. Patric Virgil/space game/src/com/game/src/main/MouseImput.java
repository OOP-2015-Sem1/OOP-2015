package com.game.src.main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseImput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		
		int mx=e.getX();
		int my=e.getY();
		
		
		
		if(mx >= Game.WIDTH/2 +120 && mx<=Game.WIDTH/2 +220){
			if(my >=150 && my <= 200){
				Game.State=Game.STATE.GAME;
			}
		}
		if(mx >= Game.WIDTH/2 +120 && mx<=Game.WIDTH/2 +220){
			if(my >=300 && my <= 350){
				System.exit(1);
			}
		}
		//public Rectangle playButton= new Rectangle(Game.WIDTH/2+120,150,100,50);
		//public Rectangle exitButton= new Rectangle(Game.WIDTH/2+120,300,100,50);
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
