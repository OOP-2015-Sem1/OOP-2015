package Cards;

public class Suit {

    public static final Suit HEART = new Suit( "H" );
    public static final Suit SPADE = new Suit( "S" );
    public static final Suit DIAMOND = new Suit( "D" );
    public static final Suit CLUB = new Suit( "C" );

    public static final Suit[] suits = { HEART, SPADE, DIAMOND, CLUB };

    private Suit( String id ) {
        this.id = id;
    }

    public String toString() {
        return( id );
    }

    private String id;

}
