package Object;

import java.awt.Color;
import java.util.ArrayList;

import Logic.RandomLogic;

public class Deck {
	
	private String[] suits = {"Spade", "Heart", "Diamond", "Club"};
	private String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Qween", "King"};
	private Card card;
	private static ArrayList<Card> randomPileCards;
	private static ArrayList<Card> deck;
	
	private static RandomLogic random = new RandomLogic();
	
	public Deck(){
		
		System.out.println("create deck");
		
		deck = new ArrayList<>();
		
		for (int i = 0; i < 52; i++){
			card = new Card();
			card.setRank(suits[i % 4]);
			card.setSuit(ranks[i % 13]);
		
			deck.add(card);
		}
	}
	
	public ArrayList<Card> getRandomCards(int nrOfRandomCards) {
		
		randomPileCards = new ArrayList<>();
		
		//return nrOfRandomCards cards from deck to create pile
		for (int i = 0; i < nrOfRandomCards; i++) {
	
			int randomCard = random.getRandom(deck.size());
	    	System.out.println(deck.size()+"  "+i);
	    	Card card = deck.get(randomCard);
			card.setCardBackground(Color.WHITE);
		   	card.setText(card.getSuit()+" "+card.getRank());
		   	deck.remove(randomCard);	    	
	    	
	    	randomPileCards.add(card);
		}
    	
		return randomPileCards;
	}
	
}