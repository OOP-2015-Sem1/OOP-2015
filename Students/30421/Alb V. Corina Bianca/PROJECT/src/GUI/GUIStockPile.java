package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import Object.Card;
import main.Solitaire;

public class GUIStockPile extends JLayeredPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int countCards;
	
	public GUIStockPile(int nrOfCards, ArrayList<Card> tableauPile) {
				
		countCards = nrOfCards;
		
		for(int i = 0; i < nrOfCards; i++) {
					    	
			tableauPile.get(i).setBounds(10, 0, 90, 120);
		   	add(tableauPile.get(i), new Integer(nrOfCards-i));
				  		 
		   	final Card myCard = tableauPile.get(i);
		   	final int index = i;
		   	
		   	myCard.addMouseListener(new MouseAdapter() { 
		          public void mousePressed(MouseEvent mouseEvent) { 
			            System.out.println("mouseEvent"); 
			            Solitaire.getsWastePile().addCard(myCard, index); 
			            remove(myCard);
						countCards --;
			
		   	if (countCards == 0) {
		   		System.out.println("count cards is 0 " + Solitaire.getsWastePile().getPileSize());
		   		countCards = Solitaire.getsWastePile().getPileSize();
		   		
		   		for (int j = 0; j < countCards; j++) {
		   			add(Solitaire.getsWastePile().getCardAtIndex(j), new Integer(j+1));
		   		}
		   		Solitaire.getsWastePile().emptyAll();
		   	}
		    }
		    }); 
		}
	}	
}