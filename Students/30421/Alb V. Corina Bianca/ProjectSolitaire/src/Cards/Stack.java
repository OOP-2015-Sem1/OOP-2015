package Cards;

import java.awt.*;
import java.util.*;

public class Stack {

    public static final int SPREAD_NONE     = 0;
    public static final int SPREAD_NORTH    = 1;
    public static final int SPREAD_EAST     = 2;
    public static final int SPREAD_SOUTH    = 3;
    public static final int SPREAD_WEST     = 4;

    public  Stack() {
        cards = new Vector();
        setLocation( 0, 0 );
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    public Vector getCards() {
        return cards;
    }
    
    public int firstFaceUp(){
        for( int i = 0; i < cards.size(); i++ ) {
            ClassicCard c = ((ClassicCard)cards.get( i ));
            if( !c.isFaceDown() )
                return i;
        }
        return -1;
    }

    public Card top() {
        if( cards.size() == 0 )
            return null;
        else
            return (Card)( cards.elementAt( cards.size() - 1 ) );
    }

    public Card elementAt( int index ) {
        return (Card)(cards.elementAt( index ));
    }

    public void push( Card c ) {
        cards.addElement( c );
        c.setLocation( nextCardLocation );
        switch( spreadDirection ) {
            case SPREAD_NORTH : nextCardLocation.y -= spreadingDelta; break;
            case SPREAD_EAST : nextCardLocation.x += spreadingDelta; break;
            case SPREAD_SOUTH : nextCardLocation.y += spreadingDelta; break;
            case SPREAD_WEST : nextCardLocation.x -= spreadingDelta; break;
        }
    }

    public void push( Stack sc ) {
        while (!sc.isEmpty()) {
           push( (Card) sc.pop() );
        }
    }

    public Card pop() {
        Card c = top();
        cards.removeElement( c );
        switch( spreadDirection ) {
            case SPREAD_NORTH : nextCardLocation.y += spreadingDelta; break;
            case SPREAD_EAST : nextCardLocation.x -= spreadingDelta; break;
            case SPREAD_SOUTH : nextCardLocation.y -= spreadingDelta; break;
            case SPREAD_WEST : nextCardLocation.x += spreadingDelta; break;
        }
        return c;
    }

    public Stack pop( int n ) {
        Stack s = new Stack();

        for( int i = n; i > 0 && !isEmpty() ; i-- )
            s.push( pop() );
        s.setSpreadingDelta( spreadingDelta );
        s.setSpreadingDirection( spreadDirection );

        return s;
    }

    public Stack pop( Card c ) {
        Stack s = new Stack();

        while(!top().equals( c ) && !isEmpty()) {
            s.push( pop() );
        }
        //We also take the card c
        if( !isEmpty() )
            s.push( pop() );

        s.setSpreadingDelta( spreadingDelta );
        s.setSpreadingDirection( spreadDirection );

        return s;
    }

    public int cardCount() {
       return cards.size();
    }

    public boolean contains( Card c ) {
        return cards.contains( c );
    }

    public boolean contains( Point p ) {
        Rectangle rect = null;
        switch( spreadDirection ) {
            case SPREAD_NONE :
                rect = new Rectangle( location.x, location.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT );
                break;
            case SPREAD_NORTH :
                int height = Card.DEFAULT_HEIGHT + (cards.size()-1) * spreadingDelta;
                rect = new Rectangle( location.x - height, location.y, height, Card.DEFAULT_WIDTH );
                break;
            case SPREAD_EAST :
                rect = new Rectangle( location.x, location.y, Card.DEFAULT_WIDTH + (cards.size()-1) * spreadingDelta, Card.DEFAULT_HEIGHT );
                break;
            case SPREAD_SOUTH :
                rect = new Rectangle( location.x, location.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT + (cards.size()-1) * spreadingDelta );
                break;
            case SPREAD_WEST :
                int width = Card.DEFAULT_WIDTH + (cards.size()-1) * spreadingDelta;
                rect = new Rectangle( location.x - width, location.y, width, Card.DEFAULT_HEIGHT );
                break;
        }
        return( rect.contains( p ) );
    }

    public  boolean isValid( Card c ) {
        return( true );
    }

    public  boolean isValid( Stack c ) {
        return( true );
    }

    public int getSpreadingDirection() {
        return spreadDirection;
    }

    public void setSpreadingDirection( int sd ) {
        spreadDirection = sd;
    }

    public int getSpreadingDelta() {
        return spreadingDelta;
    }

    public void setSpreadingDelta( int delta ) {
        spreadingDelta = delta;
    }

    public void paint( Graphics g, boolean hint ) {
        if( isEmpty() ) {
            Point loc = getLocation();
            g.setColor( Color.darkGray );
            g.fillRect( loc.x, loc.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT );
            g.setColor( Color.black );
            g.drawRect( loc.x, loc.y, Card.DEFAULT_WIDTH, Card.DEFAULT_HEIGHT );
        }
        else
            for( Enumeration e = cards.elements(); e.hasMoreElements(); ) {
                Card c = (Card)(e.nextElement());
                c.paint( g, hint );
            }
    }

    public Card getClickedCard( Point p ) {
        boolean cardFound = false;
        Card c = null;
        for( int i = cards.size() - 1; !cardFound && i >= 0; i-- ) {
            c = (Card)(cards.elementAt( i ));
            cardFound = c.contains( p );
        }
        return( c );
    }

    public void reverse() {
       Vector v = new Vector();

       while(!isEmpty())
          v.addElement( pop() );

        cards = v;
    }

    public void setLocation( int x, int y ) {
        location = new Point( x, y );
        if( cards != null ) {
            for( Enumeration e = cards.elements(); e.hasMoreElements(); ) {
                Card c = (Card)(e.nextElement());
                c.setLocation( x, y );
                switch( spreadDirection ) {
                    case SPREAD_NORTH : y -= spreadingDelta; break;
                    case SPREAD_EAST : x += spreadingDelta; break;
                    case SPREAD_SOUTH : y += spreadingDelta; break;
                    case SPREAD_WEST : x -= spreadingDelta; break;
                }
            }
        }
        setNextCardLocation( x, y );
    }

    public Point getLocation() {
        return location;
    }

    public String toString() {
        return cards.toString();
    }

    private void setNextCardLocation( int x, int y ) {
        nextCardLocation = new Point( x, y );
    }

//    private void setNextCardLocation(Point nextCardLocation) {
//        this.nextCardLocation = nextCardLocation;
//    }
//
//    private Point getNextCardLocation() {
//        return nextCardLocation;
//    }

    private     Vector      cards;
    private     Point       location;
    private     Point       nextCardLocation;
    private     int         spreadDirection;
    private     int         spreadingDelta;
}


