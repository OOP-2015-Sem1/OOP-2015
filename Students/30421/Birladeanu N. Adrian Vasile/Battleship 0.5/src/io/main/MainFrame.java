package io.main;

import java.awt.event.ActionListener;
import javax.swing.*;
import highscore.HighScore;
import io.battle.BattlePanel;
import io.deployment.DeploymentMap;
import resources.MapModel;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8968493879892551251L;
	
	public BattlePanel battlePanel;
	private JPanel welcomePanel;
	public DeploymentMap setShipsMap;
	private MapModel playerMap;
	HighScore highscore;
	
	public MainFrame(){
		JButton seeHighScores;
		JButton start;
		ActionListener listener=new startButtonListener();
		
		
		this.setTitle("Battleship 0.5");
		welcomePanel=new JPanel();
		this.add(welcomePanel);		
		welcomePanel.setLayout(null);
		start=new JButton("Start");
		start.addActionListener(listener);
		seeHighScores=new JButton("HighScores");
		ActionListener highscoreListener=new HighscoreListener();
		seeHighScores.addActionListener(highscoreListener);
		start.setBounds(300, 350, 100, 30);
		seeHighScores.setBounds(300, 450, 100, 30);
		welcomePanel.add(start);
		welcomePanel.add(seeHighScores);
		this.setupBackground();
		this.setSize(1366, 730);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	
	public void setupBackground(){
		ImageIcon image;
		JLabel backgroundImage;
		try {
			  image=new ImageIcon("images.jpg");
			  backgroundImage=new JLabel(image);
			  backgroundImage.setBounds(0, 0, 1366, 768);
			  welcomePanel.add(backgroundImage);
			} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setupMap(){
		setShipsMap=new DeploymentMap();
		playerMap=setShipsMap.harta;
		changePanel(setShipsMap);
	}
	
	public void changePanel(JPanel newPanel){
		this.setVisible(false);
		this.getContentPane().removeAll();
		this.add(newPanel);
		this.getContentPane().invalidate();
		this.getContentPane().validate();	
		this.pack();
		this.setVisible(true);
	}

	public void setBattlePanel(){
		battlePanel=new BattlePanel(playerMap);
		this.changePanel(battlePanel);
	}
	
	public void showHighscores(){
		highscore=new HighScore();
		this.changePanel(highscore);
	}
}