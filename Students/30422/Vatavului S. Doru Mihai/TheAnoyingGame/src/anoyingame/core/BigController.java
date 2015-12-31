package anoyingame.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import anoyingame.ui.GameChooser;

public class BigController {
	
		private GameController theGame = new GameController();
		private ShapeController theGame2 = new ShapeController();
		private GameChooser chooser = new GameChooser(); 
		private JButton game1;
		private JButton game2;
		public BigController(){
		
			game1 = chooser.getButton1();
			game2 = chooser.getButton2();
			game1.addActionListener(new ChoiceListener());
			game2.addActionListener(new ChoiceListener());
		
		}
		
	private class ChoiceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == game1)
				theGame.start();
			else
				if(event.getSource() == game2)
					theGame2.start();
			chooser.dispose();
			
		
		}
	}
}

			