package models;


import javax.swing.JFrame;

import views.*;

public class Main {
	
	public static void main(String[] args) {
		JFrame jf = new JFrame("Menu");
		jf.setVisible(true);
		jf.setSize(805, 700);
		StartMenu menu = new StartMenu();
		jf.add(menu);
		jf.addKeyListener(menu);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
}
