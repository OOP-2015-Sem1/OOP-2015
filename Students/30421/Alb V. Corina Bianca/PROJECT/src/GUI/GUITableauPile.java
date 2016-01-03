package GUI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Object.Card;
import Object.SharedPreferences;
import main.Solitaire;

public class GUITableauPile extends JLayeredPane {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int mIdentifyPile;

	//create default arrayList of cards -- specific for each tableau pile
	private ArrayList<Card> mListOfCards;
	
	//add card to GUI pile -- add card to list  
	public void addCard(Card card) {
		add(card, new Integer(getNrOfCardsInPile()+1));
		System.out.println("nr of cards in pile "+getIdentifyPile()+"  is  "+getNrOfCardsInPile());
		mListOfCards.add(card);
	}
	
	public Card getCard(int index){
		
		return mListOfCards.get(index);
		
	}
	
	//remove card from GUI pile -- remove card from list
	public void removeCard(int i) {	
		remove(i);
		mListOfCards.remove(i);
	}
	
	public int getNrOfCardsInPile() {
		return getComponentCount();
	}

	public void setIdentifyPile(int i) {
		this.mIdentifyPile = i;
	}
	
	public int getIdentifyPile() {
		return mIdentifyPile;
	}
	
	//default arrangement -- create default list of cards specific for each tableau pile
	public GUITableauPile(int nrOfCards, ArrayList<Card> tableauPile) {
		System.out.println("GUITableauPile constructor");
	    setSize(200, 150);
	    
	    mListOfCards = new ArrayList<>();
	    
	    for(int i = 0; i < nrOfCards; i++) {
	    	
	    	tableauPile.get(i).setBounds(10, 10*i, 90, 120);
	    	tableauPile.get(i).setCardOreder(i+1);
	    	add(tableauPile.get(i), new Integer(i+1));
	    	System.out.println(i+1+"   -- add cards to pile "+nrOfCards);
	    	mListOfCards.add(tableauPile.get(i));
	    	
	    	final Card myCard = tableauPile.get(i);
	    	setIdentifyPile(nrOfCards);
	    	
	    	myCard.addMouseListener(new MouseAdapter() { 
		          public void mousePressed(MouseEvent mouseEvent) { 
		        	  
		        	  System.out.println("mouseEvent"); 
		        	  
		        	  if (!SharedPreferences.isCardCliked() && myCard.getCardBackground().equals(Color.WHITE)) {
			       		   SharedPreferences.setCardCliked(true);
			       		   myCard.setCardBackground(Color.BLUE);
			       		   SharedPreferences.setBlueCardPile(getIdentifyPile());
			       		   SharedPreferences.setBlueCardOrder(myCard.getCardOrder());
			       		   System.out.println("white "+myCard.getCardBackground());
		       	  		} else if (SharedPreferences.isCardCliked() && myCard.getCardBackground().equals(Color.BLUE)) {
		       	  			SharedPreferences.setCardCliked(false);
		       	  			SharedPreferences.setBlueCardPile(null);
		       	  			myCard.setCardBackground(Color.WHITE);
				       		System.out.println("blue");
		       	  		} else {
		       	  			if (SharedPreferences.isCardCliked()){
		       	  				System.out.println("ufhjdf");
		       	  				//add to this pile and remove from blueCard pile
		       	  				SharedPreferences.setCardOrder(myCard.getCardOrder());
		       	  				int bluePile = SharedPreferences.getBlueCardPile();
		       	  				int destinationPile = getIdentifyPile();
		       	  				int number = SharedPreferences.decideGetPile(bluePile).getNrOfCardsInPile() - SharedPreferences.getBlueCardOrder();
		       	  				System.out.println(number + " ---- "+destinationPile+"  "+bluePile);
		       	  				Solitaire.manipulatePile(bluePile, destinationPile, number);
		       	  			}
		       	  		}
		          } 
		    }); 
	    }
	}
	
}