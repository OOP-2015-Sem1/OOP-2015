package io.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import resources.Main;

public class HighscoreListener implements ActionListener {

	public void actionPerformed(ActionEvent e){
		Main.game.showHighscores();
	}
}
