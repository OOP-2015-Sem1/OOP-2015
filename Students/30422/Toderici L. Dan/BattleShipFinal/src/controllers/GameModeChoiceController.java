package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ui.GameModeChoiceView;

public class GameModeChoiceController {
	
	private int gameMode = -1;
	
	private GameModeChoiceView gameModeChoiceFrame;
	
	
	public GameModeChoiceController()
	{
		gameModeChoiceFrame = new GameModeChoiceView();
		
		gameModeChoiceFrame.addChoiceListener(new ChoiceListener());
		
		
	}
	
	public void setVisibilityOfView(boolean visible)
	{
		gameModeChoiceFrame.setVisible(visible);
	}

	public int getGameMode() {
		return gameMode;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}
	
	class ChoiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int choice;
			try {
				choice = gameModeChoiceFrame.getChoice();
				if(choice<=0 || choice>3)
				{
					gameModeChoiceFrame.displayErrorMessage("Not good option");
					gameModeChoiceFrame.setChoice("");
				}
				else
				{
					gameModeChoiceFrame.displayGoodInputMessage(choice);
					setGameMode(choice);
				}
			}
			catch(NumberFormatException ex)
			{
				gameModeChoiceFrame.displayErrorMessage("Put an integer for Christ sake!");
				gameModeChoiceFrame.setChoice("");
			}
			
		}
		
	}

}
