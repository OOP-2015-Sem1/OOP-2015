package Data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class GameCreation {

	private Texture background;
	private UI menuUI;

	public GameCreation() {
		background = QuickLoad("GameCreationBack");
		menuUI = new UI();
		menuUI.addButton("Start", "Start", 470, 400);
		menuUI.addButton("Back", "Back", 180, 400);
		menuUI.addButton("2players", "checked", 320, 179);
		menuUI.addButton("3players", "unchecked", 358, 179);
		menuUI.addButton("4players", "unchecked", 396, 179);
		menuUI.addButton("1000", "checked", 457, 211);
		menuUI.addButton("1500", "unchecked", 534, 211);
		menuUI.addButton("2000", "unchecked", 612, 211);
		menuUI.addButton("Dax2", "checked", 359, 245);
		menuUI.addButton("Nux2", "unchecked", 423, 245);
		menuUI.addButton("Order1", "checked", 365, 278);
		menuUI.addButton("Order2", "unchecked", 494, 278);
		menuUI.addButton("Lucky1", "checked", 422, 310);
		menuUI.addButton("Lucky2", "unchecked", 482, 310);
	}

	private void updateButtons() {
		if (Mouse.isButtonDown(0)) {
			if (menuUI.isButtonClicked("Back"))
				StateManager.setState(GameState.MAINMENU);
			if (menuUI.isButtonClicked("Start")){
				try {
				    Thread.sleep(200);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				StateManager.setState(GameState.GAME);
			}
			if (menuUI.isButtonClicked("2players")) {
				menuUI.changeTexture("2players", "checked");
				menuUI.changeTexture("3players", "unchecked");
				menuUI.changeTexture("4players", "unchecked");
				Rules.getInstance().setNrPlayers(2);
				
			}
			if (menuUI.isButtonClicked("3players")) {
				menuUI.changeTexture("3players", "checked");
				menuUI.changeTexture("2players", "unchecked");
				menuUI.changeTexture("4players", "unchecked");
				Rules.getInstance().setNrPlayers(3);
			}
			if (menuUI.isButtonClicked("4players")) {
				menuUI.changeTexture("4players", "checked");
				menuUI.changeTexture("3players", "unchecked");
				menuUI.changeTexture("2players", "unchecked");
				Rules.getInstance().setNrPlayers(4);
			}
			if (menuUI.isButtonClicked("1000")) {
				menuUI.changeTexture("1000", "checked");
				menuUI.changeTexture("1500", "unchecked");
				menuUI.changeTexture("2000", "unchecked");
				Rules.getInstance().setStartMoney(1000);
			}
			if (menuUI.isButtonClicked("1500")) {
				menuUI.changeTexture("1500", "checked");
				menuUI.changeTexture("1000", "unchecked");
				menuUI.changeTexture("2000", "unchecked");
				Rules.getInstance().setStartMoney(1500);
			}
			if (menuUI.isButtonClicked("2000")) {
				menuUI.changeTexture("2000", "checked");
				menuUI.changeTexture("1500", "unchecked");
				menuUI.changeTexture("1000", "unchecked");
				Rules.getInstance().setStartMoney(2000);
			}
			if (menuUI.isButtonClicked("Dax2")) {
				menuUI.changeTexture("Dax2", "checked");
				menuUI.changeTexture("Nux2", "unchecked");
				Rules.getInstance().setDoubleInc(true);
			}
			if (menuUI.isButtonClicked("Nux2")) {
				menuUI.changeTexture("Nux2", "checked");
				menuUI.changeTexture("Dax2", "unchecked");
				Rules.getInstance().setDoubleInc(false);
			}
			if (menuUI.isButtonClicked("Order1")) {
				menuUI.changeTexture("Order1", "checked");
				menuUI.changeTexture("Order2", "unchecked");
				Rules.getInstance().setOrder(1);
			}
			if (menuUI.isButtonClicked("Order2")) {
				menuUI.changeTexture("Order2", "checked");
				menuUI.changeTexture("Order1", "unchecked");
				Rules.getInstance().setOrder(2);
			}
			if (menuUI.isButtonClicked("Lucky1")) {
				menuUI.changeTexture("Lucky1", "checked");
				menuUI.changeTexture("Lucky2", "unchecked");
				Rules.getInstance().setDoubleInc(true);
			}
			if (menuUI.isButtonClicked("Lucky2")) {
				menuUI.changeTexture("Lucky2", "checked");
				menuUI.changeTexture("Lucky1", "unchecked");
				Rules.getInstance().setDoubleInc(false);
			}
		}
	}


	public void update() {
		DrawQuadTex(background, 0, 0, 1024, 512);
		menuUI.draw();
		updateButtons();
	}

}
