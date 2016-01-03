package display;

import java.awt.Color;
import java.awt.Graphics;

import theGame.MainGame;

public class Score {

	public static int SCORE1 = 0;
	public static int SCORE2 = 0;

	public void tick() {
		
	}
	
	public void render(Graphics graph){
		drawPlayer1Score(graph);
		drawPlayer2Score(graph);
	}

	private void drawPlayer1Score(Graphics graph) {
		graph.setColor(Color.DARK_GRAY);
		graph.fillRect(20, 10, 150, 30);
		graph.setColor(Color.WHITE);
		graph.drawString("PLAYER 1 SCORE : " + SCORE1 , 30,30);
	}

	private void drawPlayer2Score(Graphics graph) {
		graph.setColor(Color.DARK_GRAY);
		graph.fillRect(MainGame.WIDTH-230, 10, 150, 30);
		graph.setColor(Color.WHITE);
		graph.drawString("PLAYER 2 SCORE : " + SCORE2 , MainGame.WIDTH-220,30);
	}
	
}
