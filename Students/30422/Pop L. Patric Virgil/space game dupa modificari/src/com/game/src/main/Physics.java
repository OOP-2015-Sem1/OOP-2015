package com.game.src.main;

import java.util.LinkedList;

import com.game.src.main.classes.Model;
import com.game.src.main.classes.Model;

public class Physics {

	public static boolean Collision(Model enta, Model entb) {

		if (enta.getBounds().intersects(entb.getBounds())) {
			System.out.println("itra");
			return true;
		}

		return false;
	}



	

}
