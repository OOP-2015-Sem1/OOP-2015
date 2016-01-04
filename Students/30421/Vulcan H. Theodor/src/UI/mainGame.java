package UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import GameMechanics.Player;
import GameMechanics.TileArray;


public class mainGame {
	
	private static int nrOfIconsLeft= 6;
	public static Integer numberOfPlayers;
	public static final TileArray TILE_ARRAY= new TileArray();
	public static Player[] player;
	public static Player activePlayer;
	public static Object[] availableIcons = { "boot", "thimble", "car", "dog", "barrow", "hat" };
	
	
	public static void main(String[] args) {
		
			//ask number of players
			numberOfPlayers= Board.askNumberOfPlayers();
			setPlayers();
		
			//initialize board
			Board mainFrame= new Board("Monopoly");
			mainFrame.setVisible(true);
			mainFrame.setSize(1500, 1030);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setResizable(false);

			//run game
			int playerCounter= 0;
			//while(gameOver()!= true){
				if (playerCounter==4) playerCounter=0;
				activePlayer= player[playerCounter];
				activePlayer.hasRolled=false;
				
			//}
			
	}
	
	public static Player[] getPlayer() {
		return player;
	}
	private static boolean gameOver(){
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
	
	private static void setPlayers(){
		player=new Player[numberOfPlayers];
		for(int i=0;i<numberOfPlayers;i++){
			player[i]= new Player();
			player[i].setName();
			String unavailableIcon= player[i].setToken(availableIcons);
			resetAvailableIcons(unavailableIcon);
			player[i].position= 0;
		}
	}
	
	public static void resetAvailableIcons(String usedIcon){
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
