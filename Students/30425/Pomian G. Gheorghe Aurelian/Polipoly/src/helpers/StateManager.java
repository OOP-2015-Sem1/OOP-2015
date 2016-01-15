package helpers;

import Data.Game;
import Data.GameCreation;
import Data.Load;
import Data.MainMenu;
import Data.Options;
import Data.PlayerIntroduction;

public class StateManager {

	public static enum GameState {
		MAINMENU, GAME, OPTIONS, LOAD, GAMECREATION, PLAYERINIT, ENDGAME, ENDSTATS
	}

	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static GameCreation gameCreation;
	public static Options options;
	public static Load load;
	public static Game game;
	public static PlayerIntroduction playerinit;

	public static void update() {
		switch (gameState) {
		case MAINMENU:
			if (mainMenu == null)
				mainMenu = new MainMenu();
			mainMenu.update();
			break;
		case GAMECREATION:
			if (gameCreation == null)
				gameCreation = new GameCreation();
			gameCreation.update();
			break;
		case GAME:
			if (game == null)
				game = new Game();
			game.update();
			break;
		case OPTIONS:
			if (options == null)
				options = new Options();
			options.update();
			break;
		case LOAD:
			if (load == null)
				load = new Load();
			load.update();
			break;
		case PLAYERINIT:
			if (playerinit == null)
				playerinit = new PlayerIntroduction();
			playerinit.update();
		case ENDGAME:
			StateManager.setState(GameState.ENDSTATS);
			break;
		case ENDSTATS:
			
			System.exit(0);
			break;
		default:
			break;

		}
	}

	public static void setState(GameState newState) {
		gameState = newState;
	}

}
