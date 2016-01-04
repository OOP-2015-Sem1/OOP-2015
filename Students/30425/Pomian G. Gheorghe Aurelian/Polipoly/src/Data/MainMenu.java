package Data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;


public class MainMenu {

	private Texture background;
	private UI menuUI;
	

	
	public MainMenu() {
		background = QuickLoad("MainBackground");
		menuUI = new UI();
		menuUI.addButton("NewGame", "NewGame", 320, 202);
		menuUI.addButton("Load", "Load", 320, 264);
		menuUI.addButton("Options", "Options", 320, 326);
		menuUI.addButton("Quit", "Quit", 320, 388);
	}

	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("NewGame"))
				StateManager.setState(GameState.GAMECREATION);
			if (menuUI.isButtonClicked("Load"))
				StateManager.setState(GameState.MAINMENU);
			if (menuUI.isButtonClicked("Options"))
				StateManager.setState(GameState.OPTIONS);
			if (menuUI.isButtonClicked("Quit"))
				System.exit(0);
		}
	}


	public void update() {
		DrawQuadTex(background, 0, 0, 1024, 512);
		menuUI.draw();
		updateButtons();
	}

}
