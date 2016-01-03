package com.player.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.player.graphics.UpperMenuBar;

public class EditMenu extends JMenu implements ActionListener {
	public static final long serialVersionUID = 1L;
	private JMenuItem menuItem;

	public EditMenu() {
		this.setText("Edit");
		UpperMenuBar.addMenuItem(this,this, menuItem, "Copy");
		UpperMenuBar.addMenuItem(this,this, menuItem, "Cut");
		UpperMenuBar.addMenuItem(this,this, menuItem, "Paste");
		this.addSeparator();
		UpperMenuBar.addMenuItem(this,this, menuItem, "Preferences");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Copy")) {

		}
		if (e.getActionCommand().equals("Cut")) {

		}
		if (e.getActionCommand().equals("Paste")) {

		}
		if (e.getActionCommand().equals("Copy")) {

		}

	}

}
