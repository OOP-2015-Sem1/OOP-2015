package GUI;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Object.Card;
import Object.SharedPreferences;

public class GUIWastePile extends JLayeredPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Card> wasteCards = new ArrayList<>();
		
	public void addCard(Card card, int i) {
		
		Card wasteCard = new Card();
		wasteCard.setSuit(card.getSuit());
		wasteCard.setRank(card.getRank());
		wasteCard.setCardBackground(Color.WHITE);
		wasteCard.setBounds(10, 0, 90, 120);
		
		wasteCard.setText(wasteCard.getRank()+" "+wasteCard.getSuit());
		
		System.out.println("  "+i);
		if (i == 23){
	    	add(wasteCard, new Integer(24-i));
		} else {
			add(wasteCard, new Integer(i+1));
		}
		
		wasteCards.add(card);
		
		final Card myWasteCard = wasteCard;
		myWasteCard.addMouseListener(new MouseAdapter() {
			 public void mousePressed(MouseEvent mouseEvent) { 
				 
				 System.out.println("pressed");
				 
				 if (!SharedPreferences.isCardCliked() && myWasteCard.getCardBackground().equals(Color.WHITE)) {
		       		   SharedPreferences.setCardCliked(true);
		       		myWasteCard.setCardBackground(Color.BLUE);
	       	  			System.out.println("white "+myWasteCard.getCardBackground());
	       	  		} else if (SharedPreferences.isCardCliked() && myWasteCard.getCardBackground().equals(Color.BLUE)) {
	       	  			SharedPreferences.setCardCliked(false);
	       	  		myWasteCard.setCardBackground(Color.WHITE);
			       		System.out.println("blue");
	       	  		} else {
	       	  			System.out.println("else "+SharedPreferences.isCardCliked()+" "+myWasteCard.getCardBackground());
	       	  		}
				 	
			 }
		});
		
	}
	
	public void removeCard(Card card) {
		remove(card);
	}
	
	public int getPileSize() {
		return getComponentCount();
	}
	
	public Card getCardAtIndex(int i) {
		return wasteCards.get(i);
	}
	
	public void emptyAll(){
		removeAll(); 
	}
	
	public GUIWastePile() {
		
		
	}
}