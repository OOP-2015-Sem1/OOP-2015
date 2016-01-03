package org.game.cards;
public class Game {
    private Player[] players;
    private Deck deck;

    public Game() {
        deck = new Deck();
        players = new Player[4];
        players[0] = new Player();
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();
        deck.shuffle();
    }
    
    public void dealCards() {
        int count = 0;
        for (int i = 0; i < players[0].getCards().length; i++) {
            for (int j = 0; j < players.length; j++) {
                players[j].setCardAtIndex(deck.getCard(count++), i);
            }
        }
    }
    
    public void showCards() {
        for (int i = 0; i < players.length; i++) {
            System.out.print("Player " + (i + 1) + ": ");
            for (int j = 0; j < players[0].getCards().length; j++) {
                System.out.print("{" + players[i].getCardAtIndex(j).toString()+"} ");
            }
            if(players[i].countPair()> 0)
                System.out.print("\nPAIR(S):" + players[i].countPair()+ "! ");
            if(players[i].isFlush())
                System.out.print("FLUSH!!\n");
            System.out.println("\n------------------------------------");
            if(players[i].isStraight())
            	System.out.print("Straight!!\n");
            System.out.println("\n------------------------------------");
           /* if(players[i].isFullHouse())
            	System.out.print("Full House!!\n");
            System.out.println("\n------------------------------------");*/
            
        }
    }
}