package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class PlayersName implements ActionListener {

	private JFrame playerName;
	private int nbPlayers;
	private static ArrayList<String> namePlayers;
	private JPanel namePanel;
	private JButton saveName ;
	private JPanel saveNamePanel;
	private ArrayList<JTextField> arrayPlayers;
	
	public PlayersName(int nbPlayers){
		playerName = new JFrame();
		playerName.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerName.setSize(800,500);  
		playerName.setVisible(true);
		this.playerName.setLayout(new GridLayout(2, 1));
		this.namePanel = new JPanel();
		this.saveNamePanel = new JPanel();
		PlayersName.namePlayers = new ArrayList<>();
		this.arrayPlayers = new ArrayList<>();
		this.nbPlayers = nbPlayers;
		this.saveName = new JButton("Save");
		setPlace();	
		this.saveNamePanel.add(saveName);
		this.playerName.add(this.namePanel);
		this.playerName.add(this.saveNamePanel);
		addActionListeners();
	}
	
	private void setPlace(){
		this.namePanel.setLayout(new GridLayout(this.nbPlayers, 2));
		///Yeah,I know this part of code is repeated but I wanted to outline with which player the user of the game will play,
		//because initially it was also added in the for loop ,but I think in this way is less confusing 
		JLabel myname = new JLabel("Your name ");
		this.namePanel.add(myname);
		JTextField  myNamePlayer = new JTextField("Your Name " );
		this.arrayPlayers.add(myNamePlayer);
		this.namePanel.add(arrayPlayers.get(0));
		for(int i = 1 ; i < this.nbPlayers ; i++){
			JLabel name = new JLabel("Name of player "+ i);
			this.namePanel.add(name);
			JTextField  namePlayer = new JTextField("Player " + i);
			this.arrayPlayers.add(namePlayer);
			this.namePanel.add(arrayPlayers.get(i));
		}	
	}

	private void addActionListeners(){
		 this.saveName.addActionListener(this);
	 }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() == this.saveName ){
			for (JTextField s : this.arrayPlayers){
				PlayersName.namePlayers.add(s.getText());
			}
			PlayersName.namePlayers.add("Dealer");
			this.playerName.setVisible(false);
			
			new GameApplication(this.nbPlayers);
		}
	}
	
	public static ArrayList<String> getNamePlayers() {
		return namePlayers;
	}

}
