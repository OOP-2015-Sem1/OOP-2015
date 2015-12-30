package anoyingame.core;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import anoyingame.ui.ShapeGameFrame;
import anoyingame.ui.ShapeMenu;

public class ShapeController {


	private ShapeGameFrame shapeFrame;
	private int mode;
	private ShapeMenu shapeMenu;
	private JButton modeEasy;
	private JButton modeMedium;
	private JButton modeHard;
	
	public void start(){
		shapeMenu = new ShapeMenu();
		
		modeEasy = shapeMenu.getButton("easy");
		modeMedium = shapeMenu.getButton("medium");
		modeHard = shapeMenu.getButton("hard");
		
		modeEasy.addActionListener(new ModeListener());
		modeMedium.addActionListener(new ModeListener());
		modeHard.addActionListener(new ModeListener());
	}
		
	private class ModeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == modeEasy)
				mode = 4;
			else if (event.getSource() == modeMedium)
				mode = 6;
			else if (event.getSource() == modeHard)
				mode = 9;
			shapeMenu.dispose();
			shapeFrame = new ShapeGameFrame(mode);
			
			
		}
		
	}
}
