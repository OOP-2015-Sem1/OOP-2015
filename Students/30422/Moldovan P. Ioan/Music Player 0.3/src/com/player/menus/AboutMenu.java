package com.player.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.player.graphics.UpperMenuBar;

public class AboutMenu extends JMenu implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static JMenuItem menuItem;

	public AboutMenu() {
		this.setText("About");
		UpperMenuBar.addMenuItem(this,this,menuItem,"Help");
		this.addSeparator();
		UpperMenuBar.addMenuItem(this,this,menuItem,"About Me");
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Help")){
			
		}
		if(e.getActionCommand().equals("About Me")){
			
		}
	}

}
