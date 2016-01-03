package Cards;

import java.util.*;

public abstract class Deck extends Stack {
    /**
     * Shuffles the deck.
     */
    public void shuffle( ) {
        //We transfer the deck in a vector temporarily
        Vector v = new Vector();
        while( !isEmpty() )
            v.addElement( pop() );

        Random aRandom = new Random();
        
        //We push randomly selected cards on the empty deck
        while( !v.isEmpty() ) {
            int randomCard=aRandom.nextInt((int)v.size());
            Card c = (Card) v.elementAt( randomCard );
            push( c );
           v.removeElement( c );
        }
    }
}