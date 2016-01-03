package com.player.graphics;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.player.menus.AboutMenu;
import com.player.menus.EditMenu;
import com.player.menus.FileMenu;

public class UpperMenuBar extends JMenuBar {
	public static final long serialVersionUID=1L;
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private AboutMenu aboutMenu;
	
	public UpperMenuBar(){
		super();
		fileMenu=new FileMenu();
		this.add(fileMenu);
		editMenu=new EditMenu();
		this.add(editMenu);
		aboutMenu=new AboutMenu();
		this.add(aboutMenu);
	}
	
	
	public static void addMenuItem(JMenu menu,ActionListener listener,JMenuItem menuItem, String text) {
		menuItem = new JMenuItem(text);
		menuItem.setActionCommand(text);
		menuItem.addActionListener(listener);
		menu.add(menuItem);

	}

}
