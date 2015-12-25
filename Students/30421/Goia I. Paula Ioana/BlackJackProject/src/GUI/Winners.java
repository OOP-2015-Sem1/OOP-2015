package GUI;


import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Winners {
	
	private JFrame winFrame;
	private ArrayList<String> winners;
	private ArrayList<String> playersInformation;
	private ArrayList<JLabel> win;
	private  ArrayList<JLabel> info;
	private JPanel infoPanel;
	private JPanel winPanel;
	
	
	public Winners(ArrayList<String> winners , ArrayList<String> playersInformation){
		this.winners = winners;
		this.playersInformation =  playersInformation;
		this.win = new ArrayList<>();
		this.info = new ArrayList<>();
		this.winFrame = new JFrame();
		this.infoPanel = new JPanel();
		this.winPanel = new JPanel();
		this.winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.winFrame.setSize(500, 700);
		this.winFrame.setVisible(true);
		displayResults();
		this.winFrame.setLayout(new GridLayout(2,1));
		addInFrame();
		this.winFrame.add(this.infoPanel);
		this.winFrame.add(this.winPanel);
		
	}
	
	private void addInFrame(){
		//I added 14 in this place because I wanted to have a space that will make the difference betweem the info results and the actual winners
		this.infoPanel.setLayout(new GridLayout(14,1,5,5));
		for (JLabel infoPlayer : this.info){
			this.infoPanel.add(infoPlayer);
		}
		this.winPanel.setLayout(new GridLayout(13,1,5,5));
		for (JLabel winnerPlayer : this.win){
			this.winPanel.add(winnerPlayer);
		}
	}
	
	private void displayResults(){
		for( int i = 0 ; i < this.playersInformation.size() ; i++){
			JLabel aux = new JLabel(this.playersInformation.get(i));
			this.info.add(aux);
		}
		this.winPanel.add(new JLabel("   The Winners are: "));
		for( int i = 0 ; i < this.winners.size() ; i++){
			JLabel aux = new JLabel("   "+this.winners.get(i));
			this.win.add(aux);
		}

		
	}

}
