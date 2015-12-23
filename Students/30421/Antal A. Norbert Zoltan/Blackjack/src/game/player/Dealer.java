package game.player;

import game.board.Board;
import game.cards.Deck;

public class Dealer extends Player{
	public Dealer(Deck deck, Board board){
		super(deck, board);
	}
	
	public void drawPlayer(){
		board.drawDealer(hand.get(hand.size()-1));
	}
}
