package GameMechanics;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import UI.Board;

public class Game {

	//public JFrame gameBoard= new Board("Monopoly");
	public static final TileArray TILE_ARRAY= new TileArray();
	public static Player[] player;
	public Object[] availableIcons = { "boot", "thimble", "car", "dog", "barrow", "hat" };
	private int nrOfIconsLeft= 6;
	
	public static Player[] getPlayer() {
		return player;
	}

	public static Integer numberOfPlayers;
	
	public Game(){
		//ask number of players
		
		numberOfPlayers= Board.askNumberOfPlayers();
		setPlayers();
		
		//run game
		
		//while(gameOver()!= true){
			
		//}
		
		
	}
	private boolean gameOver(){
		int nonBankruptPlayers=0;
		int possibleWinner=0;
		
		for(int i=0; i<numberOfPlayers; i++){
			if (player[i].isBankrupt==false){
				nonBankruptPlayers++;
				possibleWinner=i;
			}
		}
		if (nonBankruptPlayers==1){
			 String message = player[possibleWinner].name + " won!";
				    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
				        JOptionPane.ERROR_MESSAGE);
			return true;
			}
		else return false;
		
	}
	
	private void setPlayers(){
		player=new Player[numberOfPlayers];
		for(int i=0;i<numberOfPlayers;i++){
			player[i]= new Player();
			player[i].setName();
			String unavailableIcon= player[i].setToken(availableIcons);
			resetAvailableIcons(unavailableIcon);
			player[i].position= 0;
		}
	}
	
	public void resetAvailableIcons(String usedIcon){
		Object[] newIconArray= new Object[nrOfIconsLeft-1];
		int newIconArrayCounter=-1;
		for(int i=0;i<nrOfIconsLeft;i++){
			if (usedIcon!= availableIcons[i].toString()){ 
				newIconArrayCounter++;
				newIconArray[newIconArrayCounter]=availableIcons[i];
				
			}
			
		}
		nrOfIconsLeft--;
		availableIcons= newIconArray;
	}
}
