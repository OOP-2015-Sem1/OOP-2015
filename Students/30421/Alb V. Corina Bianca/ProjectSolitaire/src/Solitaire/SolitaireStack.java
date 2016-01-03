package Solitaire;

import Cards.ClassicCard;
import Cards.Stack;
import Cards.Value;

class SolitaireStack extends Stack {
 
    public boolean isValid( ClassicCard c ) {
        if( isEmpty() )
            return( c.getValue() == Value.V_KING );
        else
            return( c.getColor() != ((ClassicCard)top()).getColor() &&
                c.getValue().getValue() == ((ClassicCard)top()).getValue().getValue() - 1 );
    }
    
    public boolean isValid( Stack s ) {
        return( isValid( ((ClassicCard)s.top()) ) );
    }
}

