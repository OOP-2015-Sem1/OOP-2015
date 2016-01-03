package project.memorygame.models;
/**
 * This provides a collection of all the cards used in a game
 * and also the matching logic
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Timer;

import project.memorygame.controllers.Game;

public class Deck {
	public List<Card> cards;
	 private Card selectedCard=null;
	    private Card c1=null;
	    private Card c2=null;
	    private Timer t;
	    public Game game;
	
	
	public Deck(Game game,int pairs){
		List<Card> cardsList = new ArrayList<Card>();
		List<Integer> cardId = new ArrayList<Integer>();
		this.game=game;
	
		//adds duplicate ids in a holder list of integers to randomize the card positions
		for (int i = 0; i < pairs; i++){
            cardId.add(i);
            cardId.add(i);
        }
        Collections.shuffle(cardId);//randomizes the positions of the ids in the vector

        for (int val : cardId){
            Card c = new Card();
            c.setId(val);
            c.addActionListener(new ActionListener(){//adds actions to every button on the board
            	public void actionPerformed(ActionEvent e){
            		selectedCard = c;
            		if (c1 == null && c2 == null){
                        c1 = selectedCard;
                        c1.flip();
                    }

                    if (c1 != null && c1 != selectedCard && c2 == null){
                        c2 = selectedCard;
                        c2.flip();
                        t.start();
                        
                    }
            	}
            });
            cardsList.add(c);
        }
        this.cards=cardsList;
      //set up the timer
        t = new Timer(750, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                checkIfCardsMatch();
            }
        });

        t.setRepeats(false);
	}

    public void checkIfCardsMatch(){
        if (c1.getId() == c2.getId()){//match condition
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()){
                game.enterWinMenu();
            }
        }

        else{
            c1.flip(); //turns back both cards
            c2.flip();
        }
        c1 = null; //reset c1 and c2
        c2 = null;
    }

    public boolean isGameWon(){//verifies if all the cards are matched
        for(Card c: this.cards){
            if (c.getMatched() == false){
                return false;
            }
        }
        return true;
    }
}
