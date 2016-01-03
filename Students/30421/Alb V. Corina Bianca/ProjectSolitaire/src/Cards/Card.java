package Cards;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Card {
	
	public static int DEFAULT_HEIGHT = 129;
    public static int DEFAULT_WIDTH = 86;
    
	private   boolean       faceDown;
    private   Point         location;
    private   Dimension     size = new Dimension( DEFAULT_WIDTH, DEFAULT_HEIGHT );

    public abstract String toString();
    public abstract void paint( Graphics g, boolean hint );
    
    public boolean isFaceDown() {
        return faceDown;
    }
    
    public void turnFaceUp() {
        faceDown = false;
    }
    
    public void turnFaceDown() {
        faceDown = true;
    }
    
    public void flip() {
        if( isFaceDown() )
            turnFaceUp();
        else
            turnFaceDown();
    }
    
    public Point getLocation() {
        return location;
    }
    
    public void setLocation( Point p ) {
        location = new Point( p.x, p.y );
    }

    public void setLocation( int x, int y ) {
        location = new Point( x, y );
    }

    public Dimension getSize() {
        return( size );
    }

    public void setSize( int width, int height ) {
        size = new Dimension( width, height );
    }

    public void setSize( Dimension dim ) {
        size = new Dimension( dim.width, dim.height );
    }

    public boolean contains( Point p ) {
        Rectangle rect = new Rectangle( location.x, location.y, size.width, size.height );
        return( rect.contains( p ) );
    }
}