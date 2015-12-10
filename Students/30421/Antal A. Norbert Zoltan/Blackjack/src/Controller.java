import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ActionListener{
	private Board board;
	private Deck deck;
	
	private List<Card> dealerHand = new ArrayList<Card>();
	private List<Card> playerHand = new ArrayList<Card>();
	
	private boolean dealerDone = false;
	private boolean playerDone = false;
	
	Card hole;
	
	public Controller(){
		board = new Board(this);
	}

	public void actionPerformed(ActionEvent ae) {
		String button = ae.getActionCommand();
		if (button == "New Game"){
			startNewGame();
		}
		else if (button == "Hit"){
			if (!playerDone){
			addNewCard();
			}
		}
		else if (button == "Stand"){
			if (!playerDone){
			getResult();
			}
		}
	}
	
	private void startNewGame(){
		deck = new Deck();
		board.clear();
		dealerHand.clear();
		playerHand.clear();
		playerDone = false;
		dealerDone = false;
		for (int i = 0; i<2; i++){
			addNewCard();
		}
		if (checkScore(playerHand)==21 || checkScore(dealerHand)==21){
			getResult();
		}
	}
	
	private void addNewCard(){
		if (!playerDone){
		Card pCard = deck.getRandCard();
		playerHand.add(pCard);
		board.drawPlayer(pCard);
		int pScore = checkScore(playerHand);
		if (pScore>21){
			board.setInfo("You busted.");
			playerDone = true;
			board.showDealer();
			return;
		}
		board.setInfo("Score:"+pScore);
		}
		if (!dealerDone){
		Card hole = deck.getRandCard();
		dealerHand.add(hole);
		board.drawDealer(hole);
		int dScore = checkScore(dealerHand); 
		if (dScore>=17 && dScore<=21){
			dealerDone = true;
		}
		else if (dScore>21){
			board.setInfo("You won!");
			playerDone = true;
			dealerDone = true;
			board.showDealer();
		}
		}
	}
	
	private void getResult(){
		playerDone = true;
		int pScore = checkScore(playerHand);
		while (!dealerDone){
			Card dCard = deck.getRandCard();
			dealerHand.add(dCard);
			board.drawDealer(dCard);
			int dScore = checkScore(dealerHand); 
			if (dScore>=17 && dScore<=21){
				dealerDone = true;
			}
			else if (dScore>21){
				dealerDone = true;
				pScore = 300;
			}
		}
		board.showDealer();
		int dScore = checkScore(dealerHand);
		if (pScore>dScore){
			board.setInfo("You won!");
		}
		else if (pScore==dScore){
			board.setInfo("You tied.");
		}
		else{
			board.setInfo("You lost.");
		}
	}
	
	private int checkScore(List<Card> hand){
		int score = 0;
		boolean isAce = false;
		for (int i = 0; i<hand.size(); i++){
			int value = hand.get(i).getValue();
			if (!isAce && value==11){
				isAce = true;
			}
			score += value;
			if (isAce && score>21){
				score -= 10;
				isAce = false;
			}
			
		}
		return score;
	}
}
