package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import theGame.MainGame;
import theGame.MainGame.STATE;

public class Menu extends MouseAdapter {

	private MainGame mainGame;
	private boolean clickGame;
	private boolean clickMultiplayer;
	private boolean clickQuit;

	public Menu(MainGame mainGame) {
		this.mainGame = mainGame;
	}

	public void mousePressed(MouseEvent e) {
		int mousex = e.getX();
		int mousey = e.getY();

		clickGame = mouseOver(mousex, mousey, 300, 150, 400, 100);
		clickMultiplayer = mouseOver(mousex, mousey, 300, 300, 400, 100);
		clickQuit = mouseOver(mousex, mousey, 300, 450, 400, 100);

		if (clickGame) {
			mainGame.gameState = STATE.Game;
		}
		if (clickMultiplayer) {
			mainGame.gameState = STATE.Multiplayer;
		}
		if (clickQuit) {
			System.exit(1);
		}
	}

	public void mouseReleassed(MouseEvent e) {

	}

	public void tick() {

	}

	public void render(Graphics graph) {
		Font titlefnt = new Font("Arial", 1, 50);
		Font menufnt = new Font("Arial", 1, 35);

		drawGameMenu(graph, titlefnt);
		drawPlayerVsCPU(graph, menufnt);
		drawMultiplayer(graph, menufnt);
		drawQuit(graph, menufnt);
	}

	private void drawQuit(Graphics graph, Font menufnt) {
		graph.setFont(menufnt);
		graph.setColor(Color.WHITE);
		graph.fillRect(300, 450, 400, 100);
		graph.setColor(Color.BLACK);
		graph.drawString("Quit", 450, 520);
	}

	private void drawMultiplayer(Graphics graph, Font menufnt) {
		graph.setFont(menufnt);
		graph.setColor(Color.WHITE);
		graph.fillRect(300, 300, 400, 100);
		graph.setColor(Color.BLACK);
		graph.drawString("Multiplayer", 400, 370);
	}

	private void drawPlayerVsCPU(Graphics graph, Font menufnt) {
		graph.setFont(menufnt);
		graph.setColor(Color.WHITE);
		graph.fillRect(300, 150, 400, 100);
		graph.setColor(Color.BLACK);
		graph.drawString("Vs. John CPU", 380, 220);
	}

	private void drawGameMenu(Graphics graph, Font titlefnt) {
		graph.setFont(titlefnt);
		graph.setColor(Color.GREEN);
		graph.drawString("GAME MENU", 350, 100);
	}

	public boolean mouseOver(int mousex, int mousey, int x, int y, int width, int height) {
		if (mousex > x && mousex < x + width) {
			if (mousey > y && mousey < y + height) {
				return true;
			} else
				return false;

		} else
			return false;
	}

}
