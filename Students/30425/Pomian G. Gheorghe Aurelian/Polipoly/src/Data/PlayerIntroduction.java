package Data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class PlayerIntroduction {

	private Texture background1, background2, background3, background4;
	private UI menuUI;
	private int nrPlayers, currentPlayer;
	
	public void setNrPlayers() {
		nrPlayers = Rules.getInstance().getNrPlayers();
	}

	public PlayerIntroduction() {
		background1 = QuickLoad("Player1");
		background2 = QuickLoad("Player2");
		background3 = QuickLoad("Player3");
		background4 = QuickLoad("Player4");
		menuUI = new UI();
		menuUI.addButton("Start", "Start", 320, 340);
		menuUI.addButton("Back", "Back", 320, 300);
		currentPlayer = 1;
		setNrPlayers();
	}

	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("Back")){
				try {
				    Thread.sleep(200);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				if(currentPlayer == 1)
				StateManager.setState(GameState.GAMECREATION);
				currentPlayer--;
			}
			if (menuUI.isButtonClicked("Start")) {
				try {
				    Thread.sleep(200);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				if (currentPlayer == nrPlayers)
				 StateManager.setState(GameState.GAME);
				currentPlayer++;
			}
		}
	}


	public void update() {
		if(currentPlayer == 0)
			currentPlayer++;
		switch (currentPlayer) {
		case 2: DrawQuadTex(background2, 0, 0, 1024, 512);
			break;
		case 3: DrawQuadTex(background3, 0, 0, 1024, 512);
			break;
		case 4: DrawQuadTex(background4, 0, 0, 1024, 512);
			break;
		default : DrawQuadTex(background1, 0, 0, 1024, 512);
			break;
		
		}

		menuUI.draw();
		updateButtons();
	}

}
