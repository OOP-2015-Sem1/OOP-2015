package userInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

import javax.swing.*;

import gameLogic.*;

public class Board implements MouseListener {
	private TileMatrix tileMatrix = new TileMatrix();
	private int type;
	private Icon icon;
	private Icon flagIcon = new ImageIcon("resources//Minesweeper_flag.png");
	JPanel mainPanel = new JPanel();
	JPanel infoPanel = new JPanel();
	JFrame frame = new JFrame();
	JTextArea loseWinArea, flagArea;
	JTextArea timeArea = new JTextArea();
	private int width = tileMatrix.getWidth();
	private int height = tileMatrix.getHeight();
	private int flagsLeft = tileMatrix.getNrOfMines();
	private int i = 0, j = 0;
	private JButton[][] buttonsMatrix = new JButton[width][height];
	private int elapsedTime = 0;
	private int nrOfMines = tileMatrix.getNrOfMines();
	private int markedMines = 0;
	private boolean isGameOver = false;
	private static final int MINE_TILE = -1;
	private static final int EMPTY_TILE = 0;
	private boolean[][] visited=new boolean[width][height];
	Runnable increaseTime = new Runnable() {
		public void run() {
			if (isGameOver == false) {
				timeArea.setText("Elapsed time: " + elapsedTime);
				elapsedTime++;
			}
		}
	};
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	public Board() {
		executor.scheduleAtFixedRate(increaseTime, 0, 1, TimeUnit.SECONDS);
		for (i = 0; i < width; i++) {
			for (j = 0; j < height; j++) {
				buttonsMatrix[i][j] = new JButton();
				buttonsMatrix[i][j].setBackground(Color.BLUE);
				mainPanel.add(buttonsMatrix[i][j]);
				buttonsMatrix[i][j].addMouseListener(this);
			}
		}
		mainPanel.setLayout(new GridLayout(width, height));
		mainPanel.setSize(width * 20, height * 20);// *20 because each tile is
													// 20x20
		infoPanel.setLayout(new GridLayout(1, 2, 10, 10));
		flagArea = new JTextArea(flagsLeft + " flags left");
		flagArea.setEditable(false);
		timeArea.setEditable(false);
		infoPanel.add(flagArea);
		infoPanel.add(timeArea);
		frame.add(mainPanel, BorderLayout.CENTER);
		frame.add(infoPanel, BorderLayout.NORTH);
		frame.setSize(width * 20, height * 20);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (buttonsMatrix[i][j] == e.getSource()) {
					if (SwingUtilities.isLeftMouseButton(e) == true) {
						icon = buttonsMatrix[i][j].getIcon();
						if (icon == flagIcon) {
							flagsLeft++;
							flagArea.setText(flagsLeft + " flags left");
						}
						type = tileMatrix.getTypeFromMatrix(i, j);
						showTile(i, j);
						if (type == MINE_TILE) {
							loseWinArea = new JTextArea("BANG! You lost!");
							frame.add(loseWinArea, BorderLayout.NORTH);
							gameOver();
						}
						if (type == EMPTY_TILE) {
							showAllNeighbours(i, j);
						}
					}
					if (SwingUtilities.isRightMouseButton(e) == true && flagsLeft > 0) {
						icon = buttonsMatrix[i][j].getIcon();
						if (icon != flagIcon) {
							icon = flagIcon;
							buttonsMatrix[i][j].setIcon(icon);
							flagsLeft--;
							flagArea.setText(flagsLeft + " flags left");
						} else {
							icon = null;
							buttonsMatrix[i][j].setIcon(icon);
							flagsLeft++;
							flagArea.setText(flagsLeft + " flags left");
						}
					}
					if (flagsLeft == 0) {
						checkForWin();
					}
				}
			}
		}
	}

	public void checkForWin() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				icon = buttonsMatrix[i][j].getIcon();
				type = tileMatrix.getTypeFromMatrix(i, j);
				if (type == MINE_TILE && icon == flagIcon) {
					markedMines++;
				}
			}
		}
		if (markedMines == nrOfMines) {
			loseWinArea = new JTextArea("You won! Your time is: " + elapsedTime);
			frame.add(loseWinArea, BorderLayout.NORTH);
			gameOver();
		}
	}

	public void showAllNeighbours(int x, int y) {
		visited[x][y]=true;
		if (x - 1 >= 0 && y - 1 >= 0) {
			showTile(x - 1, y - 1);
			if(visited[x-1][y-1]==false && tileMatrix.getTypeFromMatrix(x-1, y-1)==EMPTY_TILE){
				showAllNeighbours(x-1, y-1);
			}
		}
		if (y - 1 >= 0) {
			showTile(x, y - 1);
			if(visited[x][y-1]==false && tileMatrix.getTypeFromMatrix(x, y-1)==EMPTY_TILE){
				showAllNeighbours(x, y-1);
			}
		}
		if (y - 1 >= 0 && x + 1 < width) {
			showTile(x + 1, y - 1);
			if(visited[x+1][y-1]==false && tileMatrix.getTypeFromMatrix(x+1, y-1)==EMPTY_TILE){
				showAllNeighbours(x+1, y-1);
			}
		}
		if (x - 1 >= 0) {
			showTile(x - 1, y);
			if(visited[x-1][y]==false && tileMatrix.getTypeFromMatrix(x-1, y)==EMPTY_TILE){
				showAllNeighbours(x-1, y);
			}
		}
		if (x + 1 < width) {
			showTile(x + 1, y);
			if(visited[x+1][y]==false && tileMatrix.getTypeFromMatrix(x+1, y)==EMPTY_TILE){
				showAllNeighbours(x+1, y);
			}
		}
		if (x - 1 >= 0 && y + 1 < height) {
			showTile(x - 1, y + 1);
			if(visited[x-1][y+1]==false && tileMatrix.getTypeFromMatrix(x-1, y+1)==EMPTY_TILE){
				showAllNeighbours(x-1, y+1);
			}
		}
		if (y + 1 < height) {
			showTile(x, y + 1);
			if(visited[x][y+1]==false && tileMatrix.getTypeFromMatrix(x, y+1)==EMPTY_TILE){
				showAllNeighbours(x, y+1);
			}
		}
		if (y + 1 < height && x + 1 < width) {
			showTile(x + 1, y + 1);
			if(visited[x+1][y+1]==false && tileMatrix.getTypeFromMatrix(x+1, y+1)==EMPTY_TILE){
				showAllNeighbours(x+1, y+1);
			}
		}
	}

	public void showTile(int i, int j) {
		icon = tileMatrix.getIconFromMatrix(i, j);
		buttonsMatrix[i][j].setIcon(icon);
		buttonsMatrix[i][j].setDisabledIcon(icon);
		buttonsMatrix[i][j].setEnabled(false);
	}

	public void gameOver() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				type = tileMatrix.getTypeFromMatrix(i, j);
				icon = tileMatrix.getIconFromMatrix(i, j);
				buttonsMatrix[i][j].setIcon(icon);
				buttonsMatrix[i][j].setDisabledIcon(icon);
				buttonsMatrix[i][j].setEnabled(false);
				isGameOver = true;
			}
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
