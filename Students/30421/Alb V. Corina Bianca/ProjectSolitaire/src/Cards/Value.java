package Cards;

public class Value {

    public static final Value V_1 = new Value( 1 );
    public static final Value V_2 = new Value( 2 );
    public static final Value V_3 = new Value( 3 );
    public static final Value V_4 = new Value( 4 );
    public static final Value V_5 = new Value( 5 );
    public static final Value V_6 = new Value( 6 );
    public static final Value V_7 = new Value( 7 );
    public static final Value V_8 = new Value( 8 );
    public static final Value V_9 = new Value( 9 );
    public static final Value V_10 = new Value( 10 );
    public static final Value V_11 = new Value( 11 );
    public static final Value V_12 = new Value( 12 );
    public static final Value V_13 = new Value( 13 );

    public static final Value V_JACK = V_11;
    public static final Value V_QUEEN = V_12;
    public static final Value V_KING = V_13;

    public static final Value V_ACE = V_1;

    public static final String  STRING_ACE     = "A";
    public static final String  STRING_JACK    = "J";
    public static final String  STRING_QUEEN   = "Q";
    public static final String  STRING_KING    = "K";

    public static final Value[] values = {
        V_1, V_2, V_3, V_4, V_5,
        V_6, V_7, V_8, V_9, V_10,
        V_11, V_12, V_13
    };

    private Value( int value ) {
        this.value = value;
    }

    public int getValue() {
        return( value );
    }

    public String toString() {
        if( equals( V_JACK ) )
            return( STRING_JACK );
        else if( equals( V_QUEEN ) )
            return( STRING_QUEEN );
        else if( equals( V_KING ) )
            return( STRING_KING );
        else if( equals( V_ACE ) )
            return( STRING_ACE );
        else
            return( new Integer( value ).toString() );
    }

    int value;

}
