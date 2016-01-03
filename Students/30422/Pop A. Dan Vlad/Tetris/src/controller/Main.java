package controller;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import gui.Game;
import gui.Login;
import gui.GameOverFrame;
import shapes.LShape;
import shapes.LineShape;
import shapes.Shape;
import shapes.SquareShape;
import shapes.ZShape;

public class Main {
	public static int matrice[][] = new int[11][15];
	public static int row, col;
	public static boolean stoped;
	public static boolean gameOver = false;
	public static boolean programExited = false;
	public static int score = 0;
	public static Random randomNB = new Random();
	public static int number;
	static Shape[] shapeObj = new Shape[4];
	static Timer timer = new Timer();
	static TimerTask task = new TimerTask() {
		public void run() {
			if (gameOver == false)
				if (Login.start)
					Move.moveDown();
		}
	};

	public static void start(Game game) {
		timer.scheduleAtFixedRate(task, 1000, 1000);
		stoped = true;
		Move.init();
		shapeObj[0] = new SquareShape();
		shapeObj[1] = new LShape();
		shapeObj[2] = new LineShape();
		shapeObj[3] = new ZShape();
		while (!programExited) {
			while (!gameOver) {
				if (stoped) {
					number = randomNB.nextInt(4) * 1;
					newShape(shapeObj[number]);
				}
				Game.repaint();
			}
		}
	}

	public static void newShape(Shape shapeObj) {
		if (gameOver == false) {
			col = 6;
			row = 0;
			score++;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (shapeObj.forma[i][j] > 10 && shapeObj.forma[i][j] < 20) {

						if (matrice[i][j + 6] + shapeObj.forma[i][j] < 120) {
							matrice[i][j + 6] = shapeObj.forma[i][j];

						} else {
							System.out.println("GAME OVER!");
							gameOver = true;
							ScoreFileReader.updateFile();
							new GameOverFrame();
						}
					}
				}
			}
			stoped = false;
		}
	}

	public static void main(String[] args) {

		Login login = new Login();
		TetrisController controller = new TetrisController();
		start(controller.game);
	}
}
