package com.player.graphics;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class MainWindowRight extends JPanel {
	public static final long serialVersionUID=1L;
	MetadataPanel metadataPanel;
	ControlPanel controlPanel;
	
	public MainWindowRight(){
		this.setLayout(new GridLayout(1,2,50,0));
		metadataPanel=new MetadataPanel();
		controlPanel=new ControlPanel();
		this.add(controlPanel);
		this.add(metadataPanel);
	}
}
