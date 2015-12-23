package io.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import resources.Main;

public class startButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Main.game.setupMap();
	}

}
