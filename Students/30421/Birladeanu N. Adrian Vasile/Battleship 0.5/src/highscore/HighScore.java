package highscore;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import io.main.startButtonListener;

public class HighScore extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3858688671439658070L;
	
	private static ArrayList<Score> highscores;
	
	public HighScore(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(Box.createRigidArea(new Dimension(0, 100)));
		
		JPanel titlePanel=new JPanel();
		this.add(titlePanel);
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel titleLabel=new JLabel("Highscores");
		titlePanel.add(titleLabel);
		this.add(Box.createRigidArea(new Dimension(0, 100)));
		HighScore.readHighscores();
		if(highscores.size()==0){
			
		}
		else {
			int size=highscores.size();
			int score;
			String name;
			JLabel[] scoreLabel=new JLabel[size];
			for(int i=0; i<size; i++){
				Score temporaryScore=highscores.get(0);
				score=temporaryScore.getScore();
				name=temporaryScore.getName();
				scoreLabel[i]=new JLabel((i+1)+"."+name+"     "+score);
				this.add(scoreLabel[i]);
				this.add(Box.createRigidArea(new Dimension(0, 50)));
			}
		}
		JButton newGame=new JButton("New Game");
		this.add(newGame);
		ActionListener listener=new startButtonListener();
		newGame.addActionListener(listener);
		this.add(Box.createRigidArea(new Dimension(700, 100)));
	}
	
	public static void readHighscores(){
		highscores=new ArrayList<Score>();
		File file=new File("Highscores.txt");
		try{
			Scanner scanner=new Scanner(new FileReader(file));
			while(scanner.hasNext()){
				String name=scanner.next();
				int auxiliaryScore=scanner.nextInt();
				Score score=new Score(name, auxiliaryScore);
				highscores.add(score);
			}
			scanner.close();
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "File not found");
		}
	}
	
	public static void appendScores(String name, int score){
		try{
			FileWriter fileWriter=new FileWriter("Highscores.txt", true);
			fileWriter.write(name+" "+score+" \n");
			fileWriter.close();
		}
		catch(Exception e){
			File file=new File("Highscores.txt");
			try {
				file.createNewFile();
				appendScores(name, score);
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
