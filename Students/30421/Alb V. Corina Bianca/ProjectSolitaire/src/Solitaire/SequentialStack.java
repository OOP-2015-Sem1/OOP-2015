package Solitaire;

import Cards.ClassicCard;
import Cards.Stack;
import Cards.Value;

class SequentialStack extends Stack {

    public boolean isValid( ClassicCard c ) {
        if( isEmpty() )
            return( c.getValue() == Value.V_ACE );
        else
            return( c.getSuit() == ((ClassicCard)top()).getSuit() &&
                c.getValue().getValue() == ((ClassicCard)top()).getValue().getValue() + 1 );
    }

    public boolean isValid( Stack s ) {
        return( s.cardCount() == 1 && isValid( ((ClassicCard)s.top()) ) );
    }
}