package gui;
import java.awt.*;


import javax.swing.*;

import controller.Main;
public class Game {
	public static JFrame jframe;
	private JPanel mainPanel;
	public static JPanel Game;
	public JPanel Stats;
	private static JButton Board[][];
	private static JTextField Score;
	public Game(){
		
		jframe = new JFrame();
		jframe.setSize(500, 700);
		jframe.setTitle("Tetris");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		Score = new JTextField();
		Game = new JPanel();
		Game.setLayout(new GridLayout(10, 11));
		Board = new JButton[10][11];
		for(int i= 0; i<10;i++)
			for(int j= 0; j<11;j++){
				Board[i][j]= new JButton();
				if(i==9||j==0||j==10){
					Board[i][j].setBackground(Color.BLACK);
				}
				else{
					Board[i][j].setBackground(Color.WHITE);
				}
					
				Game.add(Board[i][j]);
			}

		Score= new JTextField("0");

		mainPanel.add(Game, BorderLayout.CENTER);
		mainPanel.add(Score, BorderLayout.NORTH);	
		jframe.add(mainPanel);
		Game.setVisible(true);
		mainPanel.setVisible(true);
		jframe.setVisible(false);
		Game.setFocusable(true);
		Game.requestFocusInWindow();
			
	}
	public static void repaint(){
		
		for(int i= 0; i<9;i++)
			for(int j= 1; j<10;j++){
				if(Main.matrice[i][j+2]==0){
					Board[i][j].setBackground(Color.WHITE);
				}
				if(Main.matrice[i][j+2]==11||Main.matrice[i][j+2]==111){
					Board[i][j].setBackground(Color.RED);
				}
				if(Main.matrice[i][j+2]==12||Main.matrice[i][j+2]==112){
					Board[i][j].setBackground(Color.BLUE);
				}
				if(Main.matrice[i][j+2]==13||Main.matrice[i][j+2]==113){
					Board[i][j].setBackground(Color.GREEN);
				}
				if(Main.matrice[i][j+2]==14||Main.matrice[i][j+2]==114){
					Board[i][j].setBackground(Color.YELLOW);
				}
				if(Main.matrice[i][j+2]==200){
					Board[i][j].setBackground(Color.BLACK);
				}
			}
		Score.setText("" + Main.score);
	}	
}
