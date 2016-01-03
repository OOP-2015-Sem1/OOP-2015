package org.game.cards;
public class Player {
    public final static int MAX_CARD = 5;
	public static final String betAmount = null;
    private Card cards[];
	public String playerName;
	public static int bank=1000;
    
    public Player() {
        cards = new Card[MAX_CARD];
    }
    
    public Card[] getCards() {
        return cards;
    }
   
    public Card getCardAtIndex(int index) {
        if (index >= 0 && index < MAX_CARD)
            return cards[index];
        else
            return null;
    }
   
    public void setCardAtIndex(Card c, int index) {
        if(index >= 0 && index < MAX_CARD)
            cards[index] = c;
    }
    
    public int countPair() {
        int count = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                if (cards[i].getFace().equals(cards[j].getFace())){
                    count++;
                }
            }
        }
        return count;
    }

    
    public boolean isFlush() {
        int count = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                if (cards[i].getSuit().equals(cards[j].getSuit())) {
                    count++;
                }
            }
        }
        if(count == 5)
            return true;
        else
            return false;
    }    
    
    public boolean isStraight() {
    	int count = 0;
    	for (int i=0; i<cards.length; i++){
    		for(int j=i+1; j<cards.length; j++){
    			if(cards[i].getFace().equals(cards[j].getFace()+1)){
    				count++;
    			}
    				
    		}
    	}
    	if(count==5)
    		return true;
    	else
    		return false;
    }
	public void loadGame(String game) {
		// TODO Auto-generated method stub
		
	}
    
    public boolean isFullHouse() {
    	int count=0;
    	for (int i=0; i<cards.length; i++){
    		for(int j=0; j<cards.length; j++){
    			if ((cards[i].getFace().equals(cards[j].getFace()) && (cards[j].getFace().equals(cards[j+1].getFace()) && (cards[j+2].getFace().equals(cards[j+3].getFace())))))
    			count=1;
    	}
    	}
    	
    	if(count==1)
    		return true;
    	else
    		return false;
	}
		
    	
    
    
}

