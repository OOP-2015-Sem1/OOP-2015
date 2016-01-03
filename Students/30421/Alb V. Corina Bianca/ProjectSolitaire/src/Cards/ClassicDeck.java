package Cards;

import java.awt.image.*;

public class ClassicDeck extends Deck {
   
	public ClassicDeck(){
        
    }
    public ClassicDeck( ImageObserver imgObserver ) {
        this.imgObserver = imgObserver;
        buildCards();
    }

    protected void buildCards() {
        for( int suit = 0; suit < Suit.suits.length; suit++ ) {
            for ( int value = 0; value < Value.values.length; value++ ) {
                ClassicCard c = new ClassicCard( Value.values[ value ], Suit.suits[ suit ] );
                if( imgObserver != null )
                    c.setImageObserver( imgObserver );
                push( c );
            }
        }
    }

    protected ImageObserver imgObserver;
}