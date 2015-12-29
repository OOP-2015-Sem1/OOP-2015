package anoyingame.core;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import anoyingame.ui.GameFrame;
import anoyingame.ui.Menu;

public class GameController {
	
	private GameFrame theFrame;
	private int mode;
	private Menu theMenu;
	private JButton timeModeEasy;
	private JButton timeModeMedium;
	private JButton timeModeHard;
	
	public void start(){
		theMenu = new Menu();
		
		timeModeEasy = theMenu.getButton("easy");
		timeModeMedium = theMenu.getButton("medium");
		timeModeHard = theMenu.getButton("hard");
		
		timeModeEasy.addActionListener(new MenuListener());
		timeModeMedium.addActionListener(new MenuListener());
		timeModeHard.addActionListener(new MenuListener());
		
		
		
	}
	
	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == timeModeEasy)
				mode = 1;
			else if (event.getSource() == timeModeMedium)
				mode = 2;
			else if (event.getSource() == timeModeHard)
				mode = 3;
			theMenu.dispose();
			theFrame = new GameFrame();
			theFrame.setCount(mode);
			theFrame.allowTimerStart(true);
		}
		
	}
}
