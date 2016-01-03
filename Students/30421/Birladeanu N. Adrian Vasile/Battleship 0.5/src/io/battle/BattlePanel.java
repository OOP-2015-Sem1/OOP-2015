package io.battle;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

import highscore.HighScore;
import io.deployment.MapDeploymentListener;
import io.main.startButtonListener;
import resources.Main;
import resources.MapModel;

public class BattlePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2940694853397019523L;
	
	private BattleMapModel playerMap;
	private BattleMapModel AIMap;
	private int score;
	private static JLabel scoreLabel;
	private JPanel mapsPanel;
	private JButton newGame=new JButton("New Game");
	private JPanel buttonPanel;
	
	public BattlePanel(MapModel playerMap){
		score=0;
		this.playerMap=new BattleMapModel(playerMap);
		this.AIMap=new BattleMapModel();
		AIMap.simulateAI();
		AIMap.interactiveMap.recolourMap();
		AIMap.addBattleListener();
		this.setSize(1366, 730);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scoreLabel=new JLabel("Score: "+ score);
		this.add(scoreLabel);
		this.add(Box.createRigidArea(new Dimension(0, 40)));
		this.setMaps();
		this.add(Box.createRigidArea(new Dimension(0, 40)));
		buttonPanel=new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(newGame);
		ActionListener listener=new startButtonListener();
		newGame.addActionListener(listener);
		this.add(buttonPanel);		
	}
	
	public void setMaps(){
		mapsPanel=new JPanel();
		mapsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		mapsPanel.add(Box.createRigidArea(new Dimension(55, 0)));
		mapsPanel.add(AIMap.interactiveMap);
		mapsPanel.add(Box.createRigidArea(new Dimension(55, 0)));
		playerMap.interactiveMap.setPreferredSize(new Dimension(600, 600));
		playerMap.colourMap();
		mapsPanel.add(playerMap.interactiveMap);
		AIMap.interactiveMap.setPreferredSize(new Dimension(600, 600));
		mapsPanel.add(Box.createRigidArea(new Dimension(55, 0)));
		this.add(mapsPanel);
		MapDeploymentListener.disable();
	}
	
	public void setScore(){
		score=AIMap.getScore()-playerMap.getScore();
		scoreLabel.setText("Score: "+score);
		
	}
	
	public int checkEndGame(){
		this.setScore();
		Main.game.setVisible(true);
		if(playerMap.getScore()==21){
			JOptionPane.showMessageDialog(null, "You lost, LOOSER!");
			Main.game.showHighscores();
			return 1;
		}
		if(AIMap.getScore()==21){
			String name=JOptionPane.showInputDialog
					("You won! \n Enter your name");
			HighScore.appendScores(name, this.score);
			Main.game.showHighscores();
			return 1;
		}
		return 0;
	}
	
	public void battle(){
		int a=this.checkEndGame();
		if(a==0){
			BattleListener.disable();
			playerMap.simulateHit();
			a=this.checkEndGame();
			BattleListener.enable();	
		}
		
	}
}
